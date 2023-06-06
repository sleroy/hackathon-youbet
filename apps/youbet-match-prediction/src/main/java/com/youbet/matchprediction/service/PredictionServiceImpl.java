package com.youbet.matchprediction.service;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
import com.youbet.ports.matchsystem.MatchRecord;
import com.youbet.utils.YoubetException;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.DecisionTreeClassificationModel;
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.feature.VectorIndexerModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service computes the score of three probabilities (home win, away win, stalemate probability).
 * It uses machine learning and a training set stored into the mysql database.
 */
public class PredictionServiceImpl implements PredictionService {
    public static final String NORM_FEATURES = "normFeatures";
    public static final String FEATURES = "features";
    public static final String LABEL = "label";
    public static final int TREE_MAX_DEPTH = 20;
    public static final int TREE_MAX_BINS = 30;
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionServiceImpl.class);
    private static final int CLASSES_COUNT = 3;
    private static final int MIN_SIZE = 100;
    
    private final MatchPredictionRepository matchPredictionRepository;
    private final long SEED = 14545454545L;
    
    // Stateful data
    private transient DecisionTreeClassificationModel treeModel;
    private transient SparkSession spark;
    
    public PredictionServiceImpl(MatchPredictionRepository matchPredictionRepository) {
        this.matchPredictionRepository = matchPredictionRepository;
    }
    
    /**
     * Train the dataset and build the decision tree model. The previous model is overwriten.
     */
    public void training() throws YoubetException {
        try {
            buildDecisionTreeModel();
        } catch (Exception e) {
            throw new YoubetException("Cannot prepare the prediction engine", e);
        }
    }
    
    private void buildDecisionTreeModel() throws Exception {
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Loading data form the DB");
        List<MatchRecord> matchRecords = matchPredictionRepository.selectAll();
        if (matchRecords.size() < MIN_SIZE) {
            LOGGER.error("Not enough records to initialize the prediction engine.");
            return;
        }
        LOGGER.info("Data fetched (records={})", matchRecords.size());
        initSparkSession();
        
        
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Creation of the training set");
        List<Row> vectorOfRecords = matchRecords
                .stream()
                .map(MatchRecordDatasetConverter::convertRawDataIntoRow)
                .collect(Collectors.toList());
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Normalization of the data");
        // https://spark.apache.org/docs/latest/ml-features
        StructType schema = new StructType(new StructField[]{
                new StructField(LABEL, DataTypes.IntegerType, false, Metadata.empty()),
                new StructField(FEATURES, new VectorUDT(), false, Metadata.empty())
        });
        
        Dataset<Row> data = spark.createDataFrame(vectorOfRecords, schema);
        
        var indexer = new VectorIndexer()
                .setInputCol("features")
                .setOutputCol(NORM_FEATURES)
                .setMaxCategories(10);
        VectorIndexerModel indexerModel = indexer.fit(data);
        
        
        Map<Integer, Map<Double, Integer>> categoryMaps = indexerModel.javaCategoryMaps();
        
        
        LOGGER.info("Chose " + categoryMaps.size() + " categorical features:");
        
        
        for (Integer feature : categoryMaps.keySet()) {
            LOGGER.info(" feature > " + feature);
        }
        LOGGER.info("----------------------------------------------------");
        
        // Create new column "indexed" with categorical values transformed to indices
        Dataset<Row> normalizedData = indexerModel.transform(data);
        normalizedData.show();
        
        
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Building training and test dataset");
        // https://spark.apache.org/docs/latest/ml-classification-regression.html#decision-tree-classifier
        // Split the data into training and test sets (30% held out for testing).
        Dataset<Row>[] splits = normalizedData.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testData = splits[1];
        
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Instantiate model....");
        
        
        // Train a DecisionTree model.
        DecisionTreeClassifier dt = new DecisionTreeClassifier()
                .setMaxDepth(TREE_MAX_DEPTH)
                .setLabelCol(LABEL)
                .setSeed(SEED)
                .setMaxBins(TREE_MAX_BINS)
                .setFeaturesCol(NORM_FEATURES);
        
        // Convert indexed labels back to original labels.
        IndexToString labelConverter = new IndexToString()
                .setInputCol("prediction")
                .setOutputCol("predictedLabel")
                .setLabels(MatchStateEnum.names());
        
        // Chain indexers and tree in a Pipeline.
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[]{dt, labelConverter});
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Run the model");
        
        // Train model. This also runs the indexers.
        PipelineModel model = pipeline.fit(trainingData);
        
        LOGGER.info("----------------------------------------------------");
        LOGGER.info("Make predictions on test data");
        
        // Make predictions.
        Dataset<Row> predictions = model.transform(testData);
        System.out.println(predictions.select("probability").toJSON());
        // Select example rows to display.
        predictions.select("predictedLabel", LABEL, FEATURES).show(5);
        
        // Select (prediction, true label) and compute test error.
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol(LABEL)
                .setPredictionCol("prediction")
                .setMetricName("accuracy");
        double accuracy = evaluator.evaluate(predictions);
        
        
        LOGGER.warn("---------------------------------------------------");
        LOGGER.warn("Test Error = " + (1.0 - accuracy));
        LOGGER.warn("---------------------------------------------------");
        
        
        this.treeModel = (DecisionTreeClassificationModel) (model.stages()[0]);
        
        //dt.write().overwrite().save("./decisionTree.ml");
        //pipeline.write().overwrite().save("./pipeline.ml");
        //this.treeModel.write().overwrite().save("./decisionTreeModel.ml");
        
    }
    
    private void initSparkSession() {
        if (this.spark != null) return;
        this.spark = SparkSession.builder()
                                 .config("spark.master", "local")
                                 .appName("youbet-prediction-demo")
                                 .getOrCreate();
    }
    
    /**
     * Load an existing saved model for testing purpose.
     *
     * @param path the path to the saved model
     */
    public void loadPredictionModel(String path) {
        this.initSparkSession();
        this.treeModel = DecisionTreeClassificationModel.load(path);
    }
    
    @Override public MatchPredictionOddsUpdatedEvent predict(MatchRecord mr) {
        Row row = MatchRecordDatasetConverter.convertRawDataIntoRow(mr);
        Vector probability = treeModel.predictProbability((Vector) row.get(1));
        LOGGER.info("Learned classification tree model:\n" + treeModel.toDebugString());
        System.out.println(probability);
    
        var resp = new MatchPredictionOddsUpdatedEvent();
        resp.setCountry(mr.getCountry_id());
        resp.setDate(mr.getDate());
        resp.setLeague(mr.getLeague_id());
        resp.setSeason(mr.getSeason());
        
        
        resp.setHomeWin(probability.apply(MatchStateEnum.HOME_WIN.value));
        resp.setAwayWin(probability.apply(MatchStateEnum.AWAY_WIN.value));
        resp.setStalemate(probability.apply(MatchStateEnum.STALEMATE.value));
        return resp;
    }
    
    public enum MatchStateEnum {
        HOME_WIN(0),
        AWAY_WIN(1),
        STALEMATE(2);
        
        private final int value;
        
        MatchStateEnum(int value) {this.value = value;}
        
        public static String[] names() {
            return Arrays.asList(values()).stream().map(Enum::name).toArray(String[]::new);
        }
        
        public Integer value() {
            return value;
        }
    }
}

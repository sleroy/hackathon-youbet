package com.youbet.matchprediction.app;

import com.youbet.adapters.envvar.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.mysql.MySqlDatabaseAdapter;
import com.youbet.adapters.rabbitmq.Queues;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.matchprediction.agents.prediction.MatchPredictionScoreAgent;
import com.youbet.matchprediction.agents.predictionstorage.MatchDataStorageAgent;
import com.youbet.matchprediction.service.PredictionServiceImpl;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the application
 */
public class MatchPredictionMonolith {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchPredictionMonolith.class);
    
    public static void main(String[] args) {
        try {
            initApp();
        } catch(Exception e) {
            LOGGER.error("Cannot initialize the app", e);
            System.exit(2);
        }
    
    
    }
    
    private static void initApp() {
        /* Initialize and bind ports to adapters */
        JsonUtils.initializeWithDefault();
        AppConfigurationPort appConfigurationPort = new AppEnvironmentVariableConfigurationAdapter();
        
        /* Initializes RabbitMQ Adapter. */
        RabbitMQMessageBrokerAdapter messageBroker = new RabbitMQMessageBrokerAdapter(appConfigurationPort);
        messageBroker.start();
        MessageBrokerPort messageBrokerPort = messageBroker;
        
        /* Initializes Aurora Adapter */
        MySqlDatabaseAdapter mySqlDatabaseAdapter = new MySqlDatabaseAdapter(appConfigurationPort);
        MatchPredictionRepository matchPredictionRepository = mySqlDatabaseAdapter.getMatchPredictionRepository();
    
        /***
         * Initialize prediction service
         */
        PredictionServiceImpl predictionService = new PredictionServiceImpl(matchPredictionRepository);
        predictionService.training();
        /**
         * Initialize missing queues
         */
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_PREDICTION_STORAGE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_PREDICTION_SCORE);
    
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_PREDICTION_STORAGE, new MatchDataStorageAgent(messageBrokerPort, matchPredictionRepository));
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_PREDICTION_SCORE, new MatchPredictionScoreAgent(messageBrokerPort, predictionService, matchPredictionRepository));
        
        LOGGER.info("Match prediction initialized");
    
    
    }
}

package com.youbet.matchprediction.service;

import com.youbet.ports.matchsystem.MatchRecord;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the code to convert a MatchRecord into the data structure required to build a decision tree.
 */
public class MatchRecordDatasetConverter {
    /**
     * Converts a match record into a Spark row. Basically it contains two columns : one with the label (class) and one vector with the features.
     *
     * @param mR the match recor d
     * @return the row.
     */
    public static Row convertRawDataIntoRow(MatchRecord mR) {
        
        PredictionServiceImpl.MatchStateEnum state = PredictionServiceImpl.MatchStateEnum.STALEMATE;
        if (mR.getScore_team_home() == mR.getScore_team_away()) {
            state = PredictionServiceImpl.MatchStateEnum.STALEMATE;
        } else if (mR.getScore_team_home() < mR.getScore_team_away()) {
            state = PredictionServiceImpl.MatchStateEnum.AWAY_WIN;
        } else {
            state = PredictionServiceImpl.MatchStateEnum.HOME_WIN;
        }
        
        List<Double> v = new ArrayList<Double>(70);
        int i = 0;
        v.add(mR.getLeague_id().doubleValue()); // League,
        v.add(mR.getCountry_id().doubleValue());// Country
        v.add(Double.valueOf(mR.getDate().getYear()));
        v.add(Double.valueOf(mR.getDate().getMonthValue()));
        v.add(Double.valueOf(mR.getDate().getDayOfMonth()));
        
        v.add(Double.valueOf(mR.getHome_team_api_id())); // Home team
        v.add(Double.valueOf(mR.getAway_team_api_id())); // Away team
        
        v.add(featurePlayerValue(mR.getAway_player_1()));
        v.add(featurePlayerValue(mR.getAway_player_2()));
        v.add(featurePlayerValue(mR.getAway_player_3()));
        v.add(featurePlayerValue(mR.getAway_player_4()));
        v.add(featurePlayerValue(mR.getAway_player_5()));
        v.add(featurePlayerValue(mR.getAway_player_6()));
        v.add(featurePlayerValue(mR.getAway_player_7()));
        v.add(featurePlayerValue(mR.getAway_player_8()));
        v.add(featurePlayerValue(mR.getAway_player_9()));
        v.add(featurePlayerValue(mR.getAway_player_10()));
        v.add(featurePlayerValue(mR.getAway_player_11()));
        
        v.add(featurePlayerValue(mR.getAway_player_X1()));
        v.add(featurePlayerValue(mR.getAway_player_X2()));
        v.add(featurePlayerValue(mR.getAway_player_X3()));
        v.add(featurePlayerValue(mR.getAway_player_X4()));
        v.add(featurePlayerValue(mR.getAway_player_X5()));
        v.add(featurePlayerValue(mR.getAway_player_X6()));
        v.add(featurePlayerValue(mR.getAway_player_X7()));
        v.add(featurePlayerValue(mR.getAway_player_X8()));
        v.add(featurePlayerValue(mR.getAway_player_X9()));
        v.add(featurePlayerValue(mR.getAway_player_X10()));
        v.add(featurePlayerValue(mR.getAway_player_X11()));
        
        v.add(featurePlayerValue(mR.getAway_player_Y1()));
        v.add(featurePlayerValue(mR.getAway_player_Y2()));
        v.add(featurePlayerValue(mR.getAway_player_Y3()));
        v.add(featurePlayerValue(mR.getAway_player_Y4()));
        v.add(featurePlayerValue(mR.getAway_player_Y5()));
        v.add(featurePlayerValue(mR.getAway_player_Y6()));
        v.add(featurePlayerValue(mR.getAway_player_Y7()));
        v.add(featurePlayerValue(mR.getAway_player_Y8()));
        v.add(featurePlayerValue(mR.getAway_player_Y9()));
        v.add(featurePlayerValue(mR.getAway_player_Y10()));
        v.add(featurePlayerValue(mR.getAway_player_Y11()));
        
        v.add(featurePlayerValue(mR.getHome_player_1()));
        v.add(featurePlayerValue(mR.getHome_player_2()));
        v.add(featurePlayerValue(mR.getHome_player_3()));
        v.add(featurePlayerValue(mR.getHome_player_4()));
        v.add(featurePlayerValue(mR.getHome_player_5()));
        v.add(featurePlayerValue(mR.getHome_player_6()));
        v.add(featurePlayerValue(mR.getHome_player_7()));
        v.add(featurePlayerValue(mR.getHome_player_8()));
        v.add(featurePlayerValue(mR.getHome_player_9()));
        v.add(featurePlayerValue(mR.getHome_player_10()));
        v.add(featurePlayerValue(mR.getHome_player_11()));
        
        v.add(featurePlayerValue(mR.getHome_player_X1()));
        v.add(featurePlayerValue(mR.getHome_player_X2()));
        v.add(featurePlayerValue(mR.getHome_player_X3()));
        v.add(featurePlayerValue(mR.getHome_player_X4()));
        v.add(featurePlayerValue(mR.getHome_player_X5()));
        v.add(featurePlayerValue(mR.getHome_player_X6()));
        v.add(featurePlayerValue(mR.getHome_player_X7()));
        v.add(featurePlayerValue(mR.getHome_player_X8()));
        v.add(featurePlayerValue(mR.getHome_player_X9()));
        v.add(featurePlayerValue(mR.getHome_player_X10()));
        v.add(featurePlayerValue(mR.getHome_player_X11()));
        
        v.add(featurePlayerValue(mR.getHome_player_Y1()));
        v.add(featurePlayerValue(mR.getHome_player_Y2()));
        v.add(featurePlayerValue(mR.getHome_player_Y3()));
        v.add(featurePlayerValue(mR.getHome_player_Y4()));
        v.add(featurePlayerValue(mR.getHome_player_Y5()));
        v.add(featurePlayerValue(mR.getHome_player_Y6()));
        v.add(featurePlayerValue(mR.getHome_player_Y7()));
        v.add(featurePlayerValue(mR.getHome_player_Y8()));
        v.add(featurePlayerValue(mR.getHome_player_Y9()));
        v.add(featurePlayerValue(mR.getHome_player_Y10()));
        v.add(featurePlayerValue(mR.getHome_player_Y11()));

//v[8] = mR.getScore_team_home();


//v[7] = state.name(); // label
        return RowFactory.create(state.value(), Vectors.dense(v.stream().mapToDouble(d -> d).toArray()));
    }
    
    private static Double featurePlayerValue(Integer value) {
        if (value == null) return -1.0;
        return value.doubleValue();
    }
}

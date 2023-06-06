package com.youbet.matchprediction.service;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.ports.matchsystem.MatchRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PredictionServiceTest {
    
    @Disabled("It requires specific JVM args to launch Spark")
    @Test
    public void testPredictionMultiClasses() {
        // Perform a prediction and evaluate the output of the service.
        var prediction = new PredictionServiceImpl(new MatchPredictionRepositoryStub());
        prediction.loadPredictionModel("src/test/resources/decisionTreeModel.ml");
        
        
        MatchRecord matchRecord = new MatchRecord();
        matchRecord.setSeason("Season 1");
        matchRecord.setLeague_id(1);
        matchRecord.setCountry_id(4);
        matchRecord.setDate(LocalDateTime.now());
        matchRecord.setHome_team_api_id(1);
        matchRecord.setAway_team_api_id(1100);
        MatchPredictionOddsUpdatedEvent predict = prediction.predict(matchRecord);
        
        Assertions.assertTrue((predict.getAwayWin() + predict.getHomeWin() + predict.getStalemate()) > 0);
        
    }
    
}
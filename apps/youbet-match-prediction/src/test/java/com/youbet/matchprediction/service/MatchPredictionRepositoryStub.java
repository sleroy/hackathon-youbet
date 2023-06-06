package com.youbet.matchprediction.service;

import com.youbet.domain.requests.MatchPredictionRequest;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
import com.youbet.ports.matchsystem.MatchRecord;

import java.util.List;

public class MatchPredictionRepositoryStub implements MatchPredictionRepository {
    @Override public void createRecord(MatchSystemMatchRegistrationRequest matchRegistrationRequest) {
        
    }
    
    @Override public void updateRecord(MatchSystemMatchUpdateRequest matchUpdateRequest) {
        
    }
    
    @Override public List<MatchRecord> selectAll() {
        return null;
    }
    
    @Override public MatchRecord selectRecord(MatchPredictionRequest event) {
        return null;
    }
}

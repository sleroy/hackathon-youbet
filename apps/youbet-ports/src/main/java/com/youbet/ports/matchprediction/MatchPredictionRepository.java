package com.youbet.ports.matchprediction;

import com.youbet.domain.requests.MatchPredictionRequest;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;
import com.youbet.ports.matchsystem.MatchRecord;

import java.util.List;

public interface MatchPredictionRepository {
    
    /**
     * Creates a new record in the training set. Initializes the score to zero.
     *
     * @param matchRegistrationRequest
     */
    void createRecord(MatchSystemMatchRegistrationRequest matchRegistrationRequest);
    
    /**
     * Creates a new update to a match.
     *
     * @param matchUpdateRequest the match update request.
     */
    void updateRecord(MatchSystemMatchUpdateRequest matchUpdateRequest);
    
    /**
     * Returns all the records from the table.
     *
     * @return the list of all records
     */
    List<MatchRecord> selectAll();
    
    MatchRecord selectRecord(MatchPredictionRequest event);
}

package com.youbet.matchprediction.service;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.ports.matchsystem.MatchRecord;

/**
 * This interface offers the possibility to predict the score for a match.
 * It outputs three probabilities (home win, away win, stalemate win).
 */
public interface PredictionService {
    /**
     * Predict the outcome of a match.
     *
     * @param matchRecord the match record to use for prediction.
     * @return the probabilities.
     */
    MatchPredictionOddsUpdatedEvent predict(MatchRecord matchRecord);
}

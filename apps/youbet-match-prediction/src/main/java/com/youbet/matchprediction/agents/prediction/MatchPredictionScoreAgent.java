package com.youbet.matchprediction.agents.prediction;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.requests.MatchPredictionRequest;
import com.youbet.matchprediction.service.PredictionService;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
import com.youbet.ports.matchsystem.MatchRecord;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This agent receives match registration, detects if the match does not exist. Creates the entry if missing. Initializes the score and the state with default value.
 * It also sends an event to the integration layer to produce the new entity graph.
 * <p>
 * The agent receive two kinds of event (Match registration and Match update event)
 */
public class MatchPredictionScoreAgent implements JsonConsumer<MatchPredictionRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchPredictionScoreAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    private final PredictionService predictionService;
    private final MatchPredictionRepository matchPredictionRepository;
    
    public MatchPredictionScoreAgent(MessageBrokerPort messageBrokerPort,
                                     PredictionService predictionService,
                                     MatchPredictionRepository matchPredictionRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.predictionService = predictionService;
        this.matchPredictionRepository = matchPredictionRepository;
    }
    
    @Override public String getConsumerTag() {
        return MatchPredictionScoreAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchPredictionRequest event) {
        MatchRecord matchRecord = this.matchPredictionRepository.selectRecord(event);
        if (matchRecord == null) {
            LOGGER.error("Match not found {}", event);
            return ;
        }
        MatchPredictionOddsUpdatedEvent updatedEvent = predictionService.predict(matchRecord);
        messageBrokerPort.dispatchToMatchSystemUpdateScore(updatedEvent);
        
    }
    
    @Override public Class<MatchPredictionRequest> supportedImpl() {
        return MatchPredictionRequest.class;
    }
    
}

package com.youbet.matchprediction.agents.predictionstorage;

import com.youbet.domain.requests.*;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
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
public class MatchDataStorageAgent implements JsonConsumer<MatchBaseInfoRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataStorageAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    private final MatchPredictionRepository matchPredictionRepository;
    
    public MatchDataStorageAgent(MessageBrokerPort messageBrokerPort,
                                 MatchPredictionRepository matchPredictionRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchPredictionRepository = matchPredictionRepository;
    }
    
    @Override public String getConsumerTag() {
        return MatchDataStorageAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchBaseInfoRequest event) {
        if (event.getEventName().equals(Events.EVENT_MATCH_REGISTRATION_EVENT)) {
            matchPredictionRepository.createRecord(youbetMessage.toJson(MatchSystemMatchRegistrationRequest.class));
        } else {
            matchPredictionRepository.updateRecord(youbetMessage.toJson(MatchSystemMatchUpdateRequest.class));
        }
        messageBrokerPort.dispatchToMatchPredictionUpdateScore(event);
        
    }
    
    @Override public Class<MatchBaseInfoRequest> supportedImpl() {
        return MatchBaseInfoRequest.class;
    }
    
}

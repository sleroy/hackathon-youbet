package com.youbet.matchsystem.agents.matchupdateodds;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;

/**
 * This agent updates the odds of a match after receiving them from the prediction engine.
 */
public class MatchUpdateOddsAgent implements
        com.youbet.ports.messagebroker.JsonConsumer<MatchPredictionOddsUpdatedEvent> {
    private final MessageBrokerPort messageBrokerPort;
    private final MatchSystemRepository matchSystemRepository;
    
    public MatchUpdateOddsAgent(MessageBrokerPort messageBrokerPort, MatchSystemRepository
            matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return MatchUpdateOddsAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchPredictionOddsUpdatedEvent event) {
        this.matchSystemRepository.updateMatchOdds(event);
    }
    
    @Override public Class<MatchPredictionOddsUpdatedEvent> supportedImpl() {
        return MatchPredictionOddsUpdatedEvent.class;
    }
}

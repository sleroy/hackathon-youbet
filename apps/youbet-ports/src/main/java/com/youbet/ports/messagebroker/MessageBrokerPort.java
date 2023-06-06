package com.youbet.ports.messagebroker;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchUpdatedEvent;
import com.youbet.domain.requests.MatchBaseInfoRequest;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;

public interface MessageBrokerPort {
    void start();
    
    void dispatchToAggregateLeagueQueue(YoubetMessage message);
    
    void dispatchToMatchEventNotifier(MatchSystemMatchRegistrationRequest message);
    
    void dispatchToAggregateTeamQueue(ObjectNode youbetMessage);
    
    void dispatchToSanizationQueue(YoubetMessage youbetMessage);
    
    void dispatchToMatchSystemRegistrationQueue(YoubetMessage event);
    
    void dispatchToMatchSystemUpdateQueue(YoubetMessage event);
    
    void dispatchToMatchPredictionDataStorageQueue(YoubetMessage event);
    
    void dispatchToMatchEventNotifier(YoubetMessage youbetMessage);
    
    void dispatchToPlayerAggregation(ObjectNode jsonNode);
    
    void dispatchToMatchSystemUpdateScore(MatchPredictionOddsUpdatedEvent updatedEvent);
    
    void dispatchToMatchPredictionUpdateScore(MatchBaseInfoRequest event);
    
    void dispatchToExternalProviderMatchRegistrationQueue(ExternalProviderMatchRegisteredEvent event);
    
    void dispatchToExternalProviderMatchUpdateQueue(ExternalProviderMatchUpdatedEvent event);
}

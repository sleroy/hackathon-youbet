package com.youbet.ports.messagebroker;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface MessageBrokerPort {
    void start();
    
    void dispatchToAggregateLeagueQueue(YoubetMessage message);
    
    void dispatchToMatchSystemMatchRegistrationQueue(ObjectNode message);
    
    void dispatchToAggregateTeamQueue(ObjectNode youbetMessage);
    
    void dispatchToSanizationQueue(YoubetMessage youbetMessage);
    
    void dispatchToMatchSystemRegistrationQueue(YoubetMessage event);
    
    void dispatchToMatchSystemUpdateQueue(YoubetMessage event);
    
    void dispatchToMatchPredictionDataStorageQueue(YoubetMessage event);
}

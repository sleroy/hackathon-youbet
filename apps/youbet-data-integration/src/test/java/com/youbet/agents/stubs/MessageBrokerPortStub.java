package com.youbet.agents.stubs;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageBrokerPortStub implements MessageBrokerPort {
    
    public List<YoubetMessage> getSentEvents() {
        return sentEvents;
    }
    
    private List<YoubetMessage> sentEvents = new ArrayList<>();
    
    @Override public void start() {
        
    }
    
    @Override public void dispatchToAggregateLeagueQueue(YoubetMessage message) {
        sentEvents.add(message);
    }
    
    @Override public void dispatchToMatchSystemMatchRegistrationQueue(ObjectNode message) {
        sentEvents.add(YoubetMessage.fromJson(message));
    }
    
    @Override public void dispatchToAggregateTeamQueue(ObjectNode message) {
        sentEvents.add(YoubetMessage.fromJson(message));
        
    }
    
    @Override public void dispatchToSanizationQueue(YoubetMessage youbetMessage) {
        
        sentEvents.add(youbetMessage);
    }
    
    @Override public void dispatchToMatchSystemRegistrationQueue(YoubetMessage event) {
        sentEvents.add(event);
    }
    
    @Override public void dispatchToMatchSystemUpdateQueue(YoubetMessage event) {
        sentEvents.add(event);
    }
    
    @Override public void dispatchToMatchPredictionDataStorageQueue(YoubetMessage event) {
        sentEvents.add(event);
    }
}

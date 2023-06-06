package com.youbet.agents.stubs;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchUpdatedEvent;
import com.youbet.domain.requests.MatchBaseInfoRequest;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub to emulate the message broker.
 */
public class MessageBrokerPortStub implements MessageBrokerPort {
    
    private final List<YoubetMessage> sentEvents = new ArrayList<>();
    
    public List<YoubetMessage> getSentEvents() {
        return sentEvents;
    }
    
    @Override public void start() {
        
    }
    
    @Override public void dispatchToAggregateLeagueQueue(YoubetMessage message) {
        sentEvents.add(message);
    }
    
    @Override public void dispatchToMatchEventNotifier(MatchSystemMatchRegistrationRequest message) {
        sentEvents.add(YoubetMessage.fromEvent(message));
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
    
    @Override public void dispatchToMatchEventNotifier(YoubetMessage event) {
        sentEvents.add(event);
    }
    
    @Override public void dispatchToPlayerAggregation(ObjectNode jsonNode) {
        sentEvents.add(YoubetMessage.fromJson(jsonNode));
    }
    
    @Override public void dispatchToMatchSystemUpdateScore(MatchPredictionOddsUpdatedEvent updatedEvent) {
        sentEvents.add(YoubetMessage.fromEvent(updatedEvent));
    }
    
    @Override public void dispatchToMatchPredictionUpdateScore(MatchBaseInfoRequest event) {
        sentEvents.add(YoubetMessage.fromEvent(event));
    }
    
    @Override public void dispatchToExternalProviderMatchRegistrationQueue(ExternalProviderMatchRegisteredEvent event) {
        sentEvents.add(YoubetMessage.fromEvent(event));
    }
    
    @Override public void dispatchToExternalProviderMatchUpdateQueue(ExternalProviderMatchUpdatedEvent event) {
        
    }
}

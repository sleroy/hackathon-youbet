package com.youbet.agents.stubs;

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
    
    @Override public void dispatchMessageAggregateLeagueQueue(YoubetMessage message) {
        sentEvents.add(message);
    }
    
    @Override public void dispatchMessageMatchSystemMatchRegistrationQueue(YoubetMessage message) {
        sentEvents.add(message);
    }
    
    @Override public void dispatchMessageAggregateTeamQueue(YoubetMessage message) {
        sentEvents.add(message);
        
    }
}

package com.youbet.dataintegration.agents.matcheventdispatcher;

import com.youbet.dataintegration.agents.leagueaggregation.LeagueAggregationAgent;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchEventDispatcherAgent implements com.youbet.ports.messagebroker.Consumer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAggregationAgent.class);
    private MessageBrokerPort messageBrokerPort;
    private com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemPort;
    
    public MatchEventDispatcherAgent(MessageBrokerPort messageBrokerPort, com.youbet.ports.matchsystem.MatchSystemRepositoryPort
            matchSystemPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemPort = matchSystemPort;
    }
    
    @Override public void handleRequest(YoubetMessage event) {
        com.youbet.domain.BaseEvent baseEvent = event.toJson(com.youbet.domain.BaseEvent.class);
        switch (baseEvent.getEventName()) {
            case com.youbet.domain.Events.EVENT_MATCH_REGISTRATION_EVENT:
                messageBrokerPort.dispatchToMatchSystemRegistrationQueue(event);
                break;
            case com.youbet.domain.Events.EVENT_MATCH_UPDATE_EVENT:
                messageBrokerPort.dispatchToMatchSystemUpdateQueue(event);
                messageBrokerPort.dispatchToMatchPredictionDataStorageQueue(event);
                break;
            default:
                LOGGER.warn("Event is not supported {}", baseEvent);
        }
    }
    
    @Override public String getConsumerTag() {
        return MatchEventDispatcherAgent.class.getName();
    }
}

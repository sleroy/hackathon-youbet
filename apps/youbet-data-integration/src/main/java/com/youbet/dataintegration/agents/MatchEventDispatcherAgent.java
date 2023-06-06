package com.youbet.dataintegration.agents;

import com.youbet.domain.requests.BaseRequestView;
import com.youbet.domain.requests.Events;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This agent is in charge to dispatch the events to the two system (Match System and Match Prediction) and eventually prepare the events.
 */
public class MatchEventDispatcherAgent implements com.youbet.ports.messagebroker.Consumer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchEventDispatcherAgent.class);
    private MessageBrokerPort messageBrokerPort;
    private MatchSystemRepository matchSystemPort;
    
    public MatchEventDispatcherAgent(MessageBrokerPort messageBrokerPort, MatchSystemRepository
            matchSystemPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemPort = matchSystemPort;
    }
    
    @Override public void handleRequest(YoubetMessage event) {
        BaseRequestView baseRequest = event.toJson(BaseRequestView.class);
        switch (baseRequest.getEventName()) {
            case Events.EVENT_MATCH_REGISTRATION_EVENT:
                LOGGER.info("Match to be registered {}", event.toJson());
                messageBrokerPort.dispatchToMatchSystemRegistrationQueue(event);
                messageBrokerPort.dispatchToMatchPredictionDataStorageQueue(event);
                break;
            case Events.EVENT_MATCH_UPDATE_EVENT:
                LOGGER.debug("Match to be updated {}", event.toJson());
                messageBrokerPort.dispatchToMatchSystemUpdateQueue(event);
                messageBrokerPort.dispatchToMatchPredictionDataStorageQueue(event);
                break;
            default:
                LOGGER.warn("Event is not supported {}", baseRequest);
        }
    }
    
    @Override public String getConsumerTag() {
        return MatchEventDispatcherAgent.class.getName();
    }
}

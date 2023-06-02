package com.youbet.matchsystem.agents.matchregistration;

import com.youbet.domain.MatchReferenceBaseEvent;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchRegistrationAgent implements com.youbet.ports.messagebroker.JsonConsumer<MatchReferenceBaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchRegistrationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort;
    
    public MatchRegistrationAgent(MessageBrokerPort messageBrokerPort,
                                  com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepositoryPort = matchSystemRepositoryPort;
    }
    
    @Override public String getConsumerTag() {
        return MatchRegistrationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchReferenceBaseEvent event) {

    }
    
    @Override public Class<MatchReferenceBaseEvent> supportedImpl() {
        return MatchReferenceBaseEvent.class;
    }
}

package com.youbet.matchsystem.agents.matchupdate;

import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchUpdateAgent implements com.youbet.ports.messagebroker.JsonConsumer<MatchUpdateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchUpdateAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort;
    
    public MatchUpdateAgent(MessageBrokerPort messageBrokerPort,
                            com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepositoryPort = matchSystemRepositoryPort;
    }
    
    @Override public String getConsumerTag() {
        return MatchUpdateAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchUpdateEvent event) {

    }
    
    @Override public Class<MatchUpdateEvent> supportedImpl() {
        return MatchUpdateEvent.class;
    }
}

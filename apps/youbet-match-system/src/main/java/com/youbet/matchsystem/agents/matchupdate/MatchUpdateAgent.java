package com.youbet.matchsystem.agents.matchupdate;

import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchUpdateAgent implements com.youbet.ports.messagebroker.JsonConsumer<MatchSystemMatchUpdateRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchUpdateAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private final MatchSystemRepository matchSystemRepository;
    
    public MatchUpdateAgent(MessageBrokerPort messageBrokerPort,
                            MatchSystemRepository matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return MatchUpdateAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchSystemMatchUpdateRequest event) {
        this.matchSystemRepository.updateMatch(event);
    }
    
    @Override public Class<MatchSystemMatchUpdateRequest> supportedImpl() {
        return MatchSystemMatchUpdateRequest.class;
    }
}

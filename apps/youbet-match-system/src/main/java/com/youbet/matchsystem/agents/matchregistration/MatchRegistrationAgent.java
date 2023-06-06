package com.youbet.matchsystem.agents.matchregistration;

import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This agent receives match registration, detects if the match does not exist. Creates the entry if missing. Initializes the score and the state with default value.
 * It also sends an event to the integration layer to produce the new entity graph.
 */
public class MatchRegistrationAgent implements com.youbet.ports.messagebroker.JsonConsumer<MatchSystemMatchRegistrationRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchRegistrationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private MatchSystemRepository matchSystemRepository;
    
    public MatchRegistrationAgent(MessageBrokerPort messageBrokerPort,
                                  MatchSystemRepository matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return MatchRegistrationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchSystemMatchRegistrationRequest event) {
        this.matchSystemRepository.createMatch(event);
    }
    
    @Override public Class<MatchSystemMatchRegistrationRequest> supportedImpl() {
        return MatchSystemMatchRegistrationRequest.class;
    }
}

package com.youbet.dataintegration.agents.dataingestion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.Events;
import com.youbet.domain.ExternalProviderMatchRegisteredEvent;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Validate;

public class ExternalProviderMatchUpdateAgent implements JsonConsumer<ExternalProviderMatchRegisteredEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalProviderMatchUpdateAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    
    public ExternalProviderMatchUpdateAgent(MessageBrokerPort messageBrokerPort) {
        
        this.messageBrokerPort = messageBrokerPort;
    }
    
    @Override public String getConsumerTag() {
        return ExternalProviderMatchUpdateAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, ExternalProviderMatchRegisteredEvent event) {
        JsonNode jsonNode = youbetMessage.toJson();
        Validate.isTrue(jsonNode.isObject(), "Payload is supposed to be an object");
        ObjectNode payload = (ObjectNode) jsonNode;
        payload.put(Events.ATTR_EVENT_NAME, Events.EVENT_MATCH_UPDATE_EVENT);
        
        messageBrokerPort.dispatchToSanizationQueue(YoubetMessage.fromJson(payload));
    }
    
    @Override public Class<ExternalProviderMatchRegisteredEvent> supportedImpl() {
        return ExternalProviderMatchRegisteredEvent.class;
    }
}

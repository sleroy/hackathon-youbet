package com.youbet.ports.messagebroker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.agents.domain.ExternalProviderMatchRegisteredEvent;
import com.youbet.utils.JsonUtils;

/**
 * This interface is a wrapper to manipulate messages through the MessageBroker.
 */
public class YoubetMessage {
    
    private byte[] body;
    private String routingKey;
    
    public YoubetMessage(byte[] body) {
        this.body = body;
    }
    
    public static YoubetMessage toJsonBody(ExternalProviderMatchRegisteredEvent event) {
        return new YoubetMessage(JsonUtils.toJsonBytes(event));
    }
    
    public static YoubetMessage fromJson(ObjectNode jsonNode) {
        return new YoubetMessage(JsonUtils.toJsonBytes(jsonNode));
    }
    
    public byte[] getBody() {
        return body;
    }
    
    public String getRoutingKey() {
        return routingKey;
    }
    
    public JsonNode toJson() {
        return JsonUtils.parseBytes(body);
    }
    
    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}

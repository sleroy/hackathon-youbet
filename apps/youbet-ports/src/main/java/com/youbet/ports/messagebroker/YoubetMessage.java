package com.youbet.ports.messagebroker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Envelope;
import com.youbet.utils.JsonUtils;

/**
 * This interface is a wrapper to manipulate messages through the MessageBroker.
 */
public class YoubetMessage {
    
    private byte[] body;
    
    public YoubetMessage(byte[] body) {
        this.body = body;
    }
    
    public YoubetMessage(Envelope envelope, byte[] body) {
        this.body = body;
    }
    
    
    public static YoubetMessage fromEvent(Object event) {
        return new YoubetMessage(JsonUtils.toJsonBytes(event));
    }
    
    public static YoubetMessage fromJson(ObjectNode jsonNode) {
        return new YoubetMessage(JsonUtils.toJsonBytes(jsonNode));
    }
    
    public byte[] getBody() {
        return body;
    }
    
    
    public JsonNode toJson() {
        return JsonUtils.parseBytes(body);
    }
    
    public <T> T toJson(Class<T> implClass) {
        return JsonUtils.parseBytes(body, implClass);
    }
}

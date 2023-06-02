package com.youbet.adapters.rabbitmq;

import com.youbet.ports.messagebroker.Consumer;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonUtils;

/**
 * This is an adapter that converts RabbitMQ messages from binary data to JSON.
 */
public class JsonConsumerAdapter implements Consumer {
    private final JsonConsumer jsonConsumer;
    
    public JsonConsumerAdapter(JsonConsumer jsonConsumer) {
        
        this.jsonConsumer = jsonConsumer;
    }
    
    @Override
    public void handleRequest(YoubetMessage event) {
        Object obj = JsonUtils.instantiate(jsonConsumer.supportedImpl(), event.getBody());
        jsonConsumer.handleRequest(event, obj);
    }
    
    @Override public String getConsumerTag() {
        return jsonConsumer.getConsumerTag();
    }
}

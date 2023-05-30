package com.youbet.ports.messagebroker;

import com.youbet.utils.JsonConsumer;

public interface MessageConsumerFactory {

    /**
     * Initializes a new message consumer by passing the queue and the implementation.
     *
     * @param queueName the queue name
     * @param agent1    the implementation
     * @param <T> the type of event consumed
     * @return the initialized consumer
     */
    public <T> JsonConsumer<T> newConsumer(String queueName, JsonConsumer<T> agent1);

}

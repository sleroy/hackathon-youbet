package com.youbet.utils;

import com.youbet.ports.messagebroker.YoubetMessage;

/**
 * This interface describes a component that receives an Event. The event is processed from the raw binaries as a JSON data stream and then converted into a Java object
 *
 * @param <E> the type of event
 */
public interface JsonConsumer<E> {
    
    /**
     * Returns the name of the consumer.
     *
     * @return the consumer tag
     */
    String getConsumerTag();
    
    
    /**
     * This method is called for each event
     * @param youbetMessage raw message
     * @param event payload parsed as a JSON 
     */
    void handleRequest(YoubetMessage youbetMessage, E event);
    
    
    Class<E> supportedImpl();
}

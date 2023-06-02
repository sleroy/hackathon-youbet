package com.youbet.ports.messagebroker;

/**
 * This interface describes a component that receives an Event:
 *
 */
public interface Consumer {

    /**
     * This method is called for each event
     */
    void handleRequest(YoubetMessage event);
    
    /**
     * Returns the name of the consumer.
     * @return the consumer tag
     */
    String getConsumerTag();
}

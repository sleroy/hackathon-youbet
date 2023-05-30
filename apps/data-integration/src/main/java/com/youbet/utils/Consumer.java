package com.youbet.utils;

import com.youbet.ports.messagebroker.YoubetMessage;

/**
 * This interface describes a component that receives an Event:
 *
 * @param <E> the type of event
 */
@FunctionalInterface
public interface Consumer<E> {

    /**
     * This method is called for each event
     */
    void handleRequest(E event);
}

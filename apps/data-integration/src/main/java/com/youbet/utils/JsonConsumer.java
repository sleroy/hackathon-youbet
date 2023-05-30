package com.youbet.utils;

/**
 * This interface describes a component that receives an Event. The event is processed from the raw binaries as a JSON data stream and then converted into a Java object
 *
 * @param <E> the type of event
 */
public interface JsonConsumer<E> extends Consumer<E> {

    Class<E> supportedImpl();
}

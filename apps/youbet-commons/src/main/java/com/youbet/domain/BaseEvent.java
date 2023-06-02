package com.youbet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEvent {
    
    private String eventName;
    
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    
    @Override public String toString() {
        return "BaseEvent{" +
                "eventName='" + eventName + '\'' +
                '}';
    }
}

package com.youbet.domain.requests;

import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;

public class MatchSystemMatchRegistrationRequest extends ExternalProviderMatchRegisteredEvent {
    private String eventName;
    private MatchReferences references;
    
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public MatchReferences getReferences() {
        return references;
    }
    
    public void setReferences(MatchReferences references) {
        this.references = references;
    }
    
    @Override public String toString() {
        return "MatchRegistrationRequest{" +
                "eventName='" + eventName + '\'' +
                ", references=" + references +
                '}';
    }
}

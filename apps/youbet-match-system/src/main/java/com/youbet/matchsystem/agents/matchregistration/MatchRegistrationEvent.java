package com.youbet.matchsystem.agents.matchregistration;

import com.youbet.domain.MatchReferenceBaseEvent;
import com.youbet.matchsystem.domain.MatchReferences;

public class MatchRegistrationEvent extends MatchReferenceBaseEvent {
    private String eventName;
    private MatchReferences references;
    
    public String getEventName()                          {return eventName;}
    
    public void setEventName(String eventName)            {this.eventName = eventName;}
    
    public MatchReferences getReferences()                {return references;}
    
    public void setReferences(MatchReferences references) {this.references = references;}
    
    
    @Override public String toString() {
        return "MatchRegistrationEvent{" +
                "eventName='" + eventName + '\'' +
                ", references=" + references +
                '}';
    }
}

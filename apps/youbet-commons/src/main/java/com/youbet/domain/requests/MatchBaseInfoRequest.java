package com.youbet.domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.youbet.domain.MatchBaseInfoView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchBaseInfoRequest extends MatchBaseInfoView {
    private String eventName;
    
    public String getEventName() {
        
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    @Override public String toString() {
        return "MatchBaseInfoEvent{" +
                "eventName='" + eventName + '\'' +
                '}';
    }
}

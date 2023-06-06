package com.youbet.matchsystem.agents.matchupdate;

import com.youbet.domain.MatchBaseInfoView;
import com.youbet.domain.MatchState;
import com.youbet.domain.requests.MatchReferences;

/**
 * This event updates an existing match with the state, the score. It also sends an event to the integration layer to produce the new entity graph.
 */
public class MatchUpdateInfo extends MatchBaseInfoView {
    private String eventName;
    private MatchReferences references;
    private MatchState state;
    private int score_team_home;
    private int score_team_away;
    
    public MatchState getState()           {return state;}
    
    public void setState(MatchState state) {this.state = state;}
    
    public int getScore_team_home()                     {return score_team_home;}
    
    public void setScore_team_home(int score_team_home) {this.score_team_home = score_team_home;}
    
    public int getScore_team_away()                     {return score_team_away;}
    
    public void setScore_team_away(int score_team_away) {this.score_team_away = score_team_away;}
    
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
        return "MatchRegistrationEvent{" +
                "eventName='" + eventName + '\'' +
                ", references=" + references +
                '}';
    }
}

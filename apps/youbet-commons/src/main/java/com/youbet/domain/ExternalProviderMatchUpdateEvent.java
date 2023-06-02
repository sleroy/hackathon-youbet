package com.youbet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProviderMatchUpdateEvent extends MatchReferenceBaseEvent {
    private MatchStateEnum state;
    private int score_team_home;
    private int score_team_away;
    
    public MatchStateEnum getState() {
        return state;
    }
    
    public void setState(MatchStateEnum state) {
        this.state = state;
    }
    
    public int getScore_team_home() {
        return score_team_home;
    }
    
    public void setScore_team_home(int score_team_home) {
        this.score_team_home = score_team_home;
    }
    
    public int getScore_team_away() {
        return score_team_away;
    }
    
    public void setScore_team_away(int score_team_away) {
        this.score_team_away = score_team_away;
    }
    
    @Override public String toString() {
        return "ExternalProviderMatchUpdateEvent{" +
                "state=" + state +
                ", score_team_home=" + score_team_home +
                ", score_team_away=" + score_team_away +
                '}';
    }
}

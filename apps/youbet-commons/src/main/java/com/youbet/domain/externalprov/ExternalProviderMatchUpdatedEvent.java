package com.youbet.domain.externalprov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.youbet.domain.MatchBaseInfoView;
import com.youbet.domain.MatchState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProviderMatchUpdatedEvent extends MatchBaseInfoView {
    private MatchState state;
    private Integer home_team_goal;
    private Integer away_team_goal;
    private String goal;
    private String shoton;
    private String shotoff;
    private String foulcommit;
    private String card;
    private String cross;
    private String corner;
    private String possession;
    
    public Integer getHome_team_goal() {
        return home_team_goal;
    }
    
    public void setHome_team_goal(Integer home_team_goal) {
        this.home_team_goal = home_team_goal;
    }
    
    public Integer getAway_team_goal() {
        return away_team_goal;
    }
    
    public void setAway_team_goal(Integer away_team_goal) {
        this.away_team_goal = away_team_goal;
    }
    
    public String getGoal() {
        return goal;
    }
    
    public void setGoal(String goal) {
        this.goal = goal;
    }
    
    public String getShoton() {
        return shoton;
    }
    
    public void setShoton(String shoton) {
        this.shoton = shoton;
    }
    
    public String getShotoff() {
        return shotoff;
    }
    
    public void setShotoff(String shotoff) {
        this.shotoff = shotoff;
    }
    
    public String getFoulcommit() {
        return foulcommit;
    }
    
    public void setFoulcommit(String foulcommit) {
        this.foulcommit = foulcommit;
    }
    
    public String getCard() {
        return card;
    }
    
    public void setCard(String card) {
        this.card = card;
    }
    
    public String getCross() {
        return cross;
    }
    
    public void setCross(String cross) {
        this.cross = cross;
    }
    
    public String getCorner() {
        return corner;
    }
    
    public void setCorner(String corner) {
        this.corner = corner;
    }
    
    public String getPossession() {
        return possession;
    }
    
    public void setPossession(String possession) {
        this.possession = possession;
    }
    
    public MatchState getState() {
        return state;
    }
    
    public void setState(MatchState state) {
        this.state = state;
    }
    
    @Override public String toString() {
        return "ExternalProviderMatchUpdateEvent{" +
                "state=" + state +
                ", home_team_goal=" + home_team_goal +
                ", away_team_goal=" + away_team_goal +
                ", goal=" + goal +
                ", shoton=" + shoton +
                ", shotoff=" + shotoff +
                ", foulcommit=" + foulcommit +
                ", card=" + card +
                ", cross=" + cross +
                ", corner=" + corner +
                ", possession=" + possession +
                '}';
    }
}

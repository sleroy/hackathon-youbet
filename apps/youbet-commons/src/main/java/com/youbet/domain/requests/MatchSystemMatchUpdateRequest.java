package com.youbet.domain.requests;

import com.youbet.domain.MatchBaseInfoView;
import com.youbet.domain.MatchState;

public class MatchSystemMatchUpdateRequest extends MatchBaseInfoView {
    private String eventName;
    private MatchReferences references;
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
    private MatchState state;
    
    public MatchState getState() {
        return state;
    }
    
    public void setState(MatchState state) {
        this.state = state;
    }
    
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
    
}

package com.youbet.agents.domain;

public class League {
    private Integer leagueId;
    private String leagueName;
    
    public String getLeagueName() {
        return leagueName;
    }
    
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
    
    public Integer getLeagueId() {
        return leagueId;
    }
    
    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }
    
    
    @Override public String toString() {
        return "League{" +
                "leagueName='" + leagueName + '\'' +
                ", leagueId=" + leagueId +
                '}';
    }
}

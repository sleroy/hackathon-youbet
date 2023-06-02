package com.youbet.matchsystem.domain;

public class MatchReferences {
    private Integer league;
    private Integer homeTeam;
    private Integer awayTeam;
    
    public Integer getLeague() {
        return league;
    }
    
    public void setLeague(Integer league) {
        this.league = league;
    }
    
    public Integer getHomeTeam() {
        return homeTeam;
    }
    
    public void setHomeTeam(Integer homeTeam) {
        this.homeTeam = homeTeam;
    }
    
    public Integer getAwayTeam() {
        return awayTeam;
    }
    
    public void setAwayTeam(Integer awayTeam) {
        this.awayTeam = awayTeam;
    }
    
    @Override public String toString() {
        return "MatchReferences{" +
                "league=" + league +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                '}';
    }
}

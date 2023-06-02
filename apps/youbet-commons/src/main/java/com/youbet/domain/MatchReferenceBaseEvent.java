package com.youbet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchReferenceBaseEvent {
    public static final String ATTR_LEAGUE = "league";
    public static final String ATTR_REFERENCES = "references";
    public static final String ATTR_HOME_TEAM = "homeTeam";
    public static final String ATTR_AWAY_TEAM = "awayTeam";
    
    
    private String matchName;
    private LocalDateTime date;
    private String league;
    private String country;
    private String homeTeam;
    private String awayTeam;
    
    public String getHomeTeam() {
        
        return homeTeam;
    }
    
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }
    
    public String getAwayTeam() {
        return awayTeam;
    }
    
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
    
    
    public String getMatchName() {
        return matchName;
    }
    
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public String getLeague() {
        return league;
    }
    
    public void setLeague(String league) {
        this.league = league;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override public String toString() {
        return "MatchReferenceBaseEvent{" +
                "matchName='" + matchName + '\'' +
                ", date=" + date +
                ", league='" + league + '\'' +
                ", country='" + country + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                '}';
    }
}

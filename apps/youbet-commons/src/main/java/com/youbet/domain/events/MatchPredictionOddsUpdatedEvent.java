package com.youbet.domain.events;

import java.time.LocalDateTime;

public class MatchPredictionOddsUpdatedEvent {
    private LocalDateTime date;
    private Integer league;
    private Integer country;
    private String season;
    private Double homeWin;
    private Double awayWin;
    private Double stalemate;
    
    public Double getHomeWin() {
        return homeWin;
    }
    
    public void setHomeWin(Double homeWin) {
        this.homeWin = homeWin;
    }
    
    public Double getAwayWin() {
        return awayWin;
    }
    
    public void setAwayWin(Double awayWin) {
        this.awayWin = awayWin;
    }
    
    public Double getStalemate() {
        return stalemate;
    }
    
    public void setStalemate(Double stalemate) {
        this.stalemate = stalemate;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public Integer getLeague() {
        return league;
    }
    
    public void setLeague(Integer league) {
        this.league = league;
    }
    
    public Integer getCountry() {
        return country;
    }
    
    public void setCountry(Integer country) {
        this.country = country;
    }
    
    public String getSeason() {
        return season;
    }
    
    public void setSeason(String season) {
        this.season = season;
    }
}

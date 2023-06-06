package com.youbet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchBaseInfoView {
    public static final String ATTR_REFERENCES = "references";
    
    private LocalDateTime date;
    private String league;
    private String country;
    private String homeTeam;
    private String awayTeam;
    private String season;
    private Integer stage;
    
    public Integer getStage() {
        return stage;
    }
    
    public void setStage(Integer stage) {
        this.stage = stage;
    }
    
    public String getSeason() {
        return season;
    }
    
    public void setSeason(String season) {
        this.season = season;
    }
    
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
    
    @Override public String toString() {
        return new StringJoiner(", ", MatchBaseInfoView.class.getSimpleName() + "[", "]")
                .add("date=" + date)
                .add("league='" + league + "'")
                .add("country='" + country + "'")
                .add("homeTeam='" + homeTeam + "'")
                .add("awayTeam='" + awayTeam + "'")
                .add("season='" + season + "'")
                .add("stage='" + stage + "'")
                .toString();
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
    
}

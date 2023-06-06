package com.youbet.domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true) public class MatchPredictionRequest {
    private LocalDateTime date;
    private String league;
    private String country;
    private String season;
    
    @Override public String toString() {
        return new StringJoiner(", ", MatchPredictionRequest.class.getSimpleName() + "[", "]").add("date=" + date)
                                                                                              .add("league='" + league + "'")
                                                                                              .add("country='" + country + "'")
                                                                                              .add("season='" + season + "'")
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
    
    public String getSeason() {
        return season;
    }
    
    public void setSeason(String season) {
        this.season = season;
    }
}

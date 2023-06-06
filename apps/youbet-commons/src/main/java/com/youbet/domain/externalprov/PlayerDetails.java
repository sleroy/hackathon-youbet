package com.youbet.domain.externalprov;

import java.time.LocalDate;

public class PlayerDetails {
    private String player_name;
    private LocalDate birthday;
    private Integer height;
    private Integer weight;
    
    public PlayerDetails() {
        
    }
    
    public PlayerDetails(String player_name, LocalDate birthday, Integer height, Integer weight) {
        this.player_name = player_name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
    }
    
    public String getPlayerName() {
        return player_name;
    }
    
    public void setPlayerName(String playerName) {
        this.player_name = playerName;
    }
    
    public LocalDate getBirthday() {
        return birthday;
    }
    
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
    public Integer getHeight() {
        return height;
    }
    
    public void setHeight(Integer height) {
        this.height = height;
    }
    
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}

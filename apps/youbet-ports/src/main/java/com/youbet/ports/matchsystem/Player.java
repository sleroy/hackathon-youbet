package com.youbet.ports.matchsystem;

import java.time.LocalDate;

public class Player {
    private Integer id;
    private String player_api_id;
    private String player_name;
    private LocalDate birthday;
    private Integer height;
    private Integer weight;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPlayer_api_id() {
        return player_api_id;
    }
    
    public void setPlayer_api_id(String player_api_id) {
        this.player_api_id = player_api_id;
    }
    
    public String getPlayer_name() {
        return player_name;
    }
    
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
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

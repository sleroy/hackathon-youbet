package com.youbet.ports.matchsystem;

public class Team {
    private Integer id;
    private String team_api_id;
    private String name;
    private String short_name;
    
    public String getShort_name() {
        return short_name;
    }
    
    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
    
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTeam_api_id() {
        return team_api_id;
    }
    
    public void setTeam_api_id(String team_api_id) {
        this.team_api_id = team_api_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortName() {
        return short_name;
    }
    
    public void setShortName(String shortName) {
        this.short_name = shortName;
    }
    
    @Override public String toString() {
        return "Team{" +
                "id=" + id +
                ", team_api_id='" + team_api_id + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                '}';
    }
}

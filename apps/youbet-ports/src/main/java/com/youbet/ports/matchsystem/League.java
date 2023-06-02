package com.youbet.ports.matchsystem;

public class League {
    private Integer id;
    private String name;
    private Integer country_id;
    private String league_api_id;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getCountry_id() {
        return country_id;
    }
    
    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }
    
    public String getLeague_api_id() {
        return league_api_id;
    }
    
    public void setLeague_api_id(String league_api_id) {
        this.league_api_id = league_api_id;
    }
    
    @Override public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country_id=" + country_id +
                ", league_api_id='" + league_api_id + '\'' +
                '}';
    }
}

package com.youbet.ports.matchsystem;

import com.youbet.domain.MatchState;

import java.time.LocalDateTime;

public class Match {
    private Integer id;
    private Integer country_id;
    private Integer league_id;
    private String match_api_id;
    private String season;
    private Integer stage;
    private MatchState state = MatchState.planned;
    private LocalDateTime date;
    private Integer home_team_api_id;
    private Integer away_team_api_id;
    private Integer score_team_away;
    private Integer score_team_home;
    private Integer home_player_X1;
    private Integer home_player_X2;
    private Integer home_player_X3;
    private Integer home_player_X4;
    private Integer home_player_X5;
    private Integer home_player_X6;
    private Integer home_player_X7;
    private Integer home_player_X8;
    private Integer home_player_X9;
    private Integer home_player_X10;
    private Integer home_player_X11;
    private Integer away_player_X1;
    private Integer away_player_X2;
    private Integer away_player_X3;
    private Integer away_player_X4;
    private Integer away_player_X5;
    private Integer away_player_X6;
    private Integer away_player_X7;
    private Integer away_player_X8;
    private Integer away_player_X9;
    private Integer away_player_X10;
    private Integer away_player_X11;
    private Integer home_player_Y1;
    private Integer home_player_Y2;
    private Integer home_player_Y3;
    private Integer home_player_Y4;
    private Integer home_player_Y5;
    private Integer home_player_Y6;
    private Integer home_player_Y7;
    private Integer home_player_Y8;
    private Integer home_player_Y9;
    private Integer home_player_Y10;
    private Integer home_player_Y11;
    private Integer away_player_Y1;
    private Integer away_player_Y2;
    private Integer away_player_Y3;
    private Integer away_player_Y4;
    private Integer away_player_Y5;
    private Integer away_player_Y6;
    private Integer away_player_Y7;
    private Integer away_player_Y8;
    private Integer away_player_Y9;
    private Integer away_player_Y10;
    private Integer away_player_Y11;
    private Integer home_player_1;
    private Integer home_player_2;
    private Integer home_player_3;
    private Integer home_player_4;
    private Integer home_player_5;
    private Integer home_player_6;
    private Integer home_player_7;
    private Integer home_player_8;
    private Integer home_player_9;
    private Integer home_player_10;
    private Integer home_player_11;
    private Integer away_player_1;
    private Integer away_player_2;
    private Integer away_player_3;
    private Integer away_player_4;
    private Integer away_player_5;
    private Integer away_player_6;
    private Integer away_player_7;
    private Integer away_player_8;
    private Integer away_player_9;
    private Integer away_player_10;
    private Integer away_player_11;
    private String goal;
    private String shoton;
    private String shotoff;
    private String foulcommit;
    private String card;
    private String cross;
    private String corner;
    private String possession;

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }
    
    public String getMatch_api_id() {
        return match_api_id;
    }
    
    public void setMatch_api_id(String match_api_id) {
        this.match_api_id = match_api_id;
    }
    
    public String getGoal() {
        return goal;
    }
    
    public void setGoal(String goal) {
        this.goal = goal;
    }
    
    public String getShoton() {
        return shoton;
    }
    
    public void setShoton(String shoton) {
        this.shoton = shoton;
    }
    
    public String getShotoff() {
        return shotoff;
    }
    
    public void setShotoff(String shotoff) {
        this.shotoff = shotoff;
    }
    
    public String getFoulcommit() {
        return foulcommit;
    }
    
    public void setFoulcommit(String foulcommit) {
        this.foulcommit = foulcommit;
    }
    
    public String getCard() {
        return card;
    }
    
    public void setCard(String card) {
        this.card = card;
    }
    
    public String getCross() {
        return cross;
    }
    
    public void setCross(String cross) {
        this.cross = cross;
    }
    
    public String getCorner() {
        return corner;
    }
    
    public void setCorner(String corner) {
        this.corner = corner;
    }
    
    public String getPossession() {
        return possession;
    }
    
    public void setPossession(String possession) {
        this.possession = possession;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getCountry_id() {
        return country_id;
    }
    
    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }
    
    public Integer getLeague_id() {
        return league_id;
    }
    
    public void setLeague_id(Integer league_id) {
        this.league_id = league_id;
    }
    
    public String getSeason() {
        return season;
    }
    
    public void setSeason(String season) {
        this.season = season;
    }
    
    public Integer getStage() {
        return stage;
    }
    
    public void setStage(Integer stage) {
        this.stage = stage;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public Integer getHome_team_api_id() {
        return home_team_api_id;
    }
    
    public void setHome_team_api_id(Integer home_team_api_id) {
        this.home_team_api_id = home_team_api_id;
    }
    
    public Integer getAway_team_api_id() {
        return away_team_api_id;
    }
    
    public void setAway_team_api_id(Integer away_team_api_id) {
        this.away_team_api_id = away_team_api_id;
    }
    
    public Integer getScore_team_away() {
        return score_team_away;
    }
    
    public void setScore_team_away(Integer score_team_away) {
        this.score_team_away = score_team_away;
    }
    
    public Integer getScore_team_home() {
        return score_team_home;
    }
    
    public void setScore_team_home(Integer score_team_home) {
        this.score_team_home = score_team_home;
    }
    
    public Integer getHome_player_X1() {
        return home_player_X1;
    }
    
    public void setHome_player_X1(Integer home_player_X1) {
        this.home_player_X1 = home_player_X1;
    }
    
    public Integer getHome_player_X2() {
        return home_player_X2;
    }
    
    public void setHome_player_X2(Integer home_player_X2) {
        this.home_player_X2 = home_player_X2;
    }
    
    public Integer getHome_player_X3() {
        return home_player_X3;
    }
    
    public void setHome_player_X3(Integer home_player_X3) {
        this.home_player_X3 = home_player_X3;
    }
    
    public Integer getHome_player_X4() {
        return home_player_X4;
    }
    
    public void setHome_player_X4(Integer home_player_X4) {
        this.home_player_X4 = home_player_X4;
    }
    
    public Integer getHome_player_X5() {
        return home_player_X5;
    }
    
    public void setHome_player_X5(Integer home_player_X5) {
        this.home_player_X5 = home_player_X5;
    }
    
    public Integer getHome_player_X6() {
        return home_player_X6;
    }
    
    public void setHome_player_X6(Integer home_player_X6) {
        this.home_player_X6 = home_player_X6;
    }
    
    public Integer getHome_player_X7() {
        return home_player_X7;
    }
    
    public void setHome_player_X7(Integer home_player_X7) {
        this.home_player_X7 = home_player_X7;
    }
    
    public Integer getHome_player_X8() {
        return home_player_X8;
    }
    
    public void setHome_player_X8(Integer home_player_X8) {
        this.home_player_X8 = home_player_X8;
    }
    
    public Integer getHome_player_X9() {
        return home_player_X9;
    }
    
    public void setHome_player_X9(Integer home_player_X9) {
        this.home_player_X9 = home_player_X9;
    }
    
    public Integer getHome_player_X10() {
        return home_player_X10;
    }
    
    public void setHome_player_X10(Integer home_player_X10) {
        this.home_player_X10 = home_player_X10;
    }
    
    public Integer getHome_player_X11() {
        return home_player_X11;
    }
    
    public void setHome_player_X11(Integer home_player_X11) {
        this.home_player_X11 = home_player_X11;
    }
    
    public Integer getAway_player_X1() {
        return away_player_X1;
    }
    
    public void setAway_player_X1(Integer away_player_X1) {
        this.away_player_X1 = away_player_X1;
    }
    
    public Integer getAway_player_X2() {
        return away_player_X2;
    }
    
    public void setAway_player_X2(Integer away_player_X2) {
        this.away_player_X2 = away_player_X2;
    }
    
    public Integer getAway_player_X3() {
        return away_player_X3;
    }
    
    public void setAway_player_X3(Integer away_player_X3) {
        this.away_player_X3 = away_player_X3;
    }
    
    public Integer getAway_player_X4() {
        return away_player_X4;
    }
    
    public void setAway_player_X4(Integer away_player_X4) {
        this.away_player_X4 = away_player_X4;
    }
    
    public Integer getAway_player_X5() {
        return away_player_X5;
    }
    
    public void setAway_player_X5(Integer away_player_X5) {
        this.away_player_X5 = away_player_X5;
    }
    
    public Integer getAway_player_X6() {
        return away_player_X6;
    }
    
    public void setAway_player_X6(Integer away_player_X6) {
        this.away_player_X6 = away_player_X6;
    }
    
    public Integer getAway_player_X7() {
        return away_player_X7;
    }
    
    public void setAway_player_X7(Integer away_player_X7) {
        this.away_player_X7 = away_player_X7;
    }
    
    public Integer getAway_player_X8() {
        return away_player_X8;
    }
    
    public void setAway_player_X8(Integer away_player_X8) {
        this.away_player_X8 = away_player_X8;
    }
    
    public Integer getAway_player_X9() {
        return away_player_X9;
    }
    
    public void setAway_player_X9(Integer away_player_X9) {
        this.away_player_X9 = away_player_X9;
    }
    
    public Integer getAway_player_X10() {
        return away_player_X10;
    }
    
    public void setAway_player_X10(Integer away_player_X10) {
        this.away_player_X10 = away_player_X10;
    }
    
    public Integer getAway_player_X11() {
        return away_player_X11;
    }
    
    public void setAway_player_X11(Integer away_player_X11) {
        this.away_player_X11 = away_player_X11;
    }
    
    public Integer getHome_player_Y1() {
        return home_player_Y1;
    }
    
    public void setHome_player_Y1(Integer home_player_Y1) {
        this.home_player_Y1 = home_player_Y1;
    }
    
    public Integer getHome_player_Y2() {
        return home_player_Y2;
    }
    
    public void setHome_player_Y2(Integer home_player_Y2) {
        this.home_player_Y2 = home_player_Y2;
    }
    
    public Integer getHome_player_Y3() {
        return home_player_Y3;
    }
    
    public void setHome_player_Y3(Integer home_player_Y3) {
        this.home_player_Y3 = home_player_Y3;
    }
    
    public Integer getHome_player_Y4() {
        return home_player_Y4;
    }
    
    public void setHome_player_Y4(Integer home_player_Y4) {
        this.home_player_Y4 = home_player_Y4;
    }
    
    public Integer getHome_player_Y5() {
        return home_player_Y5;
    }
    
    public void setHome_player_Y5(Integer home_player_Y5) {
        this.home_player_Y5 = home_player_Y5;
    }
    
    public Integer getHome_player_Y6() {
        return home_player_Y6;
    }
    
    public void setHome_player_Y6(Integer home_player_Y6) {
        this.home_player_Y6 = home_player_Y6;
    }
    
    public Integer getHome_player_Y7() {
        return home_player_Y7;
    }
    
    public void setHome_player_Y7(Integer home_player_Y7) {
        this.home_player_Y7 = home_player_Y7;
    }
    
    public Integer getHome_player_Y8() {
        return home_player_Y8;
    }
    
    public void setHome_player_Y8(Integer home_player_Y8) {
        this.home_player_Y8 = home_player_Y8;
    }
    
    public Integer getHome_player_Y9() {
        return home_player_Y9;
    }
    
    public void setHome_player_Y9(Integer home_player_Y9) {
        this.home_player_Y9 = home_player_Y9;
    }
    
    public Integer getHome_player_Y10() {
        return home_player_Y10;
    }
    
    public void setHome_player_Y10(Integer home_player_Y10) {
        this.home_player_Y10 = home_player_Y10;
    }
    
    public Integer getHome_player_Y11() {
        return home_player_Y11;
    }
    
    public void setHome_player_Y11(Integer home_player_Y11) {
        this.home_player_Y11 = home_player_Y11;
    }
    
    public Integer getAway_player_Y1() {
        return away_player_Y1;
    }
    
    public void setAway_player_Y1(Integer away_player_Y1) {
        this.away_player_Y1 = away_player_Y1;
    }
    
    public Integer getAway_player_Y2() {
        return away_player_Y2;
    }
    
    public void setAway_player_Y2(Integer away_player_Y2) {
        this.away_player_Y2 = away_player_Y2;
    }
    
    public Integer getAway_player_Y3() {
        return away_player_Y3;
    }
    
    public void setAway_player_Y3(Integer away_player_Y3) {
        this.away_player_Y3 = away_player_Y3;
    }
    
    public Integer getAway_player_Y4() {
        return away_player_Y4;
    }
    
    public void setAway_player_Y4(Integer away_player_Y4) {
        this.away_player_Y4 = away_player_Y4;
    }
    
    public Integer getAway_player_Y5() {
        return away_player_Y5;
    }
    
    public void setAway_player_Y5(Integer away_player_Y5) {
        this.away_player_Y5 = away_player_Y5;
    }
    
    public Integer getAway_player_Y6() {
        return away_player_Y6;
    }
    
    public void setAway_player_Y6(Integer away_player_Y6) {
        this.away_player_Y6 = away_player_Y6;
    }
    
    public Integer getAway_player_Y7() {
        return away_player_Y7;
    }
    
    public void setAway_player_Y7(Integer away_player_Y7) {
        this.away_player_Y7 = away_player_Y7;
    }
    
    public Integer getAway_player_Y8() {
        return away_player_Y8;
    }
    
    public void setAway_player_Y8(Integer away_player_Y8) {
        this.away_player_Y8 = away_player_Y8;
    }
    
    public Integer getAway_player_Y9() {
        return away_player_Y9;
    }
    
    public void setAway_player_Y9(Integer away_player_Y9) {
        this.away_player_Y9 = away_player_Y9;
    }
    
    public Integer getAway_player_Y10() {
        return away_player_Y10;
    }
    
    public void setAway_player_Y10(Integer away_player_Y10) {
        this.away_player_Y10 = away_player_Y10;
    }
    
    public Integer getAway_player_Y11() {
        return away_player_Y11;
    }
    
    public void setAway_player_Y11(Integer away_player_Y11) {
        this.away_player_Y11 = away_player_Y11;
    }
    
    public Integer getHome_player_1() {
        return home_player_1;
    }
    
    public void setHome_player_1(Integer home_player_1) {
        this.home_player_1 = home_player_1;
    }
    
    public Integer getHome_player_2() {
        return home_player_2;
    }
    
    public void setHome_player_2(Integer home_player_2) {
        this.home_player_2 = home_player_2;
    }
    
    public Integer getHome_player_3() {
        return home_player_3;
    }
    
    public void setHome_player_3(Integer home_player_3) {
        this.home_player_3 = home_player_3;
    }
    
    public Integer getHome_player_4() {
        return home_player_4;
    }
    
    public void setHome_player_4(Integer home_player_4) {
        this.home_player_4 = home_player_4;
    }
    
    public Integer getHome_player_5() {
        return home_player_5;
    }
    
    public void setHome_player_5(Integer home_player_5) {
        this.home_player_5 = home_player_5;
    }
    
    public Integer getHome_player_6() {
        return home_player_6;
    }
    
    public void setHome_player_6(Integer home_player_6) {
        this.home_player_6 = home_player_6;
    }
    
    public Integer getHome_player_7() {
        return home_player_7;
    }
    
    public void setHome_player_7(Integer home_player_7) {
        this.home_player_7 = home_player_7;
    }
    
    public Integer getHome_player_8() {
        return home_player_8;
    }
    
    public void setHome_player_8(Integer home_player_8) {
        this.home_player_8 = home_player_8;
    }
    
    public Integer getHome_player_9() {
        return home_player_9;
    }
    
    public void setHome_player_9(Integer home_player_9) {
        this.home_player_9 = home_player_9;
    }
    
    public Integer getHome_player_10() {
        return home_player_10;
    }
    
    public void setHome_player_10(Integer home_player_10) {
        this.home_player_10 = home_player_10;
    }
    
    public Integer getHome_player_11() {
        return home_player_11;
    }
    
    public void setHome_player_11(Integer home_player_11) {
        this.home_player_11 = home_player_11;
    }
    
    public Integer getAway_player_1() {
        return away_player_1;
    }
    
    public void setAway_player_1(Integer away_player_1) {
        this.away_player_1 = away_player_1;
    }
    
    public Integer getAway_player_2() {
        return away_player_2;
    }
    
    public void setAway_player_2(Integer away_player_2) {
        this.away_player_2 = away_player_2;
    }
    
    public Integer getAway_player_3() {
        return away_player_3;
    }
    
    public void setAway_player_3(Integer away_player_3) {
        this.away_player_3 = away_player_3;
    }
    
    public Integer getAway_player_4() {
        return away_player_4;
    }
    
    public void setAway_player_4(Integer away_player_4) {
        this.away_player_4 = away_player_4;
    }
    
    public Integer getAway_player_5() {
        return away_player_5;
    }
    
    public void setAway_player_5(Integer away_player_5) {
        this.away_player_5 = away_player_5;
    }
    
    public Integer getAway_player_6() {
        return away_player_6;
    }
    
    public void setAway_player_6(Integer away_player_6) {
        this.away_player_6 = away_player_6;
    }
    
    public Integer getAway_player_7() {
        return away_player_7;
    }
    
    public void setAway_player_7(Integer away_player_7) {
        this.away_player_7 = away_player_7;
    }
    
    public Integer getAway_player_8() {
        return away_player_8;
    }
    
    public void setAway_player_8(Integer away_player_8) {
        this.away_player_8 = away_player_8;
    }
    
    public Integer getAway_player_9() {
        return away_player_9;
    }
    
    public void setAway_player_9(Integer away_player_9) {
        this.away_player_9 = away_player_9;
    }
    
    public Integer getAway_player_10() {
        return away_player_10;
    }
    
    public void setAway_player_10(Integer away_player_10) {
        this.away_player_10 = away_player_10;
    }
    
    public Integer getAway_player_11() {
        return away_player_11;
    }
    
    public void setAway_player_11(Integer away_player_11) {
        this.away_player_11 = away_player_11;
    }
}
package com.example.youbet;

import com.youbet.domain.externalprov.PlayerDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SqliteMatchMapper {
    
    @Select("""
            SELECT
                m.id as id,
                c.name as country,
                l.name as league,
                season as season,
                stage as stage,
                date as date,
                thome.team_long_name as homeTeam,
                taway .team_long_name as awayTeam,
                home_team_goal, 
                away_team_goal, 
                home_player_X1, 
                home_player_X2, 
                home_player_X3, 
                home_player_X4, 
                home_player_X5, 
                home_player_X6, 
                home_player_X7, 
                home_player_X8, 
                home_player_X9, 
                home_player_X10, 
                home_player_X11, 
                away_player_X1, 
                away_player_X2, 
                away_player_X3, 
                away_player_X4, 
                away_player_X5, 
                away_player_X6, 
                away_player_X7, 
                away_player_X8, 
                away_player_X9, 
                away_player_X10, 
                away_player_X11, 
                home_player_Y1, 
                home_player_Y2, 
                home_player_Y3, 
                home_player_Y4, 
                home_player_Y5, 
                home_player_Y6, 
                home_player_Y7, 
                home_player_Y8, 
                home_player_Y9, 
                home_player_Y10, 
                home_player_Y11, 
                away_player_Y1, 
                away_player_Y2, 
                away_player_Y3, 
                away_player_Y4, 
                away_player_Y5, 
                away_player_Y6, 
                away_player_Y7, 
                away_player_Y8, 
                away_player_Y9, 
                away_player_Y10, 
                away_player_Y11, 
                home_player_1, 
                home_player_2, 
                home_player_3, 
                home_player_4, 
                home_player_5, 
                home_player_6, 
                home_player_7, 
                home_player_8, 
                home_player_9, 
                home_player_10, 
                home_player_11, 
                away_player_1, 
                away_player_2, 
                away_player_3, 
                away_player_4, 
                away_player_5, 
                away_player_6, 
                away_player_7, 
                away_player_8, 
                away_player_9, 
                away_player_10, 
                away_player_11, 
                m.goal as goal, 
                shoton as shoton, 
                shotoff as shotoff, 
                foulcommit as foulcommit, 
                card as card, 
                "cross" as "cross", 
                corner as corner, 
                possession as possession
                FROM
                    "Match" m
                LEFT JOIN Team thome ON
                    m.home_team_api_id = thome.team_api_id
                LEFT JOIN Team taway ON
                    m.away_team_api_id = taway.team_api_id
                LEFT JOIN Country c ON
                    m.country_id = c.id
                LEFT JOIN League l ON
                    m.league_id = l.id;
            """)
    public List<SqliteMatch> fetchAll();
    
    @Select("SELECT * FROM Match")
    public List<SqliteMatch> selectAll();
    
    
    @Select("SELECT * FROM PLAYER WHERE id=#{playerId}")
    public PlayerDetails getPlayer(@Param("playerId") Integer playerId);
}

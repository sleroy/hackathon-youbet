package com.youbet.adapters.mysql.mappers;

import com.youbet.domain.MatchState;
import com.youbet.domain.requests.MatchPredictionRequest;
import com.youbet.ports.matchsystem.Match;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This mapper stores all the match with the details and the odds.
 */
public interface MatchMapper {
    
    @Insert("INSERT INTO matchsystem.`Match`\n" +
            "(country_id, league_id, match_api_id, season, state, `date`, home_team_api_id, away_team_api_id, score_team_home,\n" +
            " score_team_away, home_player_X1, home_player_X2, home_player_X3, home_player_X4, home_player_X5, home_player_X6,\n" +
            " home_player_X7, home_player_X8, home_player_X9, home_player_X10, home_player_X11, away_player_X1, away_player_X2,\n" +
            " away_player_X3, away_player_X4, away_player_X5, away_player_X6, away_player_X7, away_player_X8, away_player_X9,\n" +
            " away_player_X10, away_player_X11, home_player_Y1, home_player_Y2, home_player_Y3, home_player_Y4, home_player_Y5,\n" +
            " home_player_Y6, home_player_Y7, home_player_Y8, home_player_Y9, home_player_Y10, home_player_Y11, away_player_Y1,\n" +
            " away_player_Y2, away_player_Y3, away_player_Y4, away_player_Y5, away_player_Y6, away_player_Y7, away_player_Y8,\n" +
            " away_player_Y9, away_player_Y10, away_player_Y11, home_player_1, home_player_2, home_player_3, home_player_4,\n" +
            " home_player_5, home_player_6, home_player_7, home_player_8, home_player_9, home_player_10, home_player_11,\n" +
            " away_player_1, away_player_2, away_player_3, away_player_4, away_player_5, away_player_6, away_player_7, away_player_8,\n" +
            " away_player_9, away_player_10, away_player_11, goal, shoton, shotoff, foulcommit, card, `cross`, corner, possession,\n" +
            " homewin, awaywin, stalemate)\n" +
            "VALUES(#{country_id}, #{league_id}, #{match_api_id},#{season}, #{state}, #{date}, #{home_team_api_id}, #{away_team_api_id}, #{score_team_home}, #{score_team_away}, #{home_player_X1}, #{home_player_X2}, #{home_player_X3}, #{home_player_X4}, #{home_player_X5}, #{home_player_X6}, #{home_player_X7}, #{home_player_X8}, #{home_player_X9}, #{home_player_X10}, #{home_player_X11}, #{away_player_X1}, #{away_player_X2}, #{away_player_X3}, #{away_player_X4}, #{away_player_X5}, #{away_player_X6}, #{away_player_X7}, #{away_player_X8}, #{away_player_X9}, #{away_player_X10}, #{away_player_X11}, #{home_player_Y1}, #{home_player_Y2}, #{home_player_Y3}, #{home_player_Y4}, #{home_player_Y5}, #{home_player_Y6}, #{home_player_Y7}, #{home_player_Y8}, #{home_player_Y9}, #{home_player_Y10}, #{home_player_Y11}, #{away_player_Y1}, #{away_player_Y2}, #{away_player_Y3}, #{away_player_Y4}, #{away_player_Y5}, #{away_player_Y6}, #{away_player_Y7}, #{away_player_Y8}, #{away_player_Y9}, #{away_player_Y10}, #{away_player_Y11}, #{home_player_1}, #{home_player_2}, #{home_player_3}, #{home_player_4}, #{home_player_5}, #{home_player_6}, #{home_player_7}, #{home_player_8}, #{home_player_9}, #{home_player_10}, #{home_player_11}, #{away_player_1}, #{away_player_2}, #{away_player_3}, #{away_player_4}, #{away_player_5}, #{away_player_6}, #{away_player_7}, #{away_player_8}, #{away_player_9}, #{away_player_10}, #{away_player_11}, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createMatch(Match player);
    
    @Insert("UPDATE Match\n" +
            "SET score_team_home=#{home_team_goal}, score_team_away=#{away_team_goal}, goal=#{goal}, shoton=#{shoton}, shotoff=#{shotoff}, foulcommit=#{foulcommit}, card=#{card}, cross=#{cross}, corner=#{corner}, possession=#{possession}\n" +
            "WHERE league_id=#{league} AND season=#{season} AND country_id=#{countryId} AND date=#{date};\n")
    void updateMatch(@Param("countryId") Integer countryId,
                     @Param("league") Integer league,
                     @Param("season") String season,
                     @Param("date") LocalDateTime date,
                     @Param("home_team_goal") Integer home_team_goal,
                     @Param("away_team_goal") Integer away_team_goal,
                     @Param("goal") String goal,
                     @Param("shoton") String shoton,
                     @Param("shotoff") String shotoff,
                     @Param("foulcommit") String foulcommit,
                     @Param("card") String card,
                     @Param("cross") String cross,
                     @Param("corner") String corner,
                     @Param("possession") String possession,
                     @Param("state")MatchState state);
    
    @Select("SELECT * FROM Match")
    @Results(id = "Match",
             value = {
                     @Result(column = "id", property = "id"),
                     @Result(column = "country_id", property = "country_id"),
                     @Result(column = "league_id", property = "league_id"),
                     @Result(column = "season", property = "season"),
                     @Result(column = "date", property = "date"),
                     @Result(column = "home_team_api_id", property = "home_team_api_id"),
                     @Result(column = "away_team_api_id", property = "away_team_api_id"),
                     @Result(column = "score_team_home", property = "score_team_home"),
                     @Result(column = "score_team_away", property = "score_team_away"),
            
                     @Result(column = "goal", property = "score_team_home"),
                     @Result(column = "shoton", property = "score_team_away"),
                     @Result(column = "shotoff", property = "shotoff"),
                     @Result(column = "foulcommit", property = "foulcommit"),
                     @Result(column = "card", property = "card"),
                     @Result(column = "cross", property = "cross"),
                     @Result(column = "corner", property = "corner"),
                     @Result(column = "possession", property = "possession"),
            
            
                     @Result(column = "home_player_1", property = "home_player_1"),
                     @Result(column = "home_player_2", property = "home_player_2"),
                     @Result(column = "home_player_3", property = "home_player_3"),
                     @Result(column = "home_player_4", property = "home_player_4"),
                     @Result(column = "home_player_5", property = "home_player_5"),
                     @Result(column = "home_player_6", property = "home_player_6"),
                     @Result(column = "home_player_7", property = "home_player_7"),
                     @Result(column = "home_player_8", property = "home_player_8"),
                     @Result(column = "home_player_9", property = "home_player_9"),
                     @Result(column = "home_player_10", property = "home_player_10"),
                     @Result(column = "home_player_11", property = "home_player_11"),
            
                     @Result(column = "home_player_X1", property = "home_player_X1"),
                     @Result(column = "home_player_X2", property = "home_player_X2"),
                     @Result(column = "home_player_X3", property = "home_player_X3"),
                     @Result(column = "home_player_X4", property = "home_player_X4"),
                     @Result(column = "home_player_X5", property = "home_player_X5"),
                     @Result(column = "home_player_X6", property = "home_player_X6"),
                     @Result(column = "home_player_X7", property = "home_player_X7"),
                     @Result(column = "home_player_X8", property = "home_player_X8"),
                     @Result(column = "home_player_X9", property = "home_player_X9"),
                     @Result(column = "home_player_X10", property = "home_player_X10"),
                     @Result(column = "home_player_X11", property = "home_player_X11"),
                     @Result(column = "home_player_Y1", property = "home_player_Y1"),
                     @Result(column = "home_player_Y2", property = "home_player_Y2"),
                     @Result(column = "home_player_Y3", property = "home_player_Y3"),
                     @Result(column = "home_player_Y4", property = "home_player_Y4"),
                     @Result(column = "home_player_Y5", property = "home_player_Y5"),
                     @Result(column = "home_player_Y6", property = "home_player_Y6"),
                     @Result(column = "home_player_Y7", property = "home_player_Y7"),
                     @Result(column = "home_player_Y8", property = "home_player_Y8"),
                     @Result(column = "home_player_Y9", property = "home_player_Y9"),
                     @Result(column = "home_player_Y10", property = "home_player_Y10"),
                     @Result(column = "home_player_Y11", property = "home_player_Y11"),
            
            
                     @Result(column = "away_player_1", property = "away_player_1"),
                     @Result(column = "away_player_2", property = "away_player_2"),
                     @Result(column = "away_player_3", property = "away_player_3"),
                     @Result(column = "away_player_4", property = "away_player_4"),
                     @Result(column = "away_player_5", property = "away_player_5"),
                     @Result(column = "away_player_6", property = "away_player_6"),
                     @Result(column = "away_player_7", property = "away_player_7"),
                     @Result(column = "away_player_8", property = "away_player_8"),
                     @Result(column = "away_player_9", property = "away_player_9"),
                     @Result(column = "away_player_10", property = "away_player_10"),
                     @Result(column = "away_player_11", property = "away_player_11"),
                     @Result(column = "away_player_X1", property = "away_player_X1"),
                     @Result(column = "away_player_X2", property = "away_player_X2"),
                     @Result(column = "away_player_X3", property = "away_player_X3"),
                     @Result(column = "away_player_X4", property = "away_player_X4"),
                     @Result(column = "away_player_X5", property = "away_player_X5"),
                     @Result(column = "away_player_X6", property = "away_player_X6"),
                     @Result(column = "away_player_X7", property = "away_player_X7"),
                     @Result(column = "away_player_X8", property = "away_player_X8"),
                     @Result(column = "away_player_X9", property = "away_player_X9"),
                     @Result(column = "away_player_X10", property = "away_player_X10"),
                     @Result(column = "away_player_X11", property = "away_player_X11"),
                     @Result(column = "away_player_Y1", property = "away_player_Y1"),
                     @Result(column = "away_player_Y2", property = "away_player_Y2"),
                     @Result(column = "away_player_Y3", property = "away_player_Y3"),
                     @Result(column = "away_player_Y4", property = "away_player_Y4"),
                     @Result(column = "away_player_Y5", property = "away_player_Y5"),
                     @Result(column = "away_player_Y6", property = "away_player_Y6"),
                     @Result(column = "away_player_Y7", property = "away_player_Y7"),
                     @Result(column = "away_player_Y8", property = "away_player_Y8"),
                     @Result(column = "away_player_Y9", property = "away_player_Y9"),
                     @Result(column = "away_player_Y10", property = "away_player_Y10"),
                     @Result(column = "away_player_Y11", property = "away_player_Y11")
             }
    )
    List<Match> findAll();
    
    @ResultMap("Match")
    @Select("SELECT * FROM Match"
            + " WHERE league_id=#{league} AND season=#{season} AND country_id=#{country} AND date=#{date};\n")
    Match selectOne(MatchPredictionRequest event);
    
    @Insert("UPDATE Match\n" +
            "SET score_team_home=#{home_team_goal}, score_team_away=#{away_team_goal}, goal=#{goal}, shoton=#{shoton}, shotoff=#{shotoff}, foulcommit=#{foulcommit}, card=#{card}, cross=#{cross}, corner=#{corner}, possession=#{possession}\n" +
            "WHERE league_id=#{league} AND season=#{season} AND country_id=#{countryId} AND date=#{date};\n")
    void updateMatchOdds(@Param("countryId") Integer countryId,
                         @Param("league") Integer league,
                         @Param("season") String season,
                         @Param("date") LocalDateTime date,
                         @Param("homeWin") Double homeWin,
                         @Param("awayWin") Double awayWin,
                         @Param("stalemate") Double stalemate
    );
}

	
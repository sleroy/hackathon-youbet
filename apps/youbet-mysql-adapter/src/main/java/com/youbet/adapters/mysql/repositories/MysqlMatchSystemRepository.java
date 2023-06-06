package com.youbet.adapters.mysql.repositories;

import com.youbet.adapters.mysql.mappers.*;
import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.externalprov.PlayerDetails;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;
import com.youbet.domain.requests.MatchReferences;
import com.youbet.ports.matchsystem.*;
import com.youbet.utils.YoubetException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * MySql + MyBatis implementation of the Match System repository.
 */
public class MysqlMatchSystemRepository implements MatchSystemRepository {
    private final SqlSessionFactory sqlSessionFactory;
    
    public MysqlMatchSystemRepository(SqlSessionFactory sqlSessionFactory) {
        
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
    @Override public Optional<Team> findTeam(String teamName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TeamMapper teamMapper = session.getMapper(TeamMapper.class);
            Team team = teamMapper.selectTeam(teamName);
            return Optional.ofNullable(team);
        }
    }
    
    @Override public Team createTeam(String teamName) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            TeamMapper teamMapper = session.getMapper(TeamMapper.class);
            UUID uuid = UUID.randomUUID();
            
            Team team = new Team();
            team.setName(teamName);
            team.setShortName(teamName);
            team.setTeam_api_id(uuid.toString());
            int rowCreated = teamMapper.saveTeam(team);
            
            return team;
        }
    }
    
    @Override public Optional<League> findLeague(String countryName, String leagueName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            LeagueMapper leagueMapper = session.getMapper(LeagueMapper.class);
            CountryMapper countryMapper = session.getMapper(CountryMapper.class);
            
            Country country = countryMapper.selectCountry(countryName);
            if (country == null) throw new YoubetException("Country " + countryName + " was not found");
            Integer countryId = country.getId();
            League league = leagueMapper.selectLeague(countryId, leagueName);
            return Optional.ofNullable(league);
        }
    }
    
    @Override public League createLeague(String countryName, String leagueName) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            LeagueMapper leagueMapper = session.getMapper(LeagueMapper.class);
            CountryMapper countryMapper = session.getMapper(CountryMapper.class);
            
            Country country = countryMapper.selectCountry(countryName);
            if (country == null) throw new YoubetException("Country " + countryName + " was not found");
            Integer countryId = country.getId();
            String leagueApi_id = UUID.randomUUID().toString();
            
            League league = new League();
            league.setName(leagueName);
            league.setCountry_id(countryId);
            league.setLeague_api_id(leagueApi_id);
            
            int row = leagueMapper.saveLeague(league);
            if (row == 0) throw new YoubetException("Could not create the league " + league);
            return league;
        }
    }
    
    @Override public Integer createOrUpdatePlayer(PlayerDetails playerDetails) {
        if (playerDetails == null || playerDetails.getPlayerName() == null) {
            return null;
        }
        
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            PlayerMapper playerMapper = session.getMapper(PlayerMapper.class);
            Player player = playerMapper.selectPlayer(playerDetails.getPlayerName());
            if (player != null) return player.getId();
            
            player = new Player();
            player.setBirthday(playerDetails.getBirthday());
            player.setPlayer_name(playerDetails.getPlayerName());
            player.setHeight(playerDetails.getHeight());
            player.setWeight(playerDetails.getWeight());
            player.setPlayer_api_id(UUID.randomUUID().toString());
            
            int row = playerMapper.savePlayer(player);
            if (row == 0) {
                player = playerMapper.selectPlayer(playerDetails.getPlayerName());
                if (player == null) throw new YoubetException("Could not create the player " + player);
            }
            return player != null ? player.getId() : null;
        }
    }
    
    @Override public void createMatch(MatchSystemMatchRegistrationRequest request) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchMapper matchmapper = session.getMapper(MatchMapper.class);
            MatchReferences references = request.getReferences();
            Match match = new Match();
            match.setCountry_id(references.getCountryId());
            match.setLeague_id(references.getLeague());
            match.setSeason(request.getSeason());
            match.setStage(request.getStage());
            match.setDate(request.getDate());
            match.setHome_team_api_id(references.getHomeTeam());
            match.setAway_team_api_id(references.getAwayTeam());
            match.setScore_team_away(0);
            match.setScore_team_home(0);
            match.setMatch_api_id(UUID.randomUUID().toString());
            
            
            match.setHome_player_X1(references.getHome_player_X1());
            match.setHome_player_X2(references.getHome_player_X2());
            match.setHome_player_X3(references.getHome_player_X3());
            match.setHome_player_X4(references.getHome_player_X4());
            match.setHome_player_X5(references.getHome_player_X5());
            match.setHome_player_X6(references.getHome_player_X6());
            match.setHome_player_X7(references.getHome_player_X7());
            match.setHome_player_X8(references.getHome_player_X8());
            match.setHome_player_X9(references.getHome_player_X9());
            match.setHome_player_X10(references.getHome_player_X10());
            match.setHome_player_X11(references.getHome_player_X11());
            
            match.setHome_player_Y1(references.getHome_player_Y1());
            match.setHome_player_Y2(references.getHome_player_Y2());
            match.setHome_player_Y3(references.getHome_player_Y3());
            match.setHome_player_Y4(references.getHome_player_Y4());
            match.setHome_player_Y5(references.getHome_player_Y5());
            match.setHome_player_Y6(references.getHome_player_Y6());
            match.setHome_player_Y7(references.getHome_player_Y7());
            match.setHome_player_Y8(references.getHome_player_Y8());
            match.setHome_player_Y9(references.getHome_player_Y9());
            match.setHome_player_Y10(references.getHome_player_Y10());
            match.setHome_player_Y11(references.getHome_player_Y11());
            
            match.setHome_player_1(references.getHome_player_1());
            match.setHome_player_2(references.getHome_player_2());
            match.setHome_player_3(references.getHome_player_3());
            match.setHome_player_4(references.getHome_player_4());
            match.setHome_player_5(references.getHome_player_5());
            match.setHome_player_6(references.getHome_player_6());
            match.setHome_player_7(references.getHome_player_7());
            match.setHome_player_8(references.getHome_player_8());
            match.setHome_player_9(references.getHome_player_9());
            match.setHome_player_10(references.getHome_player_10());
            match.setHome_player_11(references.getHome_player_11());
            
            
            match.setAway_player_X1(references.getAway_player_X1());
            match.setAway_player_X2(references.getAway_player_X2());
            match.setAway_player_X3(references.getAway_player_X3());
            match.setAway_player_X4(references.getAway_player_X4());
            match.setAway_player_X5(references.getAway_player_X5());
            match.setAway_player_X6(references.getAway_player_X6());
            match.setAway_player_X7(references.getAway_player_X7());
            match.setAway_player_X8(references.getAway_player_X8());
            match.setAway_player_X9(references.getAway_player_X9());
            match.setAway_player_X10(references.getAway_player_X10());
            match.setAway_player_X11(references.getAway_player_X11());
            
            match.setAway_player_Y1(references.getAway_player_Y1());
            match.setAway_player_Y2(references.getAway_player_Y2());
            match.setAway_player_Y3(references.getAway_player_Y3());
            match.setAway_player_Y4(references.getAway_player_Y4());
            match.setAway_player_Y5(references.getAway_player_Y5());
            match.setAway_player_Y6(references.getAway_player_Y6());
            match.setAway_player_Y7(references.getAway_player_Y7());
            match.setAway_player_Y8(references.getAway_player_Y8());
            match.setAway_player_Y9(references.getAway_player_Y9());
            match.setAway_player_Y10(references.getAway_player_Y10());
            match.setAway_player_Y11(references.getAway_player_Y11());
            
            match.setAway_player_1(references.getAway_player_1());
            match.setAway_player_2(references.getAway_player_2());
            match.setAway_player_3(references.getAway_player_3());
            match.setAway_player_4(references.getAway_player_4());
            match.setAway_player_5(references.getAway_player_5());
            match.setAway_player_6(references.getAway_player_6());
            match.setAway_player_7(references.getAway_player_7());
            match.setAway_player_8(references.getAway_player_8());
            match.setAway_player_9(references.getAway_player_9());
            match.setAway_player_10(references.getAway_player_10());
            match.setAway_player_11(references.getAway_player_11());
            
            matchmapper.createMatch(match);
        }
    }
    
    @Override public void updateMatch(MatchSystemMatchUpdateRequest request) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchMapper matchmapper = session.getMapper(MatchMapper.class);
            matchmapper.updateMatch(request.getReferences().getCountryId(),
                                    request.getReferences().getLeague(),
                                    request.getSeason(),
                                    request.getDate(),
                                    request.getHome_team_goal(),
                                    request.getAway_team_goal(),
                                    request.getGoal(),
                                    request.getShoton(),
                                    request.getShotoff(),
                                    request.getFoulcommit(),
                                    request.getCard(),
                                    request.getCross(),
                                    request.getCorner(),
                                    request.getPossession(),
                                    request.getState()
            );
        }
    }
    
    @Override public void updateMatchOdds(MatchPredictionOddsUpdatedEvent request) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchMapper matchmapper = session.getMapper(MatchMapper.class);
            matchmapper.updateMatchOdds(request.getCountry(),
                                        request.getLeague(),
                                        request.getSeason(),
                                        request.getDate(),
                                        request.getHomeWin(),
                                        request.getAwayWin(),
                                        request.getStalemate()
            );
        }
    }
}

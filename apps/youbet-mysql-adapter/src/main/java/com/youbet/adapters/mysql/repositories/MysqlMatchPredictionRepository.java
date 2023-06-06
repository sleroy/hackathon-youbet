package com.youbet.adapters.mysql.repositories;

import com.youbet.adapters.mysql.mappers.MatchRecordMapper;
import com.youbet.domain.requests.MatchPredictionRequest;
import com.youbet.domain.requests.MatchReferences;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;
import com.youbet.ports.matchprediction.MatchPredictionRepository;
import com.youbet.ports.matchsystem.MatchRecord;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * MySql + MyBatis implementation of the Match System repository.
 */
public class MysqlMatchPredictionRepository implements MatchPredictionRepository {
    private final SqlSessionFactory sqlSessionFactory;
    
    public MysqlMatchPredictionRepository(SqlSessionFactory sqlSessionFactory) {
        
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
    @Override public void createRecord(final @NotNull MatchSystemMatchRegistrationRequest request) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchRecordMapper matchRecordMapper = session.getMapper(MatchRecordMapper.class);
            MatchReferences references = request.getReferences();
            MatchRecord matchRecord = new MatchRecord();
            matchRecord.setCountry_id(references.getCountryId());
            matchRecord.setLeague_id(references.getLeague());
            matchRecord.setSeason(request.getSeason());
            matchRecord.setStage(request.getStage());
            matchRecord.setDate(request.getDate());
            matchRecord.setHome_team_api_id(references.getHomeTeam());
            matchRecord.setAway_team_api_id(references.getAwayTeam());
            matchRecord.setScore_team_away(0);
            matchRecord.setScore_team_home(0);
            
            matchRecord.setHome_player_X1(references.getHome_player_X1());
            matchRecord.setHome_player_X2(references.getHome_player_X2());
            matchRecord.setHome_player_X3(references.getHome_player_X3());
            matchRecord.setHome_player_X4(references.getHome_player_X4());
            matchRecord.setHome_player_X5(references.getHome_player_X5());
            matchRecord.setHome_player_X6(references.getHome_player_X6());
            matchRecord.setHome_player_X7(references.getHome_player_X7());
            matchRecord.setHome_player_X8(references.getHome_player_X8());
            matchRecord.setHome_player_X9(references.getHome_player_X9());
            matchRecord.setHome_player_X10(references.getHome_player_X10());
            matchRecord.setHome_player_X11(references.getHome_player_X11());
            
            matchRecord.setHome_player_Y1(references.getHome_player_Y1());
            matchRecord.setHome_player_Y2(references.getHome_player_Y2());
            matchRecord.setHome_player_Y3(references.getHome_player_Y3());
            matchRecord.setHome_player_Y4(references.getHome_player_Y4());
            matchRecord.setHome_player_Y5(references.getHome_player_Y5());
            matchRecord.setHome_player_Y6(references.getHome_player_Y6());
            matchRecord.setHome_player_Y7(references.getHome_player_Y7());
            matchRecord.setHome_player_Y8(references.getHome_player_Y8());
            matchRecord.setHome_player_Y9(references.getHome_player_Y9());
            matchRecord.setHome_player_Y10(references.getHome_player_Y10());
            matchRecord.setHome_player_Y11(references.getHome_player_Y11());
            
            matchRecord.setHome_player_1(references.getHome_player_1());
            matchRecord.setHome_player_2(references.getHome_player_2());
            matchRecord.setHome_player_3(references.getHome_player_3());
            matchRecord.setHome_player_4(references.getHome_player_4());
            matchRecord.setHome_player_5(references.getHome_player_5());
            matchRecord.setHome_player_6(references.getHome_player_6());
            matchRecord.setHome_player_7(references.getHome_player_7());
            matchRecord.setHome_player_8(references.getHome_player_8());
            matchRecord.setHome_player_9(references.getHome_player_9());
            matchRecord.setHome_player_10(references.getHome_player_10());
            matchRecord.setHome_player_11(references.getHome_player_11());
            
            
            matchRecord.setAway_player_X1(references.getAway_player_X1());
            matchRecord.setAway_player_X2(references.getAway_player_X2());
            matchRecord.setAway_player_X3(references.getAway_player_X3());
            matchRecord.setAway_player_X4(references.getAway_player_X4());
            matchRecord.setAway_player_X5(references.getAway_player_X5());
            matchRecord.setAway_player_X6(references.getAway_player_X6());
            matchRecord.setAway_player_X7(references.getAway_player_X7());
            matchRecord.setAway_player_X8(references.getAway_player_X8());
            matchRecord.setAway_player_X9(references.getAway_player_X9());
            matchRecord.setAway_player_X10(references.getAway_player_X10());
            matchRecord.setAway_player_X11(references.getAway_player_X11());
            
            matchRecord.setAway_player_Y1(references.getAway_player_Y1());
            matchRecord.setAway_player_Y2(references.getAway_player_Y2());
            matchRecord.setAway_player_Y3(references.getAway_player_Y3());
            matchRecord.setAway_player_Y4(references.getAway_player_Y4());
            matchRecord.setAway_player_Y5(references.getAway_player_Y5());
            matchRecord.setAway_player_Y6(references.getAway_player_Y6());
            matchRecord.setAway_player_Y7(references.getAway_player_Y7());
            matchRecord.setAway_player_Y8(references.getAway_player_Y8());
            matchRecord.setAway_player_Y9(references.getAway_player_Y9());
            matchRecord.setAway_player_Y10(references.getAway_player_Y10());
            matchRecord.setAway_player_Y11(references.getAway_player_Y11());
            
            matchRecord.setAway_player_1(references.getAway_player_1());
            matchRecord.setAway_player_2(references.getAway_player_2());
            matchRecord.setAway_player_3(references.getAway_player_3());
            matchRecord.setAway_player_4(references.getAway_player_4());
            matchRecord.setAway_player_5(references.getAway_player_5());
            matchRecord.setAway_player_6(references.getAway_player_6());
            matchRecord.setAway_player_7(references.getAway_player_7());
            matchRecord.setAway_player_8(references.getAway_player_8());
            matchRecord.setAway_player_9(references.getAway_player_9());
            matchRecord.setAway_player_10(references.getAway_player_10());
            matchRecord.setAway_player_11(references.getAway_player_11());
            
            matchRecordMapper.createMatchRecord(matchRecord);
        }
    }
    
    @Override public void updateRecord(MatchSystemMatchUpdateRequest matchUpdateRequest) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchRecordMapper matchRecordMapper = session.getMapper(MatchRecordMapper.class);
            matchRecordMapper.updateMatchRecord(matchUpdateRequest.getReferences().getCountryId(),
                                                matchUpdateRequest.getReferences().getLeague(),
                                                matchUpdateRequest.getSeason(), matchUpdateRequest.getDate(),
                                                matchUpdateRequest.getHome_team_goal(),
                                                matchUpdateRequest.getAway_team_goal());
        }
    }
    
    @Override public List<MatchRecord> selectAll() {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchRecordMapper matchRecordMapper = session.getMapper(MatchRecordMapper.class);
            return matchRecordMapper.findAll();
        }
    }
    
    @Override public MatchRecord selectRecord(MatchPredictionRequest event) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, true)) {
            MatchRecordMapper matchRecordMapper = session.getMapper(MatchRecordMapper.class);
            return matchRecordMapper.selectOne(event);
        }
    }
}

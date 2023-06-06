package com.example.youbet;

import com.youbet.adapters.envvar.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.domain.MatchState;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchUpdatedEvent;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.utils.JsonUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        File databaseFile = new File(args[0]);
        if(!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException exception) {
                LOGGER.error( "Failed to created SQLite database.  Error: "
                        + exception.getMessage());
            }
        }
        
        /* Initializes RabbitMQ Adapter. */
        AppConfigurationPort appConfigurationPort = new AppEnvironmentVariableConfigurationAdapter();
        RabbitMQMessageBrokerAdapter messageBroker = new RabbitMQMessageBrokerAdapter(appConfigurationPort);
        messageBroker.start();
        MessageBrokerPort messageBrokerPort = messageBroker;
        
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("SQLiteConnectionPool");
        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setJdbcUrl("jdbc:sqlite:" + databaseFile);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(SqliteMatchMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    
        JsonUtils.initializeWithDefault();
        try(SqlSession session = sqlSessionFactory.openSession();) {
            SqliteMatchMapper matchMapper = session.getMapper(SqliteMatchMapper.class);
/**
            matchMapper.selectAll().forEach(row -> {
                System.out.println(row);
            });
 **/
    
            List<SqliteMatch> sqliteMatches = matchMapper.fetchAll();
            sqliteMatches.forEach(
                    row -> {
                        ExternalProviderMatchRegisteredEvent event = new ExternalProviderMatchRegisteredEvent();
                        event.setAwayTeam(row.getAwayTeam());
                        event.setCountry(row.getCountry());
                        event.setDate(row.getDate());
                        event.setHomeTeam(row.getHomeTeam());
                        event.setLeague(row.getLeague());
                        event.setSeason(row.getSeason());
                        event.setStage(row.getStage());
                        
                        event.setAway_player_1(matchMapper.getPlayer(row.getAway_player_1()));
                        event.setAway_player_2(matchMapper.getPlayer(row.getAway_player_2()));
                        event.setAway_player_3(matchMapper.getPlayer(row.getAway_player_3()));
                        event.setAway_player_4(matchMapper.getPlayer(row.getAway_player_4()));
                        event.setAway_player_5(matchMapper.getPlayer(row.getAway_player_5()));
                        event.setAway_player_6(matchMapper.getPlayer(row.getAway_player_6()));
                        event.setAway_player_7(matchMapper.getPlayer(row.getAway_player_7()));
                        event.setAway_player_8(matchMapper.getPlayer(row.getAway_player_8()));
                        event.setAway_player_9(matchMapper.getPlayer(row.getAway_player_9()));
                        event.setAway_player_10(matchMapper.getPlayer(row.getAway_player_10()));
                        event.setAway_player_11(matchMapper.getPlayer(row.getAway_player_11()));
    
                        event.setHome_player_1(matchMapper.getPlayer(row.getHome_player_1()));
                        event.setHome_player_2(matchMapper.getPlayer(row.getHome_player_2()));
                        event.setHome_player_3(matchMapper.getPlayer(row.getHome_player_3()));
                        event.setHome_player_4(matchMapper.getPlayer(row.getHome_player_4()));
                        event.setHome_player_5(matchMapper.getPlayer(row.getHome_player_5()));
                        event.setHome_player_6(matchMapper.getPlayer(row.getHome_player_6()));
                        event.setHome_player_7(matchMapper.getPlayer(row.getHome_player_7()));
                        event.setHome_player_8(matchMapper.getPlayer(row.getHome_player_8()));
                        event.setHome_player_9(matchMapper.getPlayer(row.getHome_player_9()));
                        event.setHome_player_10(matchMapper.getPlayer(row.getHome_player_10()));
                        event.setHome_player_11(matchMapper.getPlayer(row.getHome_player_11()));
    
                        event.setHome_player_X1(matchMapper.getPlayer(row.getHome_player_X1()));
                        event.setHome_player_X2(matchMapper.getPlayer(row.getHome_player_X2()));
                        event.setHome_player_X3(matchMapper.getPlayer(row.getHome_player_X3()));
                        event.setHome_player_X4(matchMapper.getPlayer(row.getHome_player_X4()));
                        event.setHome_player_X5(matchMapper.getPlayer(row.getHome_player_X5()));
                        event.setHome_player_X6(matchMapper.getPlayer(row.getHome_player_X6()));
                        event.setHome_player_X7(matchMapper.getPlayer(row.getHome_player_X7()));
                        event.setHome_player_X8(matchMapper.getPlayer(row.getHome_player_X8()));
                        event.setHome_player_X9(matchMapper.getPlayer(row.getHome_player_X9()));
                        event.setHome_player_X10(matchMapper.getPlayer(row.getHome_player_X10()));
                        event.setHome_player_X11(matchMapper.getPlayer(row.getHome_player_X11()));
    
                        event.setHome_player_Y1(matchMapper.getPlayer(row.getHome_player_Y1()));
                        event.setHome_player_Y2(matchMapper.getPlayer(row.getHome_player_Y2()));
                        event.setHome_player_Y3(matchMapper.getPlayer(row.getHome_player_Y3()));
                        event.setHome_player_Y4(matchMapper.getPlayer(row.getHome_player_Y4()));
                        event.setHome_player_Y5(matchMapper.getPlayer(row.getHome_player_Y5()));
                        event.setHome_player_Y6(matchMapper.getPlayer(row.getHome_player_Y6()));
                        event.setHome_player_Y7(matchMapper.getPlayer(row.getHome_player_Y7()));
                        event.setHome_player_Y8(matchMapper.getPlayer(row.getHome_player_Y8()));
                        event.setHome_player_Y9(matchMapper.getPlayer(row.getHome_player_Y9()));
                        event.setHome_player_Y10(matchMapper.getPlayer(row.getHome_player_Y10()));
                        event.setHome_player_Y11(matchMapper.getPlayer(row.getHome_player_Y11()));
    
    
                        event.setAway_player_X1(matchMapper.getPlayer(row.getAway_player_X1()));
                        event.setAway_player_X2(matchMapper.getPlayer(row.getAway_player_X2()));
                        event.setAway_player_X3(matchMapper.getPlayer(row.getAway_player_X3()));
                        event.setAway_player_X4(matchMapper.getPlayer(row.getAway_player_X4()));
                        event.setAway_player_X5(matchMapper.getPlayer(row.getAway_player_X5()));
                        event.setAway_player_X6(matchMapper.getPlayer(row.getAway_player_X6()));
                        event.setAway_player_X7(matchMapper.getPlayer(row.getAway_player_X7()));
                        event.setAway_player_X8(matchMapper.getPlayer(row.getAway_player_X8()));
                        event.setAway_player_X9(matchMapper.getPlayer(row.getAway_player_X9()));
                        event.setAway_player_X10(matchMapper.getPlayer(row.getAway_player_X10()));
                        event.setAway_player_X11(matchMapper.getPlayer(row.getAway_player_X11()));
    
                        event.setAway_player_Y1(matchMapper.getPlayer(row.getAway_player_Y1()));
                        event.setAway_player_Y2(matchMapper.getPlayer(row.getAway_player_Y2()));
                        event.setAway_player_Y3(matchMapper.getPlayer(row.getAway_player_Y3()));
                        event.setAway_player_Y4(matchMapper.getPlayer(row.getAway_player_Y4()));
                        event.setAway_player_Y5(matchMapper.getPlayer(row.getAway_player_Y5()));
                        event.setAway_player_Y6(matchMapper.getPlayer(row.getAway_player_Y6()));
                        event.setAway_player_Y7(matchMapper.getPlayer(row.getAway_player_Y7()));
                        event.setAway_player_Y8(matchMapper.getPlayer(row.getAway_player_Y8()));
                        event.setAway_player_Y9(matchMapper.getPlayer(row.getAway_player_Y9()));
                        event.setAway_player_Y10(matchMapper.getPlayer(row.getAway_player_Y10()));
                        event.setAway_player_Y11(matchMapper.getPlayer(row.getAway_player_Y11()));
                        
    
                        //String str = JsonUtils.toJsonString(event);
                        //System.out.println(str);
                        
                        messageBrokerPort.dispatchToExternalProviderMatchRegistrationQueue(event);
                        
                        // Update score
                        ExternalProviderMatchUpdatedEvent updateEvent = new ExternalProviderMatchUpdatedEvent();
                        updateEvent.setAwayTeam(row.getAwayTeam());
                        updateEvent.setCountry(row.getCountry());
                        updateEvent.setDate(row.getDate());
                        updateEvent.setHomeTeam(row.getHomeTeam());
                        updateEvent.setLeague(row.getLeague());
                        updateEvent.setSeason(row.getSeason());
                        updateEvent.setStage(row.getStage());
                        updateEvent.setHome_team_goal(row.getHome_team_goal());
                        updateEvent.setAway_team_goal(row.getAway_team_goal());
                        updateEvent.setCard(row.getCard());
                        updateEvent.setCorner(row.getCorner());
                        updateEvent.setCross(row.getCross());
                        updateEvent.setGoal(row.getGoal());
                        updateEvent.setFoulcommit(row.getFoulcommit());
                        updateEvent.setPossession(row.getPossession());
                        updateEvent.setShotoff(row.getShotoff());
                        updateEvent.setShoton(row.getShoton());
                        updateEvent.setState(MatchState.ended);
                        
                        messageBrokerPort.dispatchToExternalProviderMatchUpdateQueue(updateEvent);
    
                    }
            );
            
            
    
            
        }
        
    }
}
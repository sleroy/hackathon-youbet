package com.youbet.adapters.mysql;

import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.matchsystem.Country;
import com.youbet.ports.matchsystem.League;
import com.youbet.ports.matchsystem.MatchSystemRepositoryPort;
import com.youbet.ports.matchsystem.Team;
import com.youbet.utils.YoubetException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the repository to works with Aurora.
 */
public class MySqlDatabaseAdapter implements MatchSystemRepositoryPort {
    public static final String ENV_DEVELOPMENT = "development";
    private final SqlSessionFactory sqlSessionFactory;
    private final AppConfigurationPort appConfigurationPort;
    
    public MySqlDatabaseAdapter(AppConfigurationPort appConfigurationPort) {
        
        this.appConfigurationPort = appConfigurationPort;
        DataSource dataSource = createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(ENV_DEVELOPMENT, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TeamMapper.class);
        configuration.addMapper(LeagueMapper.class);
        configuration.addMapper(CountryMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(configuration);
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
    
    public DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(appConfigurationPort.getProperty("aurora.jdbcUrl"));
        config.setUsername(appConfigurationPort.getProperty("aurora.username"));
        config.setPassword(appConfigurationPort.getPassword("aurora.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}

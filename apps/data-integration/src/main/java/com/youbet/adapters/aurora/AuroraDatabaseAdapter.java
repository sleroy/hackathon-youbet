package com.youbet.adapters.aurora;

import com.youbet.agents.domain.Country;
import com.youbet.agents.domain.League;
import com.youbet.agents.domain.Team;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.MatchSystemRepositoryPort;
import com.youbet.utils.YoubetException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the repository to works with Aurora.
 */
public class AuroraDatabaseAdapter implements MatchSystemRepositoryPort {
    public static final String ENV_DEVELOPMENT = "development";
    private final SqlSessionFactory sqlSessionFactory;
    private final AppConfigurationPort appConfigurationPort;
    
    public AuroraDatabaseAdapter(AppConfigurationPort appConfigurationPort) {
        
        this.appConfigurationPort = appConfigurationPort;
        DataSource dataSource = createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(ENV_DEVELOPMENT, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TeamMapper.class);
        configuration.addMapper(LeagueMapper.class);
        configuration.addMapper(CountryMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }
    
    
    @Override public Optional<Team> findTeam(String teamName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TeamMapper teamMapper = session.getMapper(TeamMapper.class);
            Team team = teamMapper.selectTeam(teamName);
            return Optional.ofNullable(team);
        }
    }
    
    @Override public Team createTeam(String teamName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TeamMapper teamMapper = session.getMapper(TeamMapper.class);
            UUID uuid = UUID.randomUUID();
            return teamMapper.insertTeam(teamName, uuid.toString(), teamName);
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
        try (SqlSession session = sqlSessionFactory.openSession()) {
            LeagueMapper leagueMapper = session.getMapper(LeagueMapper.class);
            CountryMapper countryMapper = session.getMapper(CountryMapper.class);
            
            Country country = countryMapper.selectCountry(countryName);
            if (country == null) throw new YoubetException("Country " + countryName + " was not found");
            Integer countryId = country.getId();
            String leagueApi_id = UUID.randomUUID().toString();
            League league = leagueMapper.createLeague(countryId, leagueApi_id, leagueName);
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

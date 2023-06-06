package com.youbet.adapters.mysql;

import com.youbet.adapters.mysql.mappers.*;
import com.youbet.adapters.mysql.repositories.MysqlMatchPredictionRepository;
import com.youbet.adapters.mysql.repositories.MysqlMatchSystemRepository;
import com.youbet.ports.AppConfigurationPort;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * Implementation of the repository to works with Aurora.
 */
public class MySqlDatabaseAdapter {
    public static final String ENV_DEVELOPMENT = "development";
    private final SqlSessionFactory sqlSessionFactory;
    private final AppConfigurationPort appConfigurationPort;
    private final MysqlMatchSystemRepository matchSystemRepository;
    private final MysqlMatchPredictionRepository matchPredictionRepository;
    
    public MySqlDatabaseAdapter(AppConfigurationPort appConfigurationPort) {
        
        this.appConfigurationPort = appConfigurationPort;
        DataSource dataSource = createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(ENV_DEVELOPMENT, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TeamMapper.class);
        configuration.addMapper(LeagueMapper.class);
        configuration.addMapper(CountryMapper.class);
        configuration.addMapper(PlayerMapper.class);
        configuration.addMapper(MatchRecordMapper.class);
        configuration.addMapper(MatchMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        
        this.matchSystemRepository = new MysqlMatchSystemRepository(sqlSessionFactory);
        this.matchPredictionRepository = new MysqlMatchPredictionRepository(sqlSessionFactory);
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
    
    public MysqlMatchSystemRepository getMatchSystemRepository() {
        return matchSystemRepository;
    }
    
    public MysqlMatchPredictionRepository getMatchPredictionRepository() {
        return matchPredictionRepository;
    }
    
}

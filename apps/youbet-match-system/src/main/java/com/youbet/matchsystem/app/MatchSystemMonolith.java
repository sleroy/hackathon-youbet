package com.youbet.matchsystem.app;

import com.youbet.adapters.envvar.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.mysql.MySqlDatabaseAdapter;
import com.youbet.adapters.rabbitmq.Queues;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.matchsystem.agents.matchregistration.MatchRegistrationAgent;
import com.youbet.matchsystem.agents.matchupdate.MatchUpdateAgent;
import com.youbet.matchsystem.agents.matchupdateodds.MatchUpdateOddsAgent;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the application
 */
public class MatchSystemMonolith {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchSystemMonolith.class);
    
    public static void main(String[] args) {
        try {
            initApp();
        } catch(Exception e) {
            LOGGER.error("Cannot initialize the app", e);
            System.exit(2);
        }
    
    
    }
    
    private static void initApp() {
        /* Initialize and bind ports to adapters */
        JsonUtils.initializeWithDefault();
        AppConfigurationPort appConfigurationPort = new AppEnvironmentVariableConfigurationAdapter();
        
        /* Initializes RabbitMQ Adapter. */
        RabbitMQMessageBrokerAdapter messageBroker = new RabbitMQMessageBrokerAdapter(appConfigurationPort);
        messageBroker.start();
        MessageBrokerPort messageBrokerPort = messageBroker;
        
        /* Initializes Aurora Adapter */
        MatchSystemRepository matchSystemPort = new MySqlDatabaseAdapter(appConfigurationPort).getMatchSystemRepository();
        
        /**
         * Initialize missing queues
         */
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_REGISTRATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_UPDATE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_UPDATE_ODDS);
        
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_SYSTEM_REGISTRATION, new MatchRegistrationAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_SYSTEM_UPDATE, new MatchUpdateAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_SYSTEM_UPDATE_ODDS, new MatchUpdateOddsAgent(messageBrokerPort, matchSystemPort));
        
        LOGGER.info("Match system initialized");
    
    
    }
}

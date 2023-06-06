package com.youbet.dataintegration.app;

import com.youbet.adapters.envvar.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.mysql.MySqlDatabaseAdapter;
import com.youbet.adapters.rabbitmq.Queues;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.dataintegration.agents.*;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the application
 */
public class DataIntegrationMonolith {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DataIntegrationMonolith.class);
    
    public static void main(String[] args) {
        try {
            initApp();
        } catch (Exception e) {
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
        messageBroker.declareRequiredQueue(Queues.QUEUE_SANITIZATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_TEAM);
        messageBroker.declareRequiredQueue(Queues.QUEUE_LEAGUE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_PLAYER);
        messageBroker.declareRequiredQueue(Queues.QUEUE_EXT_MATCH_REGISTRATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_EXT_MATCH_UPDATE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_EVENT_NOTIFIER);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_PREDICTION_STORAGE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_REGISTRATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_UPDATE);
        
        
        /**
         * Initialize data ingestion
         */
        // ExternalProviderMatchRegistrationQueue -down-> DataPipelineExternalProviderMatchRegisteredAgent
        // ExternalProviderMatchUpdateQueue -down-> DataPipelineExternalProviderMatchUpdatedAgent
        messageBroker.declareConsumer(Queues.QUEUE_EXT_MATCH_REGISTRATION, new ExternalProviderMatchRegistrationAgent(messageBrokerPort));
        messageBroker.declareConsumer(Queues.QUEUE_EXT_MATCH_UPDATE, new ExternalProviderMatchUpdateAgent(messageBrokerPort));
        
        /**
         * Initialize data pipeline
         */
        messageBroker.declareConsumer(Queues.QUEUE_SANITIZATION, new DataPipelineSanitizationAgent(messageBrokerPort));
        messageBroker.declareConsumer(Queues.QUEUE_LEAGUE, new LeagueAggregationAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_TEAM, new TeamAggregationAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_PLAYER, new PlayerAggregationAgent(messageBrokerPort, matchSystemPort));
        
        /**
         * Agent to route the events
         */
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_EVENT_NOTIFIER, new MatchEventDispatcherAgent(messageBrokerPort, matchSystemPort));
        
        LOGGER.info("Data Integration system initialized");
    }
}

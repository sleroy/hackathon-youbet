package com.youbet.dataintegration.app;

import com.youbet.adapters.env.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.mysql.MySqlDatabaseAdapter;
import com.youbet.adapters.rabbitmq.Queues;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.dataintegration.agents.dataingestion.ExternalProviderMatchRegistrationAgent;
import com.youbet.dataintegration.agents.dataingestion.ExternalProviderMatchUpdateAgent;
import com.youbet.dataintegration.agents.leagueaggregation.LeagueAggregationAgent;
import com.youbet.dataintegration.agents.matcheventdispatcher.MatchEventDispatcherAgent;
import com.youbet.dataintegration.agents.sanitization.DataPipelineSanitizationAgent;
import com.youbet.dataintegration.agents.teamaggregation.TeamAggregationAgent;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.matchsystem.MatchSystemRepositoryPort;
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
        MatchSystemRepositoryPort matchSystemPort = new MySqlDatabaseAdapter(appConfigurationPort);
        
        /**
         * Initialize missing queues
         */
        messageBroker.declareRequiredQueue(Queues.QUEUE_SANITIZATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_TEAM);
        messageBroker.declareRequiredQueue(Queues.QUEUE_LEAGUE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_EXT_MATCH_REGISTRATION);
        messageBroker.declareRequiredQueue(Queues.QUEUE_EXT_MATCH_UPDATE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_DISPATCH);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_PREDICTION_STORAGE_QUEUE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_REGISTRATION_QUEUE);
        messageBroker.declareRequiredQueue(Queues.QUEUE_MATCH_SYSTEM_UPDATE_QUEUE);
    
    
    
    
    
        /**
         * Initialize data ingestion
         */
        // ExternalProviderMatchRegistrationQueue -down-> DataPipelineExternalProviderMatchRegisteredAgent
        // ExternalProviderMatchUpdateQueue -down-> DataPipelineExternalProviderMatchUpdatedAgent
        messageBroker.declareConsumer(Queues.QUEUE_EXT_MATCH_REGISTRATION,
                                      new ExternalProviderMatchRegistrationAgent(messageBrokerPort));
        messageBroker.declareConsumer(Queues.QUEUE_EXT_MATCH_UPDATE,
                                      new ExternalProviderMatchUpdateAgent(messageBrokerPort));
        
        /**
         * Initialize data pipeline
         */
        messageBroker.declareConsumer(Queues.QUEUE_SANITIZATION, new DataPipelineSanitizationAgent(messageBrokerPort));
        messageBroker.declareConsumer(Queues.QUEUE_LEAGUE,
                                      new LeagueAggregationAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_TEAM, new TeamAggregationAgent(messageBrokerPort, matchSystemPort));
    
        /**
         * Agent to route the events
         */
        messageBroker.declareConsumer(Queues.QUEUE_MATCH_DISPATCH, new MatchEventDispatcherAgent(messageBrokerPort, matchSystemPort));
    }
}

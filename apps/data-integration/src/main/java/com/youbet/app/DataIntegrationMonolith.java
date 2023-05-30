package com.youbet.app;

import com.youbet.adapters.aurora.AuroraDatabaseAdapter;
import com.youbet.adapters.env.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.agents.leagueaggregation.LeagueAggregationAgent;
import com.youbet.agents.sanitization.DataPipelineSanitizationAgent;
import com.youbet.agents.teamaggregation.TeamAggregationAgent;
import com.youbet.ports.AppConfigurationPort;

import com.youbet.ports.MatchSystemRepositoryPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.utils.JsonUtils;

/**
 * Entry point of the application
 */
public class DataIntegrationMonolith {
    public static void main(String[] args) {
        /* Initialize and bind ports to adapters */
        JsonUtils.initializeWithDefault();
        AppConfigurationPort appConfigurationPort = new AppEnvironmentVariableConfigurationAdapter();
        
        /* Initializes RabbitMQ Adapter. */
        RabbitMQMessageBrokerAdapter messageBroker = new RabbitMQMessageBrokerAdapter(appConfigurationPort);
        messageBroker.start();
        MessageBrokerPort messageBrokerPort = messageBroker;
        
        
        /* Initializes Aurora Adapter */
        MatchSystemRepositoryPort matchSystemPort = new AuroraDatabaseAdapter(appConfigurationPort);
        
        /**
         * Initialize consumers
         */
        messageBroker.declareConsumer(Queues.QUEUE_SANITIZATION, new DataPipelineSanitizationAgent(messageBrokerPort));
        messageBroker.declareConsumer(Queues.QUEUE_LEAGUE,
                                      new LeagueAggregationAgent(messageBrokerPort, matchSystemPort));
        messageBroker.declareConsumer(Queues.QUEUE_TEAM, new TeamAggregationAgent(messageBrokerPort, matchSystemPort));
        
    }
}

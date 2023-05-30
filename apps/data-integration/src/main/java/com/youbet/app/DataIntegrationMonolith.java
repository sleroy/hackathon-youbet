package com.youbet.app;

import com.youbet.adapters.env.AppEnvironmentVariableConfigurationAdapter;
import com.youbet.adapters.rabbitmq.RabbitMQMessageBrokerAdapter;
import com.youbet.agents.sanitization.DataPipelineSanitizationAgent;
import com.youbet.ports.AppConfigurationPort;

import com.youbet.ports.messagebroker.MessageBrokerPort;

/**
 * Entry point of the application
 */
public class DataIntegrationMonolith {
    public static void main(String[] args) {
        /* Initialize and bind ports to adapters */
        AppConfigurationPort appConfigurationPort = new AppEnvironmentVariableConfigurationAdapter();

        /* Initializes RabbitMQ Adapter. */
        RabbitMQMessageBrokerAdapter messageBroker = new RabbitMQMessageBrokerAdapter(appConfigurationPort);
        messageBroker.start();
        MessageBrokerPort messageBrokerPort = messageBroker;


        /* Initializes Aurora Adapter */

        /**
         * Get parameters
         */
        String QUEUE_SANITIZATION = "queue";

        /**
         * Initialize consumers
         */
        DataPipelineSanitizationAgent agent1 = new DataPipelineSanitizationAgent(messageBrokerPort);

        messageBroker.declareConsumer(QUEUE_SANITIZATION, agent1);

    }
}

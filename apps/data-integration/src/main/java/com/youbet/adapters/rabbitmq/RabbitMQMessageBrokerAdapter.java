package com.youbet.adapters.rabbitmq;

import com.rabbitmq.client.*;
import com.youbet.app.Queues;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.Consumer;
import com.youbet.utils.JsonConsumer;
import com.youbet.utils.YoubetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQMessageBrokerAdapter extends AbstractRabbitMQMessageBrokerAdapter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQMessageBrokerAdapter.class.getClass());
    
    public RabbitMQMessageBrokerAdapter(AppConfigurationPort appConfigurationPort) {
        super(appConfigurationPort);
    }
    
    @Override
    public void start() {
        LOGGER.info("Initialization RabbitMQ connection factory");
        factory = new ConnectionFactory();
        factory.setUsername(appConfigurationPort.getPropertyOrFail(PROP_RABBIT_USERNAME));
        factory.setPassword(appConfigurationPort.getPassword(PROP_RABBIT_PASSWORD));
        factory.setHost(appConfigurationPort.getPropertyOrFail(PROP_RABBIT_HOST));
        factory.setPort(appConfigurationPort.getPropertyAsInt(PROP_RABBIT_PORT, DEFAULT_PORT));
    }
    
    @Override public void dispatchMessageAggregateLeagueQueue(YoubetMessage message) {
        sendMessage(Queues.QUEUE_LEAGUE, message);
    }
    
    @Override public void dispatchMessageMatchSystemMatchRegistrationQueue(YoubetMessage message) {
        sendMessage(Queues.QUEUE_MATCH_DISPATCH, message);
    }
    
    @Override public void dispatchMessageAggregateTeamQueue(YoubetMessage youbetMessage) {
        sendMessage(Queues.QUEUE_TEAM, youbetMessage);
        
    }
    
    
}

package com.youbet.adapters.rabbitmq;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.*;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    @Override public void dispatchToAggregateLeagueQueue(YoubetMessage message) {
        sendMessage(Queues.QUEUE_LEAGUE, message);
    }
    
    @Override public void dispatchToMatchSystemMatchRegistrationQueue(ObjectNode jsonPayload) {
        sendMessage(Queues.QUEUE_MATCH_DISPATCH, YoubetMessage.fromJson(jsonPayload));
    }
    
    @Override public void dispatchToAggregateTeamQueue(ObjectNode youbetMessage) {
        sendMessage(Queues.QUEUE_TEAM, YoubetMessage.fromJson(youbetMessage));
    }
    
    @Override public void dispatchToSanizationQueue(YoubetMessage youbetMessage) {
        sendMessage(Queues.QUEUE_SANITIZATION, youbetMessage);
    }
    
    @Override public void dispatchToMatchSystemRegistrationQueue(YoubetMessage event) {
        sendMessage(Queues.QUEUE_MATCH_SYSTEM_REGISTRATION, event);
    }
    
    @Override public void dispatchToMatchSystemUpdateQueue(YoubetMessage event) {
        sendMessage(Queues.QUEUE_MATCH_SYSTEM_UPDATE, event);
    }
    
    @Override public void dispatchToMatchPredictionDataStorageQueue(YoubetMessage event) {
        sendMessage(Queues.QUEUE_MATCH_PREDICTION_STORAGE, event);
    }
    
    
}

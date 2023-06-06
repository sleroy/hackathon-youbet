package com.youbet.adapters.rabbitmq;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.ConnectionFactory;
import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.domain.externalprov.ExternalProviderMatchUpdatedEvent;
import com.youbet.domain.requests.MatchBaseInfoRequest;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
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
    
    @Override public void dispatchToMatchEventNotifier(MatchSystemMatchRegistrationRequest jsonPayload) {
        sendMessage(Queues.QUEUE_MATCH_EVENT_NOTIFIER, YoubetMessage.fromEvent(jsonPayload));
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
    
    @Override public void dispatchToMatchEventNotifier(YoubetMessage youbetMessage) {
        sendMessage(Queues.QUEUE_MATCH_EVENT_NOTIFIER, youbetMessage);
        
    }
    
    @Override public void dispatchToPlayerAggregation(ObjectNode jsonNode) {
        sendMessage(Queues.QUEUE_PLAYER, YoubetMessage.fromJson(jsonNode));
    }
    
    @Override public void dispatchToMatchSystemUpdateScore(MatchPredictionOddsUpdatedEvent updatedEvent) {
        sendMessage(Queues.QUEUE_MATCH_SYSTEM_UPDATE_ODDS, YoubetMessage.fromEvent(updatedEvent));
    }
    
    @Override public void dispatchToMatchPredictionUpdateScore(MatchBaseInfoRequest event) {
        sendMessage(Queues.QUEUE_MATCH_PREDICTION_SCORE, YoubetMessage.fromEvent(event));
    }
    
    @Override public void dispatchToExternalProviderMatchRegistrationQueue(ExternalProviderMatchRegisteredEvent event) {
        sendMessage(Queues.QUEUE_EXT_MATCH_REGISTRATION, YoubetMessage.fromEvent(event));
    }
    
    @Override public void dispatchToExternalProviderMatchUpdateQueue(ExternalProviderMatchUpdatedEvent event) {
        sendMessage(Queues.QUEUE_EXT_MATCH_UPDATE, YoubetMessage.fromEvent(event));
    }
    
    
}

package com.youbet.adapters.rabbitmq;

import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.rabbitmq.client.*;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.Consumer;
import com.youbet.utils.JsonConsumer;
import com.youbet.utils.YoubetException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQMessageBrokerAdapter implements MessageBrokerPort {
    public static final int DEFAULT_PORT = 1562;
    private final AppConfigurationPort appConfigurationPort;
    private ConnectionFactory factory;

    public RabbitMQMessageBrokerAdapter(AppConfigurationPort appConfigurationPort) {

        this.appConfigurationPort = appConfigurationPort;
    }

    @Override
    public void start() {
        factory = new ConnectionFactory();
        factory.setHost(appConfigurationPort.getProperty("rabbit.host"));
        factory.setPort(appConfigurationPort.getPropertyAsInt("rabbit.port", DEFAULT_PORT));
    }

    @Override
    public void dispatchMessageAggregateTeamQueue(YoubetMessage message) {

    }

    public <T> void declareConsumer(String queueName, Consumer<T> agent1) {
        Connection connection = null;
        try {
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            final Consumer<YoubetMessage> adapt = adapt(agent1);
            new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    YoubetMessage youbetMessage = new YoubetMessage(body);
                    adapt.handleRequest(youbetMessage);
                    //
                }
            };
        } catch (IOException e) {
            throw new YoubetException(e);
        } catch (TimeoutException e) {
            throw new YoubetException(e);
        }
    }

    private <T> Consumer<YoubetMessage> adapt(Consumer<T> agent1) {
        if (agent1 instanceof JsonConsumer) {
            JsonConsumerAdapter jsonConsumerAdapter = new JsonConsumerAdapter((JsonConsumer) agent1);
            return jsonConsumerAdapter;
        }
        return (Consumer<YoubetMessage>) agent1;
    }
}

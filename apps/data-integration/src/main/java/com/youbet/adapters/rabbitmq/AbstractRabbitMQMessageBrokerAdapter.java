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


public abstract class AbstractRabbitMQMessageBrokerAdapter implements MessageBrokerPort {
    
    public static final int DEFAULT_PORT = 5672;
    public static final String PROP_RABBIT_HOST = "rabbit.host";
    public static final String PROP_RABBIT_PORT = "rabbit.port";
    public static final String PROP_RABBIT_USERNAME = "rabbit.user";
    public static final String PROP_RABBIT_PASSWORD = "rabbit.password";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRabbitMQMessageBrokerAdapter.class.getClass());
    
    protected final AppConfigurationPort appConfigurationPort;
    protected ConnectionFactory factory;
    
    public AbstractRabbitMQMessageBrokerAdapter(AppConfigurationPort appConfigurationPort) {
        
        this.appConfigurationPort = appConfigurationPort;
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
    
    
    protected void sendMessage(String queueName, YoubetMessage message) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish(queueName, message.getRoutingKey(), null, message.getBody());
        } catch (Exception e) {
            throw new YoubetException(
                    MessageFormat.format("Cannot send the message {0} to the queue {1}", message, Queues.QUEUE_LEAGUE),
                    e);
        }
    }
    
    public <T> void declareConsumer(String queueName, JsonConsumer<T> agent1) {
        final Consumer consumerAdapter = adapt(agent1);
        declareConsumer(queueName, consumerAdapter);
    }
    
    public <T> void declareConsumer(String queueName, Consumer agent) {
        Connection connection = null;
        try {
            // Auto declare queue
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            LOGGER.info("Declare queue -> {} if required", queueName);
            
            /** DLQ AND EXCHANGE DECLARATION **/
            // Auto declare if required
            String dlqExchangeName = queueName + ".DLX";
            String dlqQueueName = queueName + ".DLQ";
            channel.queueDeclare(dlqQueueName, true, false, false, null);
            channel.exchangeDeclare(dlqExchangeName, "direct", true);
            channel.queueBind(dlqQueueName, dlqExchangeName, dlqQueueName);
            
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-dead-letter-exchange", dlqExchangeName);
            args.put("x-dead-letter-routing-key", dlqQueueName);
            
            channel.queueDeclare(queueName, true, false, false, args);
            
            
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    var deliveryTag = envelope.getDeliveryTag();
                    try {
                        LOGGER.debug("Received message envelope={}, consumerTag={}, bodyLength={}", envelope,
                                     consumerTag,
                                     body.length);
                        YoubetMessage youbetMessage = new YoubetMessage(body);
                        agent.handleRequest(youbetMessage);
                        channel.basicAck(deliveryTag, false);
                    } catch (Exception e) {
                        LOGGER.error("Cannot process the message", e);
                        channel.basicNack(deliveryTag, false, false);
                    }
                    //
                }
            };
            channel.basicConsume(queueName, false, agent.getConsumerTag(), consumer);
        } catch (IOException e) {
            throw new YoubetException(e);
        } catch (TimeoutException e) {
            throw new YoubetException(e);
        }
    }
    
    private <T> Consumer adapt(JsonConsumer<T> agent1) {
        JsonConsumerAdapter jsonConsumerAdapter = new JsonConsumerAdapter((JsonConsumer) agent1);
        return jsonConsumerAdapter;
    }
}

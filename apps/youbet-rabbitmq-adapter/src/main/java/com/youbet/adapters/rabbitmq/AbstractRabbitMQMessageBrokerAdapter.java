package com.youbet.adapters.rabbitmq;

import com.rabbitmq.client.*;
import com.youbet.ports.AppConfigurationPort;
import com.youbet.ports.messagebroker.Consumer;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
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
    protected static final String DATA_INTEGRATION_EXCHANGE = "DataIntegrationExchange";
    
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
    
    
    protected void sendMessage(String routingKey, YoubetMessage message) {
        LOGGER.debug("Send to {}, body = {}", routingKey, message);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish(DATA_INTEGRATION_EXCHANGE, routingKey, null, message.getBody());
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
        try {
            // Connection stay opened until the program crash or end. 
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            LOGGER.info("Declare queue -> {} if required", queueName);
            
            declareQueue(queueName, channel);
            
            DefaultConsumer consumer = new YoubetRabbitMqConsumer(channel, agent);
            channel.basicConsume(queueName, false, agent.getConsumerTag(), consumer);
        } catch (IOException e) {
            throw new YoubetException("Cannot declare the RabbitMQ consumer", e);
        } catch (TimeoutException e) {
            throw new YoubetException("Cannot declare the RabbitMQ consumer, timeout happened", e);
        }
    }
    
    /**
     * Declared required queue, the DLQ and the exchange  in RabbitMQ if missing.
     * @param queueName the queue to declare if missing.
     */
    public void declareRequiredQueue(String queueName) {
        
        try (Connection connection = factory.newConnection();
             final Channel channel = connection.createChannel();) {
            
            declareQueue(queueName, channel);
            ;
        } catch (IOException e) {
            throw new YoubetException("Cannot declare the RabbitMQ consumer", e);
        } catch (TimeoutException e) {
            throw new YoubetException("Cannot declare the RabbitMQ consumer, timeout happened", e);
        }
    }
    
    private static void declareQueue(String queueName, Channel channel) throws IOException {
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
        channel.exchangeDeclare(DATA_INTEGRATION_EXCHANGE, "direct", true);
        channel.queueBind(queueName, DATA_INTEGRATION_EXCHANGE, queueName);
    }
    
    private <T> Consumer adapt(JsonConsumer<T> agent1) {
        JsonConsumerAdapter jsonConsumerAdapter = new JsonConsumerAdapter((JsonConsumer) agent1);
        return jsonConsumerAdapter;
    }
    
    private static class YoubetRabbitMqConsumer extends DefaultConsumer {
        private final Channel channel;
        private final Consumer agent;
        
        public YoubetRabbitMqConsumer(Channel channel, Consumer agent) {
            super(channel);
            this.channel = channel;
            this.agent = agent;
        }
        
        @Override
        public void handleDelivery(
                String consumerTag,
                Envelope envelope,
                AMQP.BasicProperties properties,
                byte[] body) throws IOException {
            var deliveryTag = envelope.getDeliveryTag();
            try {
                LOGGER.debug("{}> Received message envelope={}, consumerTag={}, bodyLength={}", agent.getConsumerTag(),
                             envelope,
                             consumerTag,
                             body.length);
                YoubetMessage youbetMessage = new YoubetMessage(envelope, body);
                agent.handleRequest(youbetMessage);
                channel.basicAck(deliveryTag, false);
            } catch (Exception e) {
                LOGGER.error("Cannot process the message", e);
                channel.basicNack(deliveryTag, false, false);
            }
            //
        }
    }
}

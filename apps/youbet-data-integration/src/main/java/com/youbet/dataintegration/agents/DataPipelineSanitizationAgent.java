package com.youbet.dataintegration.agents;

import com.youbet.dataintegration.utils.StringUtils;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPipelineSanitizationAgent implements JsonConsumer<ExternalProviderMatchRegisteredEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataPipelineSanitizationAgent.class);
    private final MessageBrokerPort messageBrokerPort;

    public DataPipelineSanitizationAgent(MessageBrokerPort messageBrokerPort) {

        this.messageBrokerPort = messageBrokerPort;
    }

    @Override public void handleRequest(YoubetMessage youbetMessage, ExternalProviderMatchRegisteredEvent event) {
        if (StringUtils.isBlank(event.getLeague())) {
            throw new IllegalArgumentException("League is expected in the event");
        }
        if (StringUtils.isBlank(event.getCountry())) {
            throw new IllegalArgumentException("Country is expected in the event");
        }
        if (StringUtils.isBlank(event.getHomeTeam())) {
            throw new IllegalArgumentException("Team is expected in the event");
        }
        if (null == event.getDate()) {
            throw new IllegalArgumentException("Date of the match is expected in the event");
        }
        // Transfer the message
        messageBrokerPort.dispatchToAggregateLeagueQueue(youbetMessage);
    }
    
    @Override public String getConsumerTag() {
        return DataPipelineSanitizationAgent.class.getName();
    }
    
    @Override public Class<ExternalProviderMatchRegisteredEvent> supportedImpl() {
        return ExternalProviderMatchRegisteredEvent.class;
    }
}

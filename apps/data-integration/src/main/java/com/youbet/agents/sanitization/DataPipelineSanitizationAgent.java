package com.youbet.agents.sanitization;

import com.youbet.agents.utils.StringUtils;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPipelineSanitizationAgent implements JsonConsumer<ExternalProviderMatchRegisteredEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataPipelineSanitizationAgent.class);
    private final MessageBrokerPort messageBrokerPort;

    public DataPipelineSanitizationAgent(MessageBrokerPort messageBrokerPort) {

        this.messageBrokerPort = messageBrokerPort;
    }

    @Override
    public void handleRequest(ExternalProviderMatchRegisteredEvent event) {
        if (StringUtils.isBlank(event.getLeague())) {
            throw new IllegalArgumentException("League is expected in the event");
        }
        if (StringUtils.isBlank(event.getCountry())) {
            throw new IllegalArgumentException("Country is expected in the event");
        }
        if (StringUtils.isBlank(event.getMatchName())) {
            throw new IllegalArgumentException("Match name is expected in the event");
        }
        if (null == event.getDate()) {
            throw new IllegalArgumentException("Date of the match is expected in the event");
        }
        messageBrokerPort.dispatchMessageAggregateTeamQueue(YoubetMessage.toJsonBody(event));
    }
    
    @Override public Class<ExternalProviderMatchRegisteredEvent> supportedImpl() {
        return ExternalProviderMatchRegisteredEvent.class;
    }
}

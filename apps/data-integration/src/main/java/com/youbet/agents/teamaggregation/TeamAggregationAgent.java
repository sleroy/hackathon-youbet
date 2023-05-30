package com.youbet.agents.teamaggregation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.agents.domain.MatchReferenceBaseEvent;
import com.youbet.agents.domain.Team;
import com.youbet.ports.MatchSystemRepositoryPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonConsumer;
import com.youbet.utils.JsonUtils;
import com.youbet.utils.YoubetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TeamAggregationAgent implements JsonConsumer<MatchReferenceBaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamAggregationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private MatchSystemRepositoryPort matchSystemRepositoryPort;
    
    public TeamAggregationAgent(MessageBrokerPort messageBrokerPort,
                                MatchSystemRepositoryPort matchSystemRepositoryPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepositoryPort = matchSystemRepositoryPort;
    }
    
    @Override public String getConsumerTag() {
        return TeamAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchReferenceBaseEvent event) {
        ObjectNode jsonNode = (ObjectNode) youbetMessage.toJson();
        ObjectNode referenceNode = (ObjectNode) jsonNode.get("references");
        Optional<Team> existingTeam = matchSystemRepositoryPort.findTeam(event.getTeamName());
        if (existingTeam.isEmpty()) {
            // Create the existingTeam
            Team team = matchSystemRepositoryPort.createTeam(event.getTeamName());
            referenceNode.put("team", team.getTeamId());
        } else {
            referenceNode.put("team", existingTeam.get().getTeamId());
        }
        messageBrokerPort.dispatchMessageMatchSystemMatchRegistrationQueue(YoubetMessage.fromJson(jsonNode));
    }
    
    @Override public Class<MatchReferenceBaseEvent> supportedImpl() {
        return MatchReferenceBaseEvent.class;
    }
}

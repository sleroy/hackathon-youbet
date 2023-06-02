package com.youbet.dataintegration.agents.teamaggregation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.MatchReferenceBaseEvent;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TeamAggregationAgent implements JsonConsumer<MatchReferenceBaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamAggregationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort;
    
    public TeamAggregationAgent(MessageBrokerPort messageBrokerPort,
                                com.youbet.ports.matchsystem.MatchSystemRepositoryPort matchSystemRepositoryPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepositoryPort = matchSystemRepositoryPort;
    }
    
    @Override public String getConsumerTag() {
        return TeamAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchReferenceBaseEvent event) {
        ObjectNode jsonNode = (ObjectNode) youbetMessage.toJson();
        ObjectNode referenceNode = (ObjectNode) jsonNode.get("references");
        loadTeam(event, referenceNode, event.getHomeTeam(),  MatchReferenceBaseEvent.ATTR_HOME_TEAM);
        loadTeam(event, referenceNode, event.getAwayTeam(),  MatchReferenceBaseEvent.ATTR_AWAY_TEAM);
        messageBrokerPort.dispatchToMatchSystemMatchRegistrationQueue(jsonNode);
    }
    
    private void loadTeam(MatchReferenceBaseEvent event, ObjectNode referenceNode, String teamName, String attributeName) {
        Optional<com.youbet.ports.matchsystem.Team> existingTeam = matchSystemRepositoryPort.findTeam(teamName);
        if (existingTeam.isEmpty()) {
            // Create the existingTeam
            com.youbet.ports.matchsystem.Team team = matchSystemRepositoryPort.createTeam(teamName);
            referenceNode.put(attributeName, team.getId());
        } else {
            referenceNode.put(attributeName, existingTeam.get().getId());
        }
    }
    
    @Override public Class<MatchReferenceBaseEvent> supportedImpl() {
        return MatchReferenceBaseEvent.class;
    }
}

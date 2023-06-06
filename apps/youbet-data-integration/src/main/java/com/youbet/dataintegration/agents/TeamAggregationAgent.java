package com.youbet.dataintegration.agents;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.MatchBaseInfoView;
import com.youbet.domain.requests.MatchReferences;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TeamAggregationAgent implements JsonConsumer<MatchBaseInfoView> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamAggregationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private MatchSystemRepository matchSystemRepository;
    
    public TeamAggregationAgent(MessageBrokerPort messageBrokerPort,
                                MatchSystemRepository matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return TeamAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchBaseInfoView event) {
        ObjectNode jsonNode = (ObjectNode) youbetMessage.toJson();
        ObjectNode referenceNode = (ObjectNode) jsonNode.get("references");
        loadTeam(event, referenceNode, event.getHomeTeam(), MatchReferences.ATTR_HOME_TEAM);
        loadTeam(event, referenceNode, event.getAwayTeam(), MatchReferences.ATTR_AWAY_TEAM);
        messageBrokerPort.dispatchToPlayerAggregation(jsonNode);
    }
    
    private void loadTeam(MatchBaseInfoView event, ObjectNode referenceNode, String teamName, String attributeName) {
        Optional<com.youbet.ports.matchsystem.Team> existingTeam = matchSystemRepository.findTeam(teamName);
        if (existingTeam.isEmpty()) {
            // Create the existingTeam
            com.youbet.ports.matchsystem.Team team = matchSystemRepository.createTeam(teamName);
            referenceNode.put(attributeName, team.getId());
        } else {
            referenceNode.put(attributeName, existingTeam.get().getId());
        }
    }
    
    @Override public Class<MatchBaseInfoView> supportedImpl() {
        return MatchBaseInfoView.class;
    }
}

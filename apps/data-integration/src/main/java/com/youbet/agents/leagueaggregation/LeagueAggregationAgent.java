package com.youbet.agents.leagueaggregation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.agents.domain.League;
import com.youbet.agents.domain.MatchReferenceBaseEvent;
import com.youbet.agents.domain.Team;
import com.youbet.ports.MatchSystemRepositoryPort;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonConsumer;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LeagueAggregationAgent implements JsonConsumer<MatchReferenceBaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAggregationAgent.class);
    private final MessageBrokerPort messageBrokerPort;
    private MatchSystemRepositoryPort matchSystemRepositoryPort;
    
    public LeagueAggregationAgent(MessageBrokerPort messageBrokerPort,
                                  MatchSystemRepositoryPort matchSystemRepositoryPort) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepositoryPort = matchSystemRepositoryPort;
    }
    
    @Override public String getConsumerTag() {
        return LeagueAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchReferenceBaseEvent event) {
        ObjectNode jsonNode = (ObjectNode) youbetMessage.toJson();
        ObjectNode referenceNode = addReferenceNodeIfNecessary(jsonNode);
        Optional<League> existingLeague = matchSystemRepositoryPort.findLeague(event.getCountry(), event.getLeague());
        if (existingLeague.isEmpty()) {
            // Create a new league
            League league = matchSystemRepositoryPort.createLeague(event.getCountry(), event.getLeague());
            referenceNode.put("league", league.getLeagueId());
        } else {
            referenceNode.put("league", existingLeague.get().getLeagueId());
        }
        messageBrokerPort.dispatchMessageAggregateTeamQueue(YoubetMessage.fromJson(jsonNode));
    }
    
    private static ObjectNode addReferenceNodeIfNecessary(ObjectNode jsonNode) {
        ObjectNode referenceNode;
        if (jsonNode.has("references")) {
            jsonNode.set("references", referenceNode = JsonUtils.createObjectNode());
        } else {
            referenceNode = (ObjectNode) jsonNode.get("references");
        }
        return referenceNode;
    }
    
    @Override public Class<MatchReferenceBaseEvent> supportedImpl() {
        return MatchReferenceBaseEvent.class;
    }
}

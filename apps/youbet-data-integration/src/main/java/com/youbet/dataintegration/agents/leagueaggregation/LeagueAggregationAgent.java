package com.youbet.dataintegration.agents.leagueaggregation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.MatchReferenceBaseEvent;
import com.youbet.ports.matchsystem.League;
import com.youbet.ports.matchsystem.MatchSystemRepositoryPort;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LeagueAggregationAgent implements JsonConsumer<MatchReferenceBaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAggregationAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    private final MatchSystemRepositoryPort matchSystemRepositoryPort;
    
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
            referenceNode.put(MatchReferenceBaseEvent.ATTR_LEAGUE, league.getId());
        } else {
            referenceNode.put(MatchReferenceBaseEvent.ATTR_LEAGUE, existingLeague.get().getId());
        }
        messageBrokerPort.dispatchToAggregateTeamQueue(jsonNode);
    }
    
    private static ObjectNode addReferenceNodeIfNecessary(ObjectNode jsonNode) {
        ObjectNode referenceNode;
        if (!jsonNode.has(MatchReferenceBaseEvent.ATTR_REFERENCES)) {
            jsonNode.set(MatchReferenceBaseEvent.ATTR_REFERENCES, referenceNode = JsonUtils.createObjectNode());
        } else {
            referenceNode = (ObjectNode) jsonNode.get(MatchReferenceBaseEvent.ATTR_REFERENCES);
        }
        return referenceNode;
    }
    
    @Override public Class<MatchReferenceBaseEvent> supportedImpl() {
        return MatchReferenceBaseEvent.class;
    }
}

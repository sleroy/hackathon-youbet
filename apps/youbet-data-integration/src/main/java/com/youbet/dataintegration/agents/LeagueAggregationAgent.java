package com.youbet.dataintegration.agents;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youbet.domain.MatchBaseInfoView;
import com.youbet.domain.requests.MatchReferences;
import com.youbet.ports.matchsystem.League;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LeagueAggregationAgent implements JsonConsumer<MatchBaseInfoView> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAggregationAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    private final MatchSystemRepository matchSystemRepository;
    
    public LeagueAggregationAgent(MessageBrokerPort messageBrokerPort,
                                  MatchSystemRepository matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return LeagueAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchBaseInfoView event) {
        ObjectNode jsonNode = (ObjectNode) youbetMessage.toJson();
        ObjectNode referenceNode = addReferenceNodeIfNecessary(jsonNode);
        Optional<League> existingLeague = matchSystemRepository.findLeague(event.getCountry(), event.getLeague());
        if (existingLeague.isEmpty()) {
            // Create a new league
            League league = matchSystemRepository.createLeague(event.getCountry(), event.getLeague());
            referenceNode.put(MatchReferences.ATTR_LEAGUE, league.getId());
            referenceNode.put(MatchReferences.ATTR_COUNTRY_ID, league.getCountry_id());
        } else {
            referenceNode.put(MatchReferences.ATTR_LEAGUE, existingLeague.get().getId());
            referenceNode.put(MatchReferences.ATTR_COUNTRY_ID, existingLeague.get().getCountry_id());
        }
        messageBrokerPort.dispatchToAggregateTeamQueue(jsonNode);
    }
    
    private static ObjectNode addReferenceNodeIfNecessary(ObjectNode jsonNode) {
        ObjectNode referenceNode;
        if (!jsonNode.has(MatchBaseInfoView.ATTR_REFERENCES)) {
            jsonNode.set(MatchBaseInfoView.ATTR_REFERENCES, referenceNode = JsonUtils.createObjectNode());
        } else {
            referenceNode = (ObjectNode) jsonNode.get(MatchBaseInfoView.ATTR_REFERENCES);
        }
        return referenceNode;
    }
    
    @Override public Class<MatchBaseInfoView> supportedImpl() {
        return MatchBaseInfoView.class;
    }
}

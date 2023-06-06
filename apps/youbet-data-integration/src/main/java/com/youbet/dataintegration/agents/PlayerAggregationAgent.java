package com.youbet.dataintegration.agents;

import com.youbet.domain.requests.Events;
import com.youbet.domain.requests.MatchBaseInfoRequest;
import com.youbet.domain.requests.MatchReferences;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.ports.matchsystem.MatchSystemRepository;
import com.youbet.ports.messagebroker.JsonConsumer;
import com.youbet.ports.messagebroker.MessageBrokerPort;
import com.youbet.ports.messagebroker.YoubetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This agent aggregates all the player data to the MatchRegisterEvent.
 */
public class PlayerAggregationAgent implements JsonConsumer<MatchBaseInfoRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAggregationAgent.class);
    
    private final MessageBrokerPort messageBrokerPort;
    private final MatchSystemRepository matchSystemRepository;
    
    public PlayerAggregationAgent(MessageBrokerPort messageBrokerPort,
                                  MatchSystemRepository matchSystemRepository) {
        
        this.messageBrokerPort = messageBrokerPort;
        this.matchSystemRepository = matchSystemRepository;
    }
    
    @Override public String getConsumerTag() {
        return PlayerAggregationAgent.class.getName();
    }
    
    @Override public void handleRequest(YoubetMessage youbetMessage, MatchBaseInfoRequest event) {
        if (event.getEventName().equals(Events.EVENT_MATCH_REGISTRATION_EVENT)) {
            aggregatePlayersOnMatchRegisterEvent(youbetMessage);
        } else {
            messageBrokerPort.dispatchToMatchEventNotifier(youbetMessage);
        }
    }
    
    private void aggregatePlayersOnMatchRegisterEvent(YoubetMessage youbetMessage) {
        // We aggregate the player information
        MatchSystemMatchRegistrationRequest request = youbetMessage.toJson(MatchSystemMatchRegistrationRequest.class);
        MatchReferences references = request.getReferences();
        references.setHome_player_1(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_1()));
        references.setHome_player_2(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_2()));
        references.setHome_player_3(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_3()));
        references.setHome_player_4(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_4()));
        references.setHome_player_5(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_5()));
        references.setHome_player_6(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_6()));
        references.setHome_player_7(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_7()));
        references.setHome_player_8(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_8()));
        references.setHome_player_9(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_9()));
        references.setHome_player_10(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_10()));
        references.setHome_player_11(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_11()));
        
        references.setAway_player_1(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_1()));
        references.setAway_player_2(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_2()));
        references.setAway_player_3(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_3()));
        references.setAway_player_4(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_4()));
        references.setAway_player_5(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_5()));
        references.setAway_player_6(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_6()));
        references.setAway_player_7(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_7()));
        references.setAway_player_8(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_8()));
        references.setAway_player_9(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_9()));
        references.setAway_player_10(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_10()));
        references.setAway_player_11(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_11()));
        
        
        references.setHome_player_X1(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X1()));
        references.setHome_player_X2(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X2()));
        references.setHome_player_X3(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X3()));
        references.setHome_player_X4(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X4()));
        references.setHome_player_X5(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X5()));
        references.setHome_player_X6(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X6()));
        references.setHome_player_X7(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X7()));
        references.setHome_player_X8(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X8()));
        references.setHome_player_X9(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X9()));
        references.setHome_player_X10(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X10()));
        references.setHome_player_X11(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_X11()));
        
        references.setAway_player_X1(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X1()));
        references.setAway_player_X2(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X2()));
        references.setAway_player_X3(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X3()));
        references.setAway_player_X4(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X4()));
        references.setAway_player_X5(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X5()));
        references.setAway_player_X6(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X6()));
        references.setAway_player_X7(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X7()));
        references.setAway_player_X8(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X8()));
        references.setAway_player_X9(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X9()));
        references.setAway_player_X10(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X10()));
        references.setAway_player_X11(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_X11()));
        
        references.setHome_player_Y1(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y1()));
        references.setHome_player_Y2(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y2()));
        references.setHome_player_Y3(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y3()));
        references.setHome_player_Y4(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y4()));
        references.setHome_player_Y5(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y5()));
        references.setHome_player_Y6(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y6()));
        references.setHome_player_Y7(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y7()));
        references.setHome_player_Y8(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y8()));
        references.setHome_player_Y9(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y9()));
        references.setHome_player_Y10(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y10()));
        references.setHome_player_Y11(matchSystemRepository.createOrUpdatePlayer(request.getHome_player_Y11()));
        
        references.setAway_player_Y1(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y1()));
        references.setAway_player_Y2(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y2()));
        references.setAway_player_Y3(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y3()));
        references.setAway_player_Y4(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y4()));
        references.setAway_player_Y5(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y5()));
        references.setAway_player_Y6(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y6()));
        references.setAway_player_Y7(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y7()));
        references.setAway_player_Y8(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y8()));
        references.setAway_player_Y9(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y9()));
        references.setAway_player_Y10(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y10()));
        references.setAway_player_Y11(matchSystemRepository.createOrUpdatePlayer(request.getAway_player_Y11()));
        
        messageBrokerPort.dispatchToMatchEventNotifier(request);
    }
    
    @Override public Class<MatchBaseInfoRequest> supportedImpl() {
        return MatchBaseInfoRequest.class;
    }
}

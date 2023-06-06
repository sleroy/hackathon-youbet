package com.youbet.agents.sanitization;

import com.youbet.agents.stubs.MessageBrokerPortStub;
import com.youbet.dataintegration.agents.DataPipelineSanitizationAgent;
import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.ports.messagebroker.YoubetMessage;
import com.youbet.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DataPipelineSanitizationAgentTest {
    
    private static final String EXAMPLE_ROUTE = "route_example";
    private DataPipelineSanitizationAgent agent;
    private MessageBrokerPortStub messageBrokerPort;
    
    @BeforeEach
    public void beforeEach() {
        JsonUtils.initializeWithDefault();
        messageBrokerPort = new MessageBrokerPortStub();
        agent = new DataPipelineSanitizationAgent(messageBrokerPort);
    }
    
    @org.junit.jupiter.api.Test
    void leagueValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        validateCheck(event, "League is expected in the event");
    }
    
    @org.junit.jupiter.api.Test
    void countryValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        validateCheck(event, "Country is expected in the event");
    }
    
    
    @org.junit.jupiter.api.Test
    void teamNameValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        validateCheck(event, "Team is expected in the event");
    }
    
    
    @org.junit.jupiter.api.Test
    void matchNameValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        event.setHomeTeam(MatchEventStub.TEAM_EXAMPLE1);
        event.setAwayTeam(MatchEventStub.TEAM_EXAMPLE2);
        validateCheck(event, "Match name is expected in the event");
    }
    
    
    @org.junit.jupiter.api.Test
    void matchDateValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        event.setHomeTeam(MatchEventStub.TEAM_EXAMPLE1);
        event.setAwayTeam(MatchEventStub.TEAM_EXAMPLE2);
        validateCheck(event, "Date of the match is expected in the event");
    }
    
    @org.junit.jupiter.api.Test
    void validEvent() {
        
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        event.setHomeTeam(MatchEventStub.TEAM_EXAMPLE1);
        event.setAwayTeam(MatchEventStub.TEAM_EXAMPLE2);
        event.setDate(LocalDateTime.now());
        Assertions.assertDoesNotThrow(() -> agent.handleRequest(YoubetMessage.fromEvent(event), event));
        Assertions.assertEquals(messageBrokerPort.getSentEvents().size(), 1, "Expected one event to be sent");
    }
    
    
    private void validateCheck(ExternalProviderMatchRegisteredEvent event, String errorMessage) {
        IllegalArgumentException except = assertThrows(IllegalArgumentException.class,
                                                       () -> agent.handleRequest(
                                                               YoubetMessage.fromEvent(event), event),
                                                       "Check should be triggered");
        Assertions.assertEquals(errorMessage, except.getMessage());
    }
}
package com.youbet.agents.sanitization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youbet.agents.stubs.MessageBrokerPortStub;
import com.youbet.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DataPipelineSanitizationAgentTest {
    
    private static final String LEAGUE_EXAMPLE = "LeagueExample";
    private static final String MATCH_EXAMPLE = "MatchExample";
    private static final String COUNTRY_EXAMPLE = "CountryExample";
    
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
        event.setLeague(LEAGUE_EXAMPLE);
        validateCheck(event, "Country is expected in the event");
    }
    
    @org.junit.jupiter.api.Test
    void matchNameValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(LEAGUE_EXAMPLE);
        event.setCountry(COUNTRY_EXAMPLE);
        
        validateCheck(event, "Match name is expected in the event");
    }
    
    
    @org.junit.jupiter.api.Test
    void matchDateValidationCheck() {
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(LEAGUE_EXAMPLE);
        event.setCountry(COUNTRY_EXAMPLE);
        event.setMatchName(MATCH_EXAMPLE);
        validateCheck(event, "Date of the match is expected in the event");
    }
    
    @org.junit.jupiter.api.Test
    void validEvent() {
        
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(LEAGUE_EXAMPLE);
        event.setCountry(COUNTRY_EXAMPLE);
        event.setMatchName(MATCH_EXAMPLE);
        event.setDate(LocalDateTime.now());
        Assertions.assertDoesNotThrow(() -> agent.handleRequest(event));
        Assertions.assertEquals(messageBrokerPort.getSentEvents().size(), 1, "Expected one event to be sent");
    }
    
    
    
    private void validateCheck(ExternalProviderMatchRegisteredEvent event, String errorMessage) {
        IllegalArgumentException except = assertThrows(IllegalArgumentException.class,
                                                       () -> agent.handleRequest(event),
                                                       "Check should be triggered");
        Assertions.assertEquals(errorMessage, except.getMessage());
    }
}
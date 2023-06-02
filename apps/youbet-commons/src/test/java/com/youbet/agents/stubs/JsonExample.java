package com.youbet.agents.stubs;

import com.youbet.domain.ExternalProviderMatchRegisteredEvent;
import com.youbet.utils.JsonUtils;

import java.time.LocalDateTime;

public class JsonExample {
    
    public static void main(String[] args) {
        JsonUtils.initializeWithDefault();
        
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        event.setMatchName(MatchEventStub.MATCH_EXAMPLE);
        event.setHomeTeam(MatchEventStub.TEAM_EXAMPLE1);
        event.setAwayTeam(MatchEventStub.TEAM_EXAMPLE2);
        event.setDate(LocalDateTime.now());
    
        System.out.println(JsonUtils.toJsonString(event));
    
        
    }
}

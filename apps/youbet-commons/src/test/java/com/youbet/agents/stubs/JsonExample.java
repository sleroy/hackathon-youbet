package com.youbet.agents.stubs;

import com.youbet.domain.externalprov.ExternalProviderMatchRegisteredEvent;
import com.youbet.domain.externalprov.PlayerDetails;
import com.youbet.utils.JsonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JsonExample {
    
    public static void main(String[] args) {
        JsonUtils.initializeWithDefault();
        
        var event = new ExternalProviderMatchRegisteredEvent();
        event.setLeague(MatchEventStub.LEAGUE_EXAMPLE);
        event.setCountry(MatchEventStub.COUNTRY_EXAMPLE);
        event.setHomeTeam(MatchEventStub.TEAM_EXAMPLE1);
        event.setAwayTeam(MatchEventStub.TEAM_EXAMPLE2);
        event.setAway_player_1(new PlayerDetails("Aaron Cresswell", LocalDate.of(1989, 12, 15), 170, 146));
        event.setDate(LocalDateTime.now());
        
        System.out.println(JsonUtils.toJsonString(event));
        
        
    }
}

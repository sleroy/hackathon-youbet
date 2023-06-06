package com.youbet.ports.matchsystem;

import com.youbet.domain.events.MatchPredictionOddsUpdatedEvent;
import com.youbet.domain.externalprov.PlayerDetails;
import com.youbet.domain.requests.MatchSystemMatchRegistrationRequest;
import com.youbet.domain.requests.MatchSystemMatchUpdateRequest;

import java.util.Optional;

public interface MatchSystemRepository {
    Optional<Team> findTeam(String team);
    
    Team createTeam(String teamName);
    
    Optional<League> findLeague(String country, String league);
    
    League createLeague(String country, String league);
    
    /**
     * Create or update a player with details from a registered match.
     *
     * @param playerDetails the player detail
     * @return the player ID.
     */
    Integer createOrUpdatePlayer(PlayerDetails playerDetails);
    
    void createMatch(MatchSystemMatchRegistrationRequest event);
    
    /**
     * Update an existing match with new details.
     *
     * @param event the event that contains the information to update.
     */
    void updateMatch(MatchSystemMatchUpdateRequest event);
    /**
     * Update the odds of an existing match
     *
     * @param event the event that contains the information to update.
     */
    void updateMatchOdds(MatchPredictionOddsUpdatedEvent event);
}

package com.youbet.ports.matchsystem;

import java.util.Optional;

public interface MatchSystemRepositoryPort {
    Optional<Team> findTeam(String team);
    
    Team createTeam(String teamName);
    
    Optional<League> findLeague(String country, String league);
    
    League createLeague(String country, String league);
}
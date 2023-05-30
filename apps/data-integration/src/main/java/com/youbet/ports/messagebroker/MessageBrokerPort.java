package com.youbet.ports.messagebroker;

public interface MessageBrokerPort {
    void start();
    
    void dispatchMessageAggregateLeagueQueue(YoubetMessage message);
    
    void dispatchMessageMatchSystemMatchRegistrationQueue(YoubetMessage message);
    
    void dispatchMessageAggregateTeamQueue(YoubetMessage youbetMessage);
}

package com.youbet.ports.messagebroker;

public interface MessageBrokerPort {
    void start();

    void dispatchMessageAggregateTeamQueue(YoubetMessage message);
}

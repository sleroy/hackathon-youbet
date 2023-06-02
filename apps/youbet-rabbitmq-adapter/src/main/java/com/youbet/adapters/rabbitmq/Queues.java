package com.youbet.adapters.rabbitmq;

public class Queues {
    public static final String QUEUE_SANITIZATION = "DataPipelineMatchRegistrationSanitizationQueue";
    public static final String QUEUE_LEAGUE = "DataPipelineAggregateLeagueQueue";
    public static final String QUEUE_TEAM = "DataPipelineAggregateTeamQueue";
    public static final String QUEUE_MATCH_DISPATCH = "DataPipelineMatchEventDispatcherQueue";
    public static final String QUEUE_EXT_MATCH_REGISTRATION = "ExternalProviderMatchRegistrationQueue";
    public static final String QUEUE_EXT_MATCH_UPDATE = "ExternalProviderMatchUpdateQueue";
    public static final String QUEUE_MATCH_PREDICTION_STORAGE_QUEUE = "MatchPredictionMatchDataStorageQueue";
    public static final String QUEUE_MATCH_SYSTEM_REGISTRATION_QUEUE = "MatchSystemMatchRegistrationQueue";
    public static final String QUEUE_MATCH_SYSTEM_UPDATE_QUEUE = "MatchSystemMatchUpdateQueue";
}
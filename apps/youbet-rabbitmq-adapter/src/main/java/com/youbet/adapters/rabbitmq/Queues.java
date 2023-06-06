package com.youbet.adapters.rabbitmq;

public class Queues {
    
    public static final String QUEUE_EXT_MATCH_REGISTRATION = "ExternalProviderMatchRegistrationQueue";
    public static final String QUEUE_EXT_MATCH_UPDATE = "ExternalProviderMatchUpdateQueue";
    
    public static final String QUEUE_SANITIZATION = "DataPipelineMatchRegistrationSanitizationQueue";
    public static final String QUEUE_LEAGUE = "DataPipelineAggregateLeagueQueue";
    public static final String QUEUE_TEAM = "DataPipelineAggregateTeamQueue";
    public static final String QUEUE_PLAYER = "DataPipelineAggregatePlayerQueue";
    public static final String QUEUE_MATCH_EVENT_NOTIFIER = "DataPipelineMatchEventDispatcherQueue";
    
    /** Prediction Queues */
    public static final String QUEUE_MATCH_PREDICTION_STORAGE = "MatchPredictionMatchDataStorageQueue";
    public static final String QUEUE_MATCH_PREDICTION_SCORE = "MatchPredictionQueue";
    
    /** Match System queues */
    public static final String QUEUE_MATCH_SYSTEM_REGISTRATION = "MatchSystemMatchRegistrationQueue";
    public static final String QUEUE_MATCH_SYSTEM_UPDATE = "MatchSystemMatchUpdateQueue";
    public static final String QUEUE_MATCH_SYSTEM_UPDATE_ODDS = "MatchSystemUpdateMatchOddsQueue";

    
}

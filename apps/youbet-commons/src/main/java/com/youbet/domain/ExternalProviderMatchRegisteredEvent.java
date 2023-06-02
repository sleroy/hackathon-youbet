package com.youbet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProviderMatchRegisteredEvent extends MatchReferenceBaseEvent {
}

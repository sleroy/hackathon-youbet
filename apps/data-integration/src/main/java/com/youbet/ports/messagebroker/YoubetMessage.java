package com.youbet.ports.messagebroker;

import com.youbet.agents.sanitization.ExternalProviderMatchRegisteredEvent;
import com.youbet.utils.JsonUtils;

/**
 * This interface is a wrapper to manipulate messages through the MessageBroker.
 */
public class YoubetMessage {

    private byte[] body;

    public YoubetMessage(byte[] body) {
        this.body = body;
    }

    public static YoubetMessage toJsonBody(ExternalProviderMatchRegisteredEvent event) {
        return new YoubetMessage(JsonUtils.toJsonBytes(event));
    }

    public byte[] getBody() {
        return body;
    }
}

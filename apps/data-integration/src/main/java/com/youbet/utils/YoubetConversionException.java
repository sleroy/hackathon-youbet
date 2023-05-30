package com.youbet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class YoubetConversionException extends RuntimeException {

    public YoubetConversionException(JsonProcessingException e) {
        super("Object cannot be converted", e);
    }
}

package com.youbet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper objectMapper;

    public static void initialize(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }


    /**
     * Converts the event into a JSON String.
     *
     * @param event the event as an java object
     * @return the JSON string
     */
    public static String toJsonString(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new YoubetConversionException(e);
        }
    }


    /**
     * Converts the event into a JSON String.
     *
     * @param event the event as an java object
     * @return the JSON string
     */
    public static byte[] toJsonBytes(Object event) {
        try {
            return objectMapper.writeValueAsBytes(event);
        } catch (JsonProcessingException e) {
            throw new YoubetConversionException(e);
        }
    }

    public static Object instantiate(Class impl, byte[] body) {
        try {
            return objectMapper.reader().readValue(body, impl);
        } catch (IOException e) {
            throw new YoubetException(e);
        }
    }
}

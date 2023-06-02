package com.youbet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonUtils {
    public static ObjectMapper objectMapper;
    
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
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(event);
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
    
    public static void initializeWithDefault() {
        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.findAndRegisterModules();
        initialize(objectMapper1);
    }
    
    public static JsonNode parseBytes(byte[] body) {
        try {
            return objectMapper.readTree(body);
        } catch (Exception e) {
            throw new YoubetException("Cannot parse the payload as a JSON object", e);
        }
    }
    
    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }
    
    public static JsonNode toJsonNode(Integer teamId) {
        return objectMapper.valueToTree(teamId);
    }
    
    /**
     * Parse raw bytes into JSON and instantiate the object.
     *
     * @param body      the body
     * @param implClass the Java implementation of the JSON object.
     * @param <T>       the expected. type of object unserialized.
     * @return the object instantiated
     */
    public static <T> T parseBytes(byte[] body, Class<T> implClass) {
        try {
            return objectMapper.readValue(body, implClass);
        } catch (Exception e) {
            throw new YoubetException("Cannot parse the payload as a JSON object", e);
        }
    }
}

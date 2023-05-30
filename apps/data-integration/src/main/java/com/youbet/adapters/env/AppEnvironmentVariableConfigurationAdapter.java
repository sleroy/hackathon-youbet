package com.youbet.adapters.env;

import com.youbet.ports.AppConfigurationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Validate;

import java.util.Locale;

public class AppEnvironmentVariableConfigurationAdapter implements AppConfigurationPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppEnvironmentVariableConfigurationAdapter.class);
    
    @Override public String getProperty(String key) {
        String propValue = System.getenv(convertKey(key));
        if (propValue == null) {
            LOGGER.error("Cannot retrieve the property {}", convertKey(key));
        }
        return propValue;
    }
    
    public static String convertKey(String key) {
        Validate.notNull(key, "Property %s name must be provided", key);
        return key.replace('.', '_').toUpperCase(Locale.ROOT);
    }
    
    @Override public int getPropertyAsInt(String key, int defaultValue) {
        String envValue = getProperty(convertKey(key));
        
        if (envValue == null) {
            LOGGER.error("Cannot retrieve the property {}, using default Value {}", convertKey(key), defaultValue);
            return defaultValue;
        }
        return Integer.parseInt(envValue);
    }
    
    @Override public String getPropertyOrFail(String key) {
        String property = getProperty(key);
        if (property == null) throw new IllegalArgumentException("ENV Variable " + convertKey(key) + " is missing");
        return property;
    }
    
    @Override public String getPassword(String key) {
        return getPropertyOrFail(key);
    }
}

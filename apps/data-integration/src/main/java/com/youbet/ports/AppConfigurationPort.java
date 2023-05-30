package com.youbet.ports;

public interface AppConfigurationPort {

    String getProperty(String key);

    int getPropertyAsInt(String key, int defaultValue);
}

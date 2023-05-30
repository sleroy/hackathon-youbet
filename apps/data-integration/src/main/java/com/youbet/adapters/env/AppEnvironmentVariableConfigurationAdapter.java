package com.youbet.adapters.env;

import com.youbet.ports.AppConfigurationPort;

public class AppEnvironmentVariableConfigurationAdapter implements AppConfigurationPort {
    @Override public String getProperty(String key) {
        return null;
    }
    
    @Override public int getPropertyAsInt(String key, int defaultValue) {
        return 0;
    }
}

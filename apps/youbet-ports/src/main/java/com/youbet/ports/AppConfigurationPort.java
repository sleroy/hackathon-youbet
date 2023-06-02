package com.youbet.ports;

public interface AppConfigurationPort {
    
    /**
     * Returns the property value or null.
     *
     * @param key the property key
     * @return the value associated to the property key.
     */
    String getProperty(String key);
    
    /**
     * Returns the property as an integer
     *
     * @param key          the property key
     * @param defaultValue the property value
     * @return the property value converted as an Integer or the default value if the property is not defined.
     */
    int getPropertyAsInt(String key, int defaultValue);
    
    /**
     * Returns the property or fail if the property is not defined
     *
     * @param key the property key
     * @return the property value or an exception if missing.
     */
    String getPropertyOrFail(String key);
    
    /**
     * Collect a secured identifier.
     * @param key the property key
     * @return the deciphered password
     */
    String getPassword(String key);
}

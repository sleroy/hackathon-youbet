package com.youbet.dataintegration.agents.utils;

public class StringUtils {
    /**
     * Tests if the string parameter is blank.
     * @param str the parameter to test
     * @return true if the string is blank
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}

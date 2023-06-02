package com.youbet.utils;

import java.io.IOException;

public class YoubetException extends RuntimeException {
    public YoubetException(Exception e) {
        super(e.getMessage(), e);
    }
    
    public YoubetException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public YoubetException(String message) {
        super(message);
    }
}

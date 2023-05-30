package com.youbet.utils;

import java.io.IOException;

public class YoubetException extends RuntimeException {
    public YoubetException(Exception e) {
        super(e.getMessage(), e);
    }
}

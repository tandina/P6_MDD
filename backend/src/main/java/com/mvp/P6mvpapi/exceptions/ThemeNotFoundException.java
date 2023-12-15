package com.mvp.P6mvpapi.exceptions;

public class ThemeNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ThemeNotFoundException(String message) {
        super(message);
    }
}
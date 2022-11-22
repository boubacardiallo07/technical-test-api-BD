package com.technicaltest.api.exception;

public class UserNotFoundException  extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

package com.kabarxx.store_example.application.exceptions.authentication;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}

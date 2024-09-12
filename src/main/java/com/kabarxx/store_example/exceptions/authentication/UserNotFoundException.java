package com.kabarxx.store_example.exceptions.authentication;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}

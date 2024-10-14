package com.kabarxx.store_example.application.exceptions.authentication;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

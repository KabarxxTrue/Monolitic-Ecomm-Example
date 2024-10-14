package com.kabarxx.store_example.application.exceptions.authentication;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password");
    }
}

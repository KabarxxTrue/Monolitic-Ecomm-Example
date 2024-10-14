package com.kabarxx.store_example.application.exceptions.authentication;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("Login failed");
    }
}

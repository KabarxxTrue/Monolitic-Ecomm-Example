package com.kabarxx.store_example.exceptions.authentication;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("Login failed");
    }
}

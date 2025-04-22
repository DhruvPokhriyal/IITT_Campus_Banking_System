package com.iit.banking.exceptions;

public class AuthenticationException extends BaseException {
    public AuthenticationException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException("AUTH_001", "Invalid credentials");
    }

    public static AuthenticationException userNotFound(String email) {
        return new AuthenticationException("AUTH_002", "User not found with email: " + email);
    }
}
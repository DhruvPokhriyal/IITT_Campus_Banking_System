package com.iit.banking.exceptions;

public class UserException extends BaseException {
    public UserException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public static UserException userNotFound(Long id) {
        return new UserException("USER_001", "User not found with ID: " + id);
    }

    public static UserException userNotFound(String email) {
        return new UserException("USER_002", "User not found with email: " + email);
    }

    public static UserException emailExists(String email) {
        return new UserException("USER_003", "Account already exists with this email: " + email);
    }

    public static UserException passwordsDoNotMatch() {
        return new UserException("USER_004", "Passwords do not match");
    }

    public static UserException incorrectPassword() {
        return new UserException("USER_005", "Old password is incorrect");
    }
}
package com.iit.banking.exceptions;

public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    protected BaseException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
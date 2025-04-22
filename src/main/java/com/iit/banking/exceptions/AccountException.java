package com.iit.banking.exceptions;

public class AccountException extends BaseException {
    public AccountException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public static AccountException accountNotFound(Long accountNumber) {
        return new AccountException("ACC_001", "Account not found with number: " + accountNumber);
    }

    public static AccountException insufficientBalance() {
        return new AccountException("ACC_002", "Insufficient balance");
    }

    public static AccountException accountNumberExists(Long accountNumber) {
        return new AccountException("ACC_003", "Account number already exists: " + accountNumber);
    }
}
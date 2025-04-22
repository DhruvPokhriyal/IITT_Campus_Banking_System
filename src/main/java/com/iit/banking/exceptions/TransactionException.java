package com.iit.banking.exceptions;

public class TransactionException extends BaseException {
    public TransactionException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public static TransactionException transactionNotFound(Long id) {
        return new TransactionException("TXN_001", "Transaction not found with ID: " + id);
    }

    public static TransactionException invalidTransactionType(String type) {
        return new TransactionException("TXN_002", "Invalid transaction type: " + type);
    }

    public static TransactionException invalidAmount() {
        return new TransactionException("TXN_003", "Invalid transaction amount");
    }
}
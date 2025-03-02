package com.iit.banking.dto;

import java.time.LocalDateTime;

import com.iit.banking.model.entity.Transaction;

public class TransactionDTO {
    private Long id;
    private String transactionType;
    private Double amount;
    private String description;
    private LocalDateTime timestamp;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.timestamp = transaction.getTimestamp();
    }

    public Long getId() {
        return id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}

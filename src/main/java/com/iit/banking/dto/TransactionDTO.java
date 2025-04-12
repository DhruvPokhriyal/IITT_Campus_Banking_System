package com.iit.banking.dto;

import java.time.LocalDateTime;

import com.iit.banking.model.entity.Transaction;

public class TransactionDTO {
    private Long id;
    private String transactionType;
    private Double amount;
    private String description;
    private LocalDateTime timestamp;
    private SenderDTO sender;
    private ReceiverDTO receiver;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.timestamp = transaction.getTimestamp();
        this.sender = transaction.getSender() != null ? new SenderDTO(transaction.getSender()) : null;
        this.receiver = transaction.getReceiver() != null ? new ReceiverDTO(transaction.getReceiver()) : null;
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

    public SenderDTO getSender() {
        return sender;
    }

    public ReceiverDTO getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }

    public static class SenderDTO {
        private Long id;
        private Long accountNumber;

        public SenderDTO(com.iit.banking.model.entity.Account account) {
            this.id = account.getId();
            this.accountNumber = account.getAccountNumber();
        }

        public Long getId() {
            return id;
        }

        public Long getAccountNumber() {
            return accountNumber;
        }

        @Override
        public String toString() {
            return "SenderDTO{" +
                    "id=" + id +
                    ", accountNumber=" + accountNumber +
                    '}';
        }
    }

    public static class ReceiverDTO {
        private Long id;
        private Long accountNumber;

        public ReceiverDTO(com.iit.banking.model.entity.Account account) {
            this.id = account.getId();
            this.accountNumber = account.getAccountNumber();
        }

        public Long getId() {
            return id;
        }

        public Long getAccountNumber() {
            return accountNumber;
        }

        @Override
        public String toString() {
            return "ReceiverDTO{" +
                    "id=" + id +
                    ", accountNumber=" + accountNumber +
                    '}';
        }
    }
}

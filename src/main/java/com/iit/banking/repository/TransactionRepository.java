package com.iit.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySender_IdOrReceiver_Id(Long senderId, Long receiverId);

    List<Transaction> findBySender_AccountNumberOrReceiver_AccountNumber(Long senderAccountNumber,
            Long receiverAccountNumber);
}

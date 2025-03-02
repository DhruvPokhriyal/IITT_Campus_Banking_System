package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}

package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    <Optional> Account findByAccountNumber(String accountNumber);

}

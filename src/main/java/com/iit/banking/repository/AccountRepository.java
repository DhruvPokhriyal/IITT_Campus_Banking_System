package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.iit.banking.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Long accountNumber);

    Optional<Account> getAccountByAccountNumber(Long accountNumber);

}

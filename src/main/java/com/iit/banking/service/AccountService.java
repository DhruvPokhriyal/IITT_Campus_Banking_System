package com.iit.banking.service;

import org.springframework.stereotype.Service;

import com.iit.banking.dto.AccountDTO;
import com.iit.banking.exceptions.AccountException;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.model.entity.Account;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountByAccountNumber(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> AccountException.accountNotFound(accountNumber));
        return new AccountDTO(account);
    }

    public double getBalanceByAccountNumber(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> AccountException.accountNotFound(accountNumber));
        return account.getBalance();
    }
}

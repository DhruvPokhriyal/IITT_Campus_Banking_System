package com.iit.banking.service;

import org.springframework.stereotype.Service;

import com.iit.banking.dto.AccountDTO;
import com.iit.banking.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        AccountDTO account = new AccountDTO(accountRepository.findByAccountNumber(accountNumber));
        return account;
    }

    public double getBalanceByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).getBalance();
    }

}

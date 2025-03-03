package com.iit.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionDTO> getAllTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySenderOrReceiverId(accountId, accountId).stream().map(TransactionDTO::new)
                .toList();
    }
}

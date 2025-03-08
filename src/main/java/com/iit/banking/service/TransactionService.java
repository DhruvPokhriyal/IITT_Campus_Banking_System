package com.iit.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.repository.TransactionRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public TransactionDTO deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        Transaction transaction = new Transaction("Deposit", amount, null, null, account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO withdraw(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (account.getBalance() < amount) {
            throw new Error("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        Transaction transaction = new Transaction("Withdraw", amount, null, null, account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO transfer(Long senderId, Long receiverId, Double amount) {
        Account sender = accountRepository.findById(senderId).orElseThrow();
        Account receiver = accountRepository.findById(receiverId).orElseThrow();
        if (sender.getBalance() < amount) {
            throw new Error("Insufficient balance");
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.save(sender);
        accountRepository.save(receiver);
        Transaction transaction = new Transaction("Transfer", amount, null, sender, receiver);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

}

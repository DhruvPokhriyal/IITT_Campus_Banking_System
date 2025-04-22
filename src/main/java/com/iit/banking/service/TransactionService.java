package com.iit.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.exceptions.AccountException;
import com.iit.banking.exceptions.TransactionException;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.Transaction;
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
        return transactionRepository.findBySender_AccountNumberOrReceiver_AccountNumber(accountId, accountId).stream()
                .map(TransactionDTO::new)
                .toList();
    }

    @Transactional
    public TransactionDTO deposit(Long accountId, Double amount) {
        if (amount <= 0) {
            throw TransactionException.invalidAmount();
        }

        Account account = accountRepository.findByAccountNumber(accountId)
                .orElseThrow(() -> AccountException.accountNotFound(accountId));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        Transaction transaction = new Transaction("Deposit", amount, "Deposit to account", null, account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO withdraw(Long accountId, Double amount) {
        if (amount <= 0) {
            throw TransactionException.invalidAmount();
        }

        Account account = accountRepository.findByAccountNumber(accountId)
                .orElseThrow(() -> AccountException.accountNotFound(accountId));
        if (account.getBalance() < amount) {
            throw AccountException.insufficientBalance();
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        Transaction transaction = new Transaction("Withdrawal", amount, "Withdrawal from account", account, null);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO transfer(Long senderId, Long receiverId, Double amount) {
        if (amount <= 0) {
            throw TransactionException.invalidAmount();
        }

        Account sender = accountRepository.findByAccountNumber(senderId)
                .orElseThrow(() -> AccountException.accountNotFound(senderId));
        Account receiver = accountRepository.findByAccountNumber(receiverId)
                .orElseThrow(() -> AccountException.accountNotFound(receiverId));

        if (sender.getBalance() < amount) {
            throw AccountException.insufficientBalance();
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.save(sender);
        accountRepository.save(receiver);
        Transaction transaction = new Transaction("Transfer", amount, "Transfer between accounts", sender, receiver);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(savedTransaction);
    }

}

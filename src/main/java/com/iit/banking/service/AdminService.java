package com.iit.banking.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.AdminDTO;
import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.repository.AdminRepository;
import com.iit.banking.repository.TransactionRepository;
import com.iit.banking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AdminService(AdminRepository adminRepository, UserRepository userRepository,
            TransactionRepository transactionRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<AdminDTO> getAllAdmins() {
        List<AdminDTO> adminDTOs = adminRepository.findAll().stream().map(AdminDTO::new).toList();
        return adminDTOs;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAll().stream().map(UserDTO::new).toList();
        return userDTOs;
    }

    @Transactional
    public void deleteUser(UserDTO user) {
        userRepository.deleteByEmail(user.getEmail());
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public TransactionDTO reverseTransaction(Transaction transaction) {
        if (transaction.getTransactionType().equals("Deposit")) {
            Account userAccount = transaction.getSender();
            userAccount.setBalance(userAccount.getBalance() + transaction.getAmount());
        } else if (transaction.getTransactionType().equals("Withdraw")) {
            Account userAccount = transaction.getSender();
            userAccount.setBalance(userAccount.getBalance() - transaction.getAmount());
        } else if (transaction.getTransactionType().equals("Transfer")) {
            throw new Error("Cannot reverse transferred amount");
        }

        else {
            throw new IllegalArgumentException("Invalid transaction type: " + transaction.getTransactionType());
        }
        transactionRepository.delete(transaction);
        return new TransactionDTO(transaction);
    }
}

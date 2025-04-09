package com.iit.banking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.iit.banking.dto.AdminDTO;
import com.iit.banking.dto.AdminRequestDTO;
import com.iit.banking.dto.AdminUpdateDTO;
import com.iit.banking.dto.PasswordUpdateDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.model.entity.Admin;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AdminRepository;
import com.iit.banking.repository.UserRepository;
import com.iit.banking.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AdminDTO addAdmin(AdminRequestDTO adminRequest) {
        try {
            logger.debug("Attempting to create admin with email: {}", adminRequest.getEmail());

            // Validate passwords match
            if (!adminRequest.getPassword().equals(adminRequest.getConfirmPassword())) {
                logger.warn("Password confirmation failed for email: {}", adminRequest.getEmail());
                throw new IllegalArgumentException("Passwords do not match");
            }

            // Check if admin already exists
            if (adminRepository.findByEmail(adminRequest.getEmail()).isPresent()) {
                logger.warn("Admin already exists with email: {}", adminRequest.getEmail());
                throw new IllegalArgumentException("Account already exists with this email");
            }

            // Create and save admin
            Admin admin = new Admin();
            admin.setName(adminRequest.getName());
            admin.setEmail(adminRequest.getEmail());
            admin.setPassword(adminRequest.getPassword());
            Admin savedAdmin = adminRepository.save(admin);
            logger.debug("Admin created successfully with email: {}", adminRequest.getEmail());
            return new AdminDTO(savedAdmin);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error during admin creation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during admin creation: ", e);
            throw new RuntimeException("An error occurred during admin creation. Please try again.", e);
        }
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminDTO::new)
                .collect(Collectors.toList());
    }

    public AdminDTO getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(AdminDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + id));
    }

    public AdminDTO getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(AdminDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with email: " + email));
    }

    @Transactional
    public void updateName(AdminUpdateDTO adminUpdate) {
        Admin admin = adminRepository.findByEmail(adminUpdate.getEmail())
                .orElseThrow(
                        () -> new IllegalArgumentException("Admin not found with email: " + adminUpdate.getEmail()));
        admin.setName(adminUpdate.getName());
        admin.setEmail(adminUpdate.getEmail());
        adminRepository.save(admin);
    }

    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdate) {
        Admin admin = adminRepository.findByEmail(passwordUpdate.getEmail())
                .orElseThrow(
                        () -> new IllegalArgumentException("Admin not found with email: " + passwordUpdate.getEmail()));

        if (!passwordUpdate.getOldPassword().equals(admin.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        admin.setPassword(passwordUpdate.getNewPassword());
        adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(AdminDTO admin) {
        if (!adminRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Admin not found with email: " + admin.getEmail());
        }
        adminRepository.deleteByEmail(admin.getEmail());
    }

    @Transactional
    public AdminDTO updateAdmin(String email, AdminUpdateDTO adminUpdate) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with email: " + email));
        admin.setName(adminUpdate.getName());
        admin.setEmail(adminUpdate.getEmail());
        Admin updatedAdmin = adminRepository.save(admin);
        return new AdminDTO(updatedAdmin);
    }

    @Transactional
    public void deleteAdmin(String email) {
        if (!adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Admin not found with email: " + email);
        }
        adminRepository.deleteByEmail(email);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userDTO.getId()));
        userRepository.delete(user);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction reverseTransaction(Transaction transaction) {
        // Create a new transaction with reversed amounts
        Transaction reversedTransaction = new Transaction();
        reversedTransaction.setAmount(transaction.getAmount());
        reversedTransaction
                .setTransactionType(transaction.getTransactionType().equals("Deposit") ? "Withdrawal" : "Deposit");
        reversedTransaction.setDescription("Reversed: " + transaction.getDescription());
        reversedTransaction.setSender(transaction.getReceiver());
        reversedTransaction.setReceiver(transaction.getSender());

        return transactionRepository.save(reversedTransaction);
    }
}

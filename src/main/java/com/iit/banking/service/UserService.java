package com.iit.banking.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.PasswordUpdateDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.dto.UserRequestDTO;
import com.iit.banking.dto.UserUpdateDTO;
import com.iit.banking.exceptions.AccountException;
import com.iit.banking.exceptions.UserException;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public UserDTO addUser(UserRequestDTO userRequest) {
        try {
            logger.debug("Attempting to create user with email: {}", userRequest.getEmail());

            // Validate passwords match
            if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
                logger.warn("Password confirmation failed for email: {}", userRequest.getEmail());
                throw UserException.passwordsDoNotMatch();
            }

            // Check if user already exists
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                logger.warn("User already exists with email: {}", userRequest.getEmail());
                throw UserException.emailExists(userRequest.getEmail());
            }

            // Check if account number already exists
            if (accountRepository.findByAccountNumber(userRequest.getAccountNumber()).isPresent()) {
                logger.warn("Account number already exists: {}", userRequest.getAccountNumber());
                throw new AccountException("ACC_003",
                        "Account number already exists: " + userRequest.getAccountNumber());
            }

            // Create and save user
            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            User savedUser = userRepository.save(user);
            logger.debug("User created successfully with email: {}", userRequest.getEmail());

            // Create and save account
            Account account = new Account();
            account.setAccountNumber(userRequest.getAccountNumber());
            account.setBalance(userRequest.getBalance() != null ? userRequest.getBalance() : 0.0);
            account.setUser(savedUser);
            accountRepository.save(account);
            logger.debug("Account created successfully for user: {}", userRequest.getEmail());

            return new UserDTO(savedUser);
        } catch (UserException e) {
            logger.error("Validation error during user creation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during user creation: ", e);
            throw new RuntimeException("An error occurred during user creation. Please try again.", e);
        }
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> UserException.userNotFound(id));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new)
                .orElseThrow(() -> UserException.userNotFound(email));
    }

    @Transactional
    public void updateName(UserUpdateDTO userUpdate) {
        User user = userRepository.findByEmail(userUpdate.getEmail())
                .orElseThrow(() -> UserException.userNotFound(userUpdate.getEmail()));
        user.setName(userUpdate.getName());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdate) {
        User user = userRepository.findByEmail(passwordUpdate.getEmail())
                .orElseThrow(() -> UserException.userNotFound(passwordUpdate.getEmail()));

        if (!passwordUpdate.getOldPassword().equals(user.getPassword())) {
            throw UserException.incorrectPassword();
        }

        user.setPassword(passwordUpdate.getNewPassword());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDTO user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw UserException.userNotFound(user.getEmail());
        }
        userRepository.deleteByEmail(user.getEmail());
    }
}

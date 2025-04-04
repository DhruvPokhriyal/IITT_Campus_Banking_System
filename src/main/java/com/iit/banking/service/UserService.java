package com.iit.banking.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.PasswordUpdateDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.dto.UserRequestDTO;
import com.iit.banking.dto.UserUpdateDTO;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO addUser(UserRequestDTO userRequest) {
        try {
            logger.debug("Attempting to create user with email: {}", userRequest.getEmail());

            // Validate passwords match
            if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
                logger.warn("Password confirmation failed for email: {}", userRequest.getEmail());
                throw new IllegalArgumentException("Passwords do not match");
            }

            // Check if user already exists
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                logger.warn("User already exists with email: {}", userRequest.getEmail());
                throw new IllegalArgumentException("Account already exists with this email");
            }

            // Create and save user
            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
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
        } catch (IllegalArgumentException e) {
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
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Transactional
    public void updateName(UserUpdateDTO userUpdate) {
        User user = userRepository.findByEmail(userUpdate.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userUpdate.getEmail()));
        user.setName(userUpdate.getName());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdate) {
        User user = userRepository.findByEmail(passwordUpdate.getEmail())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with email: " + passwordUpdate.getEmail()));

        if (!passwordEncoder.matches(passwordUpdate.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDTO user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User not found with email: " + user.getEmail());
        }
        userRepository.deleteByEmail(user.getEmail());
    }
}

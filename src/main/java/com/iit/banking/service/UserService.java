package com.iit.banking.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.UserDTO;
import com.iit.banking.dto.UserRequestDTO;
import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AccountRepository;
import com.iit.banking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
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
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));
        if (optionalUser.isPresent()) {
            throw new Error("Account already exist from this email");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(user);
        Account account = new Account();
        account.setAccountNumber(userRequest.getAccountNumber());
        account.setBalance(userRequest.getBalance());
        account.setUser(savedUser);
        accountRepository.save(account);
        return new UserDTO(savedUser);
    }
}

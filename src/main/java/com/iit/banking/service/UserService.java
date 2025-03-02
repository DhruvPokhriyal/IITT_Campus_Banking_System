package com.iit.banking.service;

import java.util.List;
import java.util.Optional;

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

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAll().stream().map(UserDTO::new).toList();
        return userDTOs;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return new UserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return new UserDTO(user);
    }

    @Transactional
    public void updateName(UserUpdateDTO userUpdate) {
        User user = userRepository.findByEmail(userUpdate.getEmail());
        user.setName(userUpdate.getName());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdate) {
        User user = userRepository.findByEmail(passwordUpdate.getEmail());
        if (!passwordEncoder.matches(passwordUpdate.getOldPassword(), user.getPassword())) {
            throw new Error("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDTO user) {
        userRepository.deleteByEmail(user.getEmail());
    }
}

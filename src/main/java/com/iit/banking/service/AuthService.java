package com.iit.banking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.LoginRequestDTO;
import com.iit.banking.dto.LoginResponseDTO;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Instead of generating a JWT, just return a simple success message
        // You could also create a session ID or some other identifier
        return new LoginResponseDTO("LOGIN_SUCCESS");
    }

    public LoginResponseDTO adminLogin(LoginRequestDTO loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Check if user is admin (assuming there's a role field in your User entity)
        // if (!"ADMIN".equals(user.getRole())) {
        // throw new RuntimeException("Not authorized as admin");
        // }

        // Simple success message for admin login
        return new LoginResponseDTO("ADMIN_LOGIN_SUCCESS");
    }

    public boolean validateToken(String token) {
        // Since we're not using JWT, this can be simplified
        // This could validate a session if you implement sessions
        return "LOGIN_SUCCESS".equals(token) || "ADMIN_LOGIN_SUCCESS".equals(token);
    }
}
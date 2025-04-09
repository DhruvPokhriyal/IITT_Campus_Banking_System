package com.iit.banking.service;

import org.springframework.stereotype.Service;

import com.iit.banking.dto.LoginRequestDTO;
import com.iit.banking.dto.LoginResponseDTO;
import com.iit.banking.model.entity.Admin;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AdminRepository;
import com.iit.banking.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public AuthService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password (plain text comparison)
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Instead of generating a JWT, just return a simple success message
        return new LoginResponseDTO("LOGIN_SUCCESS");
    }

    public LoginResponseDTO adminLogin(LoginRequestDTO loginRequest) {
        // Find user by email
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password (plain text comparison)
        if (!loginRequest.getPassword().equals(admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Simple success message for admin login
        return new LoginResponseDTO("ADMIN_LOGIN_SUCCESS");
    }

    public boolean validateToken(String token) {
        // Since we're not using JWT, this can be simplified
        return "LOGIN_SUCCESS".equals(token) || "ADMIN_LOGIN_SUCCESS".equals(token);
    }
}
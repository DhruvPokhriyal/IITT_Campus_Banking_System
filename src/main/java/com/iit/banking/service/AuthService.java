package com.iit.banking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.LoginRequestDTO;
import com.iit.banking.dto.LoginResponseDTO;
import com.iit.banking.model.entity.Admin;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AdminRepository;
import com.iit.banking.repository.UserRepository;
import com.iit.banking.util.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, AdminRepository adminRepository,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Error("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

    public LoginResponseDTO adminLogin(LoginRequestDTO loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin == null || !passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new Error("Invalid admin credentials");
        }
        String token = jwtUtil.generateToken(admin.getEmail());
        return new LoginResponseDTO(token);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String extractEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }
}

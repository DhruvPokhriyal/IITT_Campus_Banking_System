package com.iit.banking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.banking.dto.LoginRequestDTO;
import com.iit.banking.dto.LoginResponseDTO;
import com.iit.banking.model.entity.Admin;
import com.iit.banking.model.entity.User;
import com.iit.banking.repository.AdminRepository;
import com.iit.banking.repository.UserRepository;
import com.iit.banking.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            logger.debug("Attempting user login for email: {}", loginRequest.getEmail());

            // First try to find the user
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            // Create authentication token
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    user.getAuthorities());

            String token = jwtUtil.generateToken(authentication);
            logger.debug("Login successful for user: {}", loginRequest.getEmail());
            return new LoginResponseDTO(token);
        } catch (BadCredentialsException e) {
            logger.warn("Login failed for user {}: {}", loginRequest.getEmail(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during user login: ", e);
            throw new RuntimeException("An error occurred during login. Please try again.");
        }
    }

    public LoginResponseDTO adminLogin(LoginRequestDTO loginRequest) {
        try {
            logger.debug("Attempting admin login for email: {}", loginRequest.getEmail());

            // First try to find the admin
            Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Admin not found"));

            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            // Create authentication token
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.getAuthorities());

            String token = jwtUtil.generateToken(authentication);
            logger.debug("Login successful for admin: {}", loginRequest.getEmail());
            return new LoginResponseDTO(token);
        } catch (BadCredentialsException e) {
            logger.warn("Login failed for admin {}: {}", loginRequest.getEmail(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during admin login: ", e);
            throw new RuntimeException("An error occurred during login. Please try again.");
        }
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getEmailFromToken(String token) {
        return jwtUtil.getEmailFromToken(token);
    }
}

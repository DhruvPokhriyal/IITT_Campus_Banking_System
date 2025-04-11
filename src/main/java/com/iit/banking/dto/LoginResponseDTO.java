package com.iit.banking.dto;

public class LoginResponseDTO {
    private String status;
    private String message;
    private UserDTO user;

    public LoginResponseDTO(String status, UserDTO user) {
        this.status = status;
        this.message = "Login successful";
        this.user = user;
    }

    // For backward compatibility
    public LoginResponseDTO(String status) {
        this.status = status;
        this.message = "Login successful";
    }

    public String getToken() {
        return status;
    }

    public void setToken(String token) {
        this.status = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
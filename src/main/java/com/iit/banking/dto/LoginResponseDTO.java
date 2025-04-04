package com.iit.banking.dto;

public class LoginResponseDTO {
    private String status;
    private String message;

    public LoginResponseDTO(String status) {
        this.status = status;
        this.message = "Login successful";
    }

    // For backward compatibility with the original code
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
}
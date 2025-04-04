package com.iit.banking.dto;

import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.User;

import java.math.BigDecimal;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String accountNumber;
    private BigDecimal balance;

    // No-arg constructor for frameworks like Jackson/Spring
    public UserDTO() {
    }

    // Constructor that converts User entity to DTO
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        Account account = user.getAccount();
        this.accountNumber = account != null ? account.getAccountNumber() : null;
        this.balance = account != null && account.getBalance() != null ? BigDecimal.valueOf(account.getBalance())
                : BigDecimal.ZERO;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

}

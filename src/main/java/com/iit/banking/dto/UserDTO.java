package com.iit.banking.dto;

import com.iit.banking.model.entity.Account;
import com.iit.banking.model.entity.User;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String accountNumber;
    private Double balance;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        Account account = user.getAccount();
        if (account != null) {
            this.accountNumber = account.getAccountNumber();
            this.balance = account.getBalance();
        } else {
            this.accountNumber = null;
            this.balance = 0.0;
        }
    }

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

    public Double getBalance() {
        return balance;
    }

}

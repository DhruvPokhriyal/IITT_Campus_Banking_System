package com.iit.banking.dto;

public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String accountNumber;
    private Double balance;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String email, String password, String confirmPassword, String accountNumber,
            Double balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}

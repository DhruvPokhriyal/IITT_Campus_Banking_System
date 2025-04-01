package com.iit.banking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.AdminDTO;
import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDTO user) {
        adminService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = adminService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions/reverse")
    public ResponseEntity<TransactionDTO> reverseTransaction(@RequestBody Transaction transaction) {
        TransactionDTO reversedTransaction = adminService.reverseTransaction(transaction);
        return ResponseEntity.ok(reversedTransaction);
    }
}
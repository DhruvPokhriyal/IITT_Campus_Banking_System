package com.iit.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.AdminDTO;
import com.iit.banking.dto.AdminRequestDTO;
import com.iit.banking.dto.AdminUpdateDTO;
import com.iit.banking.dto.PasswordUpdateDTO;
import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Management", description = "APIs for managing admin accounts and operations")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    @Operation(summary = "Register a new admin", description = "Creates a new admin account with the provided details")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminRequestDTO adminRequest) {
        AdminDTO admin = adminService.addAdmin(adminRequest);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all admins", description = "Retrieves a list of all admin accounts")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get admin by ID", description = "Retrieves admin details by their ID")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        AdminDTO admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get admin by email", description = "Retrieves admin details by their email")
    public ResponseEntity<AdminDTO> getAdminByEmail(@PathVariable String email) {
        AdminDTO admin = adminService.getAdminByEmail(email);
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/update-name")
    @Operation(summary = "Update admin name", description = "Updates the name of an existing admin")
    public ResponseEntity<Void> updateName(@RequestBody AdminUpdateDTO adminUpdate) {
        adminService.updateName(adminUpdate);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password")
    @Operation(summary = "Update admin password", description = "Updates the password of an existing admin")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateDTO passwordUpdate) {
        adminService.updatePassword(passwordUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete admin", description = "Deletes an admin account")
    public ResponseEntity<Void> deleteAdmin(@RequestBody AdminDTO admin) {
        adminService.deleteAdmin(admin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Retrieves a list of all user accounts")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/delete")
    @Operation(summary = "Delete user", description = "Deletes a user account")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDTO userDTO) {
        adminService.deleteUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get all transactions", description = "Retrieves a list of all transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = adminService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions/reverse")
    @Operation(summary = "Reverse transaction", description = "Reverses a specific transaction")
    public ResponseEntity<Transaction> reverseTransaction(@RequestBody Transaction transaction) {
        Transaction reversedTransaction = adminService.reverseTransaction(transaction);
        return ResponseEntity.ok(reversedTransaction);
    }
}
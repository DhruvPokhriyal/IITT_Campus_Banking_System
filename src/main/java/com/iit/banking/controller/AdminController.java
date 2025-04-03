package com.iit.banking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.AdminDTO;
import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.model.entity.Transaction;
import com.iit.banking.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all admins", description = "Retrieve a list of all admin users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved admins"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete user", description = "Delete a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User to delete", required = true) @RequestBody UserDTO user) {
        adminService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = adminService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Reverse transaction", description = "Reverse a specific transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction reversed successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot reverse transfer transaction"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @PostMapping("/transactions/reverse")
    public ResponseEntity<TransactionDTO> reverseTransaction(
            @Parameter(description = "Transaction to reverse", required = true) @RequestBody Transaction transaction) {
        TransactionDTO reversedTransaction = adminService.reverseTransaction(transaction);
        return ResponseEntity.ok(reversedTransaction);
    }
}
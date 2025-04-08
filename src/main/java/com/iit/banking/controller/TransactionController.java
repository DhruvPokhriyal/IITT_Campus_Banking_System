package com.iit.banking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Transaction management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {
        private final TransactionService transactionService;

        public TransactionController(TransactionService transactionService) {
                this.transactionService = transactionService;
        }

        @Operation(summary = "Get account transactions", description = "Retrieve all transactions for a specific account")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
                        @ApiResponse(responseCode = "404", description = "Account not found")
        })
        @GetMapping("/account/{accountId}")
        public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(
                        @Parameter(description = "Account ID", required = true) @PathVariable Long accountId) {
                List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountId(accountId);
                return ResponseEntity.ok(transactions);
        }

        @Operation(summary = "Deposit money", description = "Make a deposit to an account")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Deposit successful"),
                        @ApiResponse(responseCode = "404", description = "Account not found")
        })
        @PostMapping("/deposit/{accountId}")
        public ResponseEntity<TransactionDTO> deposit(
                        @Parameter(description = "Account ID", required = true) @PathVariable Long accountId,
                        @Parameter(description = "Amount to deposit", required = true) @RequestParam Double amount) {
                TransactionDTO transaction = transactionService.deposit(accountId, amount);
                return ResponseEntity.ok(transaction);
        }

        @Operation(summary = "Withdraw money", description = "Make a withdrawal from an account")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
                        @ApiResponse(responseCode = "400", description = "Insufficient balance"),
                        @ApiResponse(responseCode = "404", description = "Account not found")
        })
        @PostMapping("/withdraw/{accountId}")
        public ResponseEntity<TransactionDTO> withdraw(
                        @Parameter(description = "Account ID", required = true) @PathVariable Long accountId,
                        @Parameter(description = "Amount to withdraw", required = true) @RequestParam Double amount) {
                TransactionDTO transaction = transactionService.withdraw(accountId, amount);
                return ResponseEntity.ok(transaction);
        }

        @Operation(summary = "Transfer money", description = "Transfer money between accounts")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transfer successful"),
                        @ApiResponse(responseCode = "400", description = "Insufficient balance"),
                        @ApiResponse(responseCode = "404", description = "Account not found")
        })
        @PostMapping("/transfer")
        public ResponseEntity<TransactionDTO> transfer(
                        @Parameter(description = "Sender's account ID", required = true) @RequestParam Long senderId,
                        @Parameter(description = "Receiver's account ID", required = true) @RequestParam Long receiverId,
                        @Parameter(description = "Amount to transfer", required = true) @RequestParam Double amount) {
                TransactionDTO transaction = transactionService.transfer(senderId, receiverId, amount);
                return ResponseEntity.ok(transaction);
        }
}
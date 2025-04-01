package com.iit.banking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<TransactionDTO> deposit(
            @PathVariable Long accountId,
            @RequestParam Double amount) {
        TransactionDTO transaction = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<TransactionDTO> withdraw(
            @PathVariable Long accountId,
            @RequestParam Double amount) {
        TransactionDTO transaction = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam Double amount) {
        TransactionDTO transaction = transactionService.transfer(senderId, receiverId, amount);
        return ResponseEntity.ok(transaction);
    }
}
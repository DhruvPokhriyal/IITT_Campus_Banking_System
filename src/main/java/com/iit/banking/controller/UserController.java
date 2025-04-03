package com.iit.banking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iit.banking.dto.PasswordUpdateDTO;
import com.iit.banking.dto.UserDTO;
import com.iit.banking.dto.UserRequestDTO;
import com.iit.banking.dto.UserUpdateDTO;
import com.iit.banking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create user", description = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Parameter(description = "User creation request", required = true) @RequestBody UserRequestDTO userRequest) {
        UserDTO user = userService.addUser(userRequest);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by email", description = "Retrieve a user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @Parameter(description = "User email", required = true) @PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user name", description = "Update a user's name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/name")
    public ResponseEntity<Void> updateName(
            @Parameter(description = "User update request", required = true) @RequestBody UserUpdateDTO userUpdate) {
        userService.updateName(userUpdate);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update password", description = "Update a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid old password"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "Password update request", required = true) @RequestBody PasswordUpdateDTO passwordUpdate) {
        userService.updatePassword(passwordUpdate);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete user", description = "Delete a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User to delete", required = true) @RequestBody UserDTO user) {
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
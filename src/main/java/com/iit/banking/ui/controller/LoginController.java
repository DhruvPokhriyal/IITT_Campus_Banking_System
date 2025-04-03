package com.iit.banking.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.iit.banking.dto.LoginRequestDTO;
import com.iit.banking.dto.LoginResponseDTO;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheck;

    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both email and password.");
            return;
        }

        try {
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            loginRequest.setEmail(email);
            loginRequest.setPassword(password);

            ResponseEntity<LoginResponseDTO> response = restTemplate.postForEntity(
                    "http://localhost:8080/api/auth/login",
                    loginRequest,
                    LoginResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String token = response.getBody().getToken();
                // Store token for future requests
                // TODO: Implement token storage

                // Navigate to main dashboard
                navigateToDashboard();
            } else {
                showAlert("Error", "Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during login. Please try again.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Sign Up - IIT Campus Banking System");
        } catch (Exception e) {
            showAlert("Error", "Failed to load signup screen.");
            e.printStackTrace();
        }
    }

    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Dashboard - IIT Campus Banking System");
        } catch (Exception e) {
            showAlert("Error", "Failed to load dashboard.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
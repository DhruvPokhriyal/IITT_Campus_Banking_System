package com.iit.banking.ui.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.iit.banking.dto.TransactionDTO;

public class DepositDialogController {

    @FXML
    private TextField amountField;
    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    private void handleDeposit() {
        try {
            String amountStr = amountField.getText().trim();
            if (amountStr.isEmpty()) {
                showAlert("Error", "Please enter an amount.");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showAlert("Error", "Amount must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid number.");
                return;
            }

            // Make API call to deposit
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getStoredToken()); // Implement token storage/retrieval

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<TransactionDTO> response = restTemplate.exchange(
                    "http://localhost:8080/api/transactions/deposit/{accountId}?amount={amount}",
                    HttpMethod.POST,
                    entity,
                    TransactionDTO.class,
                    getCurrentAccountId(), // Implement account ID retrieval
                    amount);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                showAlert("Success", "Deposit successful!");
                closeDialog();
            } else {
                showAlert("Error", "Failed to process deposit.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the deposit.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // TODO: Implement these methods for token and account management
    private String getStoredToken() {
        return "";
    }

    private Long getCurrentAccountId() {
        return 1L;
    }
}
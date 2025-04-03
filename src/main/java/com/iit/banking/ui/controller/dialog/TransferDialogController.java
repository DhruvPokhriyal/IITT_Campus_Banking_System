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
import com.iit.banking.dto.AccountDTO;

public class TransferDialogController {

    @FXML
    private TextField recipientAccountField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    private void handleTransfer() {
        try {
            String recipientAccountStr = recipientAccountField.getText().trim();
            String amountStr = amountField.getText().trim();
            String description = descriptionField.getText().trim();

            if (recipientAccountStr.isEmpty()) {
                showAlert("Error", "Please enter recipient's account number.");
                return;
            }

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
                showAlert("Error", "Please enter a valid number for amount.");
                return;
            }

            // Get recipient account details
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getStoredToken()); // Implement token storage/retrieval

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<AccountDTO> recipientResponse = restTemplate.exchange(
                    "http://localhost:8080/api/accounts/{accountNumber}",
                    HttpMethod.GET,
                    entity,
                    AccountDTO.class,
                    recipientAccountStr);

            if (!recipientResponse.getStatusCode().is2xxSuccessful() || recipientResponse.getBody() == null) {
                showAlert("Error", "Recipient account not found.");
                return;
            }

            // Make API call to transfer
            ResponseEntity<TransactionDTO> response = restTemplate.exchange(
                    "http://localhost:8080/api/transactions/transfer?senderId={senderId}&receiverId={receiverId}&amount={amount}",
                    HttpMethod.POST,
                    entity,
                    TransactionDTO.class,
                    getCurrentAccountId(), // Implement account ID retrieval
                    recipientResponse.getBody().getId(),
                    amount);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                showAlert("Success", "Transfer successful!");
                closeDialog();
            } else {
                showAlert("Error", "Failed to process transfer. Please check your balance.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the transfer.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) recipientAccountField.getScene().getWindow();
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
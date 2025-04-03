package com.iit.banking.ui.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.iit.banking.dto.TransactionDTO;
import java.time.LocalDate;

public class PayBillsDialogController {

    @FXML
    private ComboBox<String> billTypeComboBox;
    @FXML
    private TextField billNumberField;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker dueDatePicker;
    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    private void handlePayBill() {
        try {
            String billType = billTypeComboBox.getValue();
            String billNumber = billNumberField.getText().trim();
            String amountStr = amountField.getText().trim();
            LocalDate dueDate = dueDatePicker.getValue();

            if (billType == null) {
                showAlert("Error", "Please select a bill type.");
                return;
            }

            if (billNumber.isEmpty()) {
                showAlert("Error", "Please enter a bill number.");
                return;
            }

            if (amountStr.isEmpty()) {
                showAlert("Error", "Please enter an amount.");
                return;
            }

            if (dueDate == null) {
                showAlert("Error", "Please select a due date.");
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

            // Make API call to process bill payment
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getStoredToken()); // Implement token storage/retrieval

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<TransactionDTO> response = restTemplate.exchange(
                    "http://localhost:8080/api/transactions/withdraw/{accountId}?amount={amount}",
                    HttpMethod.POST,
                    entity,
                    TransactionDTO.class,
                    getCurrentAccountId(), // Implement account ID retrieval
                    amount);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                showAlert("Success", "Bill payment successful!");
                closeDialog();
            } else {
                showAlert("Error", "Failed to process bill payment. Please check your balance.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the bill payment.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) billTypeComboBox.getScene().getWindow();
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
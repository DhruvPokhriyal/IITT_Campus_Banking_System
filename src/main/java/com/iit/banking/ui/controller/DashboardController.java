package com.iit.banking.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.iit.banking.dto.TransactionDTO;
import com.iit.banking.dto.AccountDTO;

public class DashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label lastTransactionLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private TableView<TransactionDTO> transactionsTable;
    @FXML
    private TableColumn<TransactionDTO, String> dateColumn;
    @FXML
    private TableColumn<TransactionDTO, String> descriptionColumn;
    @FXML
    private TableColumn<TransactionDTO, Double> amountColumn;
    @FXML
    private TableColumn<TransactionDTO, String> statusColumn;

    private final RestTemplate restTemplate = new RestTemplate();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Timeline timeline;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupClock();
        loadDashboardData();
    }

    private void setupTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setupClock() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLabel.setText(LocalDateTime.now().format(timeFormatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadDashboardData() {
        try {
            // Load account details
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getStoredToken()); // Implement token storage/retrieval

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<AccountDTO> accountResponse = restTemplate.exchange(
                    "http://localhost:8080/api/accounts/{accountNumber}",
                    HttpMethod.GET,
                    entity,
                    AccountDTO.class,
                    getCurrentAccountNumber() // Implement account number retrieval
            );

            if (accountResponse.getStatusCode().is2xxSuccessful() && accountResponse.getBody() != null) {
                AccountDTO account = accountResponse.getBody();
                balanceLabel.setText(String.format("$%.2f", account.getBalance()));
            }

            // Load recent transactions
            ResponseEntity<List<TransactionDTO>> transactionsResponse = restTemplate.exchange(
                    "http://localhost:8080/api/transactions/account/{accountId}",
                    HttpMethod.GET,
                    entity,
                    (Class<List<TransactionDTO>>) (Class<?>) List.class,
                    getCurrentAccountId() // Implement account ID retrieval
            );

            if (transactionsResponse.getStatusCode().is2xxSuccessful() && transactionsResponse.getBody() != null) {
                ObservableList<TransactionDTO> transactions = FXCollections
                        .observableArrayList(transactionsResponse.getBody());
                transactionsTable.setItems(transactions);

                if (!transactions.isEmpty()) {
                    TransactionDTO lastTransaction = transactions.get(0);
                    lastTransactionLabel.setText(String.format("%s - $%.2f",
                            lastTransaction.getDescription(),
                            lastTransaction.getAmount()));
                }
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load dashboard data.");
            e.printStackTrace();
        }
    }

    // Navigation handlers
    @FXML
    private void handleDashboard() {
        /* Already on dashboard */ }

    @FXML
    private void handleAccounts() {
        navigateToScreen("accounts.fxml");
    }

    @FXML
    private void handleTransactions() {
        navigateToScreen("transactions.fxml");
    }

    @FXML
    private void handleTransfers() {
        navigateToScreen("transfers.fxml");
    }

    @FXML
    private void handleStatements() {
        navigateToScreen("statements.fxml");
    }

    @FXML
    private void handleReports() {
        navigateToScreen("reports.fxml");
    }

    @FXML
    private void handleProfile() {
        navigateToScreen("profile.fxml");
    }

    @FXML
    private void handleSettings() {
        navigateToScreen("settings.fxml");
    }

    @FXML
    private void handleHelp() {
        navigateToScreen("help.fxml");
    }

    @FXML
    private void handleAbout() {
        navigateToScreen("about.fxml");
    }

    // Action handlers
    @FXML
    private void handleDeposit() {
        showDialog("deposit.fxml");
    }

    @FXML
    private void handleWithdraw() {
        showDialog("withdraw.fxml");
    }

    @FXML
    private void handleTransfer() {
        showDialog("transfer.fxml");
    }

    @FXML
    private void handlePayBills() {
        showDialog("paybills.fxml");
    }

    @FXML
    private void handleRefresh() {
        loadDashboardData();
    }

    @FXML
    private void handleLogout() {
        try {
            // Clear stored credentials
            clearStoredCredentials();

            // Navigate to login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Login - IIT Campus Banking System");
        } catch (Exception e) {
            showAlert("Error", "Failed to logout.");
            e.printStackTrace();
        }
    }

    private void navigateToScreen(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle(fxmlFile.replace(".fxml", "") + " - IIT Campus Banking System");
        } catch (Exception e) {
            showAlert("Error", "Failed to load screen: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showDialog(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/" + fxmlFile));
            Parent root = loader.load();

            Stage dialog = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            dialog.setScene(scene);
            dialog.setTitle(fxmlFile.replace(".fxml", "") + " - IIT Campus Banking System");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (Exception e) {
            showAlert("Error", "Failed to load dialog: " + fxmlFile);
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

    // TODO: Implement these methods for token and account management
    private String getStoredToken() {
        return "";
    }

    private String getCurrentAccountNumber() {
        return "";
    }

    private Long getCurrentAccountId() {
        return 1L;
    }

    private void clearStoredCredentials() {
    }
}
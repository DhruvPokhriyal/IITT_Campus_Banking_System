package com.iit.banking.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
import com.iit.banking.dto.AccountDTO;
import com.iit.banking.dto.TransactionDTO;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;

public class AccountsController {

    @FXML
    private Label statusLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private TableView<AccountDTO> accountsTable;
    @FXML
    private TableColumn<AccountDTO, String> accountNumberColumn;
    @FXML
    private TableColumn<AccountDTO, Double> balanceColumn;

    private final RestTemplate restTemplate = new RestTemplate();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Timeline timeline;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupClock();
        loadAccountsData();
    }

    private void setupTableColumns() {
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void setupClock() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLabel.setText(LocalDateTime.now().format(timeFormatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadAccountsData() {
        try {
            statusLabel.setText("Loading accounts...");

            // Load accounts
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getStoredToken()); // Implement token storage/retrieval

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<AccountDTO>> response = restTemplate.exchange(
                    "http://localhost:8080/api/accounts",
                    HttpMethod.GET,
                    entity,
                    (Class<List<AccountDTO>>) (Class<?>) List.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObservableList<AccountDTO> accounts = FXCollections.observableArrayList(response.getBody());
                accountsTable.setItems(accounts);
                statusLabel.setText("Ready");
            } else {
                statusLabel.setText("Failed to load accounts");
                showAlert("Error", "Failed to load accounts data.");
            }
        } catch (Exception e) {
            statusLabel.setText("Error loading accounts");
            showAlert("Error", "An error occurred while loading accounts data.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadAccountsData();
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) statusLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Dashboard - IIT Campus Banking System");
        } catch (Exception e) {
            showAlert("Error", "Failed to load dashboard.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport() {
        try {
            File file = new File("accounts_" + LocalDate.now() + ".csv");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                // Write header
                writer.println("Account Number,Balance");

                // Write data
                for (AccountDTO account : accountsTable.getItems()) {
                    writer.printf("%s,%.2f%n",
                            account.getAccountNumber(),
                            account.getBalance());
                }
            }

            showAlert("Success", "Accounts data exported to " + file.getName());
        } catch (Exception e) {
            showAlert("Error", "Failed to export accounts data.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrint() {
        try {
            // TODO: Implement printing functionality
            showAlert("Info", "Printing functionality will be implemented in a future update.");
        } catch (Exception e) {
            showAlert("Error", "Failed to print accounts data.");
            e.printStackTrace();
        }
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
}
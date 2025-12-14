package ma.ensa.mailbox.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ma.ensa.mailbox.model.Outbox;
import ma.ensa.mailbox.service.facade.OutboxService;
import ma.ensa.mailbox.service.impl.OutboxServiceImpl;

public class OutboxController {

    @FXML
    private TextField toField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea bodyField;

    @FXML
    private Label statusLabel;

    private final OutboxService outboxService = new OutboxServiceImpl();

    @FXML
    private void handleSend() {
        String to = toField.getText().trim();
        String subject = subjectField.getText().trim();
        String body = bodyField.getText().trim();

        // Validation
        if (to.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            showAlert("Error", "Please fill in all fields!", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidEmail(to)) {
            showAlert("Error", "Please enter a valid email address!", Alert.AlertType.ERROR);
            return;
        }

        // Create and send email
        Outbox outbox = new Outbox();
        outbox.setReceiver(to);
        outbox.setSubject(subject);
        outbox.setBody(body);

        try {
            int result = outboxService.sendMail(outbox);

            if (result == 1) {
                showAlert("Success", "Email sent successfully!", Alert.AlertType.INFORMATION);
                statusLabel.setText("✓ Email sent to " + to);
                handleClear();
            } else {
                showAlert("Error", "Failed to send email. Please try again.", Alert.AlertType.ERROR);
                statusLabel.setText("✗ Failed to send email");
            }
        } catch (Exception e) {
            showAlert("Error", "Error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        toField.clear();
        subjectField.clear();
        bodyField.clear();
        statusLabel.setText("");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
package ma.ensa.mailbox.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import ma.ensa.mailbox.repository.impl.InboxDaoImpl;
import ma.ensa.mailbox.repository.impl.OutboxDaoImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label statusLabel;

    @FXML
    private Label lastUpdateLabel;

    @FXML
    public void initialize() {
        showInbox(); // Show inbox by default
        updateLastUpdateTime();
    }

    @FXML
    private void showInbox() {
        loadView("inbox-view.fxml");
        setStatus("Viewing Inbox");
    }

    @FXML
    private void showOutbox() {
        setStatus("Outbox view not implemented yet");
        // TODO: Create outbox-view.fxml and controller
    }

    @FXML
    private void handleCompose() {
        loadView("create-email.fxml");
        setStatus("Composing new email");
    }

    @FXML
    private void handleRefreshInbox() {
        showInbox();
        updateLastUpdateTime();
        setStatus("Inbox refreshed");
    }

    @FXML
    private void handleDeleteAllInbox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete All Inbox Emails");
        alert.setContentText("Are you sure you want to delete all inbox emails?");

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                new InboxDaoImpl().deleteAllMails();
                showInbox();
                setStatus("All inbox emails deleted");
            }
        });
    }

    @FXML
    private void handleDeleteAllOutbox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete All Outbox Emails");
        alert.setContentText("Are you sure you want to delete all outbox emails?");

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                new OutboxDaoImpl().deleteAllMails();
                setStatus("All outbox emails deleted");
            }
        });
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("MailBox Application");
        alert.setContentText("Version 1.0\nA simple email client built with JavaFX\n© 2024 ENSA");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/ensa/mailbox/" + fxmlFile));
            Pane view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
            setStatus("Error loading view: " + fxmlFile);
        }
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }

    private void updateLastUpdateTime() {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        lastUpdateLabel.setText(time);
    }
}
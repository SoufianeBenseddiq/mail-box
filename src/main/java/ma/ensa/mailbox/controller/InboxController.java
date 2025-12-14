package ma.ensa.mailbox.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ma.ensa.mailbox.model.Inbox;
import ma.ensa.mailbox.repository.facade.InboxDao;
import ma.ensa.mailbox.repository.impl.InboxDaoImpl;
import ma.ensa.mailbox.service.facade.InboxService;
import ma.ensa.mailbox.service.impl.InboxServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InboxController {

    @FXML
    private TableView<Inbox> emailTable;

    @FXML
    private TableColumn<Inbox, String> senderColumn;

    @FXML
    private TableColumn<Inbox, String> subjectColumn;

    @FXML
    private TableColumn<Inbox, String> dateColumn;

    @FXML
    private TextField searchField;

    @FXML
    private VBox detailsPanel;

    @FXML
    private Label detailSender;

    @FXML
    private Label detailSubject;

    @FXML
    private Label detailDate;

    @FXML
    private TextArea detailBody;

    private final InboxDao inboxDao = new InboxDaoImpl();
    private final InboxService inboxService = new InboxServiceImpl();
    private ObservableList<Inbox> emailList;
    private Inbox selectedEmail;

    @FXML
    public void initialize() {
        setupTable();
        loadEmails();
        setupTableSelectionListener();
    }

    private void setupTable() {
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));

        // Format date column
        dateColumn.setCellFactory(column -> new TableCell<Inbox, String>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Inbox email = getTableView().getItems().get(getIndex());
                    setText(format.format(new Date()));
                }
            }
        });
    }

    private void setupTableSelectionListener() {
        emailTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        showEmailDetails(newSelection);
                    }
                }
        );
    }

    private void loadEmails() {
        List<Inbox> emails = inboxDao.findAll();
        emailList = FXCollections.observableArrayList(emails);
        emailTable.setItems(emailList);

        System.out.println("✓ Loaded " + emails.size() + " emails");
    }

    private void showEmailDetails(Inbox email) {
        selectedEmail = email;
        detailSender.setText(email.getSender());
        detailSubject.setText(email.getSubject());
        detailDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        detailBody.setText(email.getBody());
        detailsPanel.setVisible(true);
    }

    @FXML
    private void handleRefresh() {
        System.out.println("Fetching new emails from server...");

        // Fetch last email from server
        Inbox newEmail = inboxService.fetchLastEmail();

        if (newEmail != null) {
            loadEmails(); // Reload table
            showAlert("Success", "New email fetched successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Info", "No new emails found", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedEmail != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete Email");
            confirm.setContentText("Are you sure you want to delete this email?");

            confirm.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    int result = inboxDao.delete(selectedEmail);
                    if (result > 0) {
                        emailList.remove(selectedEmail);
                        detailsPanel.setVisible(false);
                        showAlert("Success", "Email deleted successfully!", Alert.AlertType.INFORMATION);
                    }
                }
            });
        }
    }

    @FXML
    private void handleCloseDetails() {
        detailsPanel.setVisible(false);
        emailTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
module ma.ensa.mailbox {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.mail;
    requires com.google.gson;
    requires mysql.connector.j;

    opens ma.ensa.mailbox to javafx.fxml;
    opens ma.ensa.mailbox.controller to javafx.fxml;
    opens ma.ensa.mailbox.model to com.google.gson;
    opens ma.ensa.mailbox.config to javafx.fxml;

    exports ma.ensa.mailbox;
    exports ma.ensa.mailbox.config;
}
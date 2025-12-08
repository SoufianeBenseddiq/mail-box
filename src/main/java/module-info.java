module ma.ensa.mailbox {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ma.ensa.mailbox to javafx.fxml;
    exports ma.ensa.mailbox;
}
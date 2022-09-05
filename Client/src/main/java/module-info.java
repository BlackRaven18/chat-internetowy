module com.client.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.json;
    requires java.desktop;
    requires org.testng;

    opens com.client.client to javafx.fxml;
    exports com.client.client;
}
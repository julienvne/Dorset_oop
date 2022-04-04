module com.example.gossipers {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.dorset.gossipers to javafx.fxml;
    exports com.dorset.gossipers;
    exports com.dorset.gossipers.server;
}
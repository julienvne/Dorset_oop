module com.example.gossipers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gossipers to javafx.fxml;
    exports com.example.gossipers;
}
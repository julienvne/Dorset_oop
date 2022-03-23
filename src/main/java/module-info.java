module com.example.gossipers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dorset.gossipers to javafx.fxml;
    exports com.dorset.gossipers;
}
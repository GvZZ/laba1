module com.example.laba1_test {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laba1_test to javafx.fxml;
    exports com.example.laba1_test;
}
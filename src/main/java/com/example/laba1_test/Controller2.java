package com.example.laba1_test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Controller2{
    private Habitat habitat;
    @FXML
    private Label cout1;

    @FXML
    private Label cout2;

    @FXML
    void initialize() {
        // Проверка наличия habitat
        if (habitat != null) {
            // Установка значений в Label
            cout1.setText(Integer.toString(habitat.getDroneCount()));
            cout2.setText(Integer.toString(habitat.getWorkerCount()));
        }
    }

}

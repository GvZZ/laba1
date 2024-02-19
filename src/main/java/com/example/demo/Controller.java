package com.example.demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timer;

    @FXML
    void Up_Timer(KeyEvent event) {
        if(event.getCode() == KeyCode.T) {
            if (timer.isVisible()) {
                timer.setVisible(false);
            } else {
                timer.setVisible(true);
            }
        }
    }

    @FXML
    void initialize() {
        assert timer != null : "fx:id=\"timer\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}


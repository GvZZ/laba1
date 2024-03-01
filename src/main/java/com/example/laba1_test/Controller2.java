package com.example.laba1_test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Controller2 {

    private Habitat habitat;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane SceneThree_Background;

    @FXML
    private Label cout1;

    @FXML
    private Label cout2;

    @FXML
    private Label timer;


    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    @FXML
    void initialize() {
        if (habitat != null) {
            cout1.setText(Integer.toString(habitat.getPhysicalPersonCount()));
            cout2.setText(Integer.toString(habitat.getLegalPersonCount()));
        }
    }

}

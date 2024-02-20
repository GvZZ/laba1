package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class Controller {

    Time time = new Time("0:0");
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane SceneTwo_Background;

    @FXML
    private Label timer;


    @FXML
    void Hide_Show() {
        if (timer.isVisible())
        {
            timer.setVisible(false);
        }
        else
        {
            timer.setVisible(true);
        }
    }

    @FXML
    void exit() throws IOException {
        new SceneSwitch(SceneTwo_Background, "end.fxml");
    }

    @FXML
    void start() {
        if (time.getCurrentTime().equals("0:0")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            e -> {
                                time.OneSecondPassed();
                                timer.setText(time.getCurrentTime());
                            }));
            timer.setText(time.getCurrentTime());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    void initialize() {

    }

}
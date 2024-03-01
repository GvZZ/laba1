package com.example.laba1_test;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Controller {
    AnimationTimer time = new AnimationTimer("0:0");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane SceneTwo_Background;

    @FXML
    private Label timer;

    @FXML
    private Canvas canvas; // Добавление Canvas для отображения объектов

    private Habitat habitat;
    private long startTime;

    public String getTimerValue() {
        return time.getCurrentTime();
    }


    @FXML
    void Hide_Show() {
        if (timer.isVisible()) {
            timer.setVisible(false);
        } else {
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
                                habitat.update(time.Second); // Обновление среды каждую секунду
                                drawObjects();
                            }));
            timer.setText(time.getCurrentTime());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    void initialize() {
        habitat = new Habitat();
        startTime = System.nanoTime();
    }

    private void drawObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (AbstractObject obj : habitat.getObjects()) {
            double x = obj.getX();
            double y = obj.getY();

            if (obj instanceof PhysicalPerson) {
                drawImage(gc, "C:/Users/zemeo/IdeaProjects/laba1_test/src/main/resources/physical_person.png", x, y);
            } else if (obj instanceof LegalPerson) {
                drawImage(gc, "C:/Users/zemeo/IdeaProjects/laba1_test/src/main/resources/legal_person.png", x, y);
            }
        }
    }

    private void drawImage(GraphicsContext gc, String imagePath, double x, double y) {
        Image image = new Image("file:///" + imagePath);
        gc.drawImage(image, x, y, 40, 40);
    }
}
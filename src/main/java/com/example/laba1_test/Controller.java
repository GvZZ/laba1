package com.example.laba1_test;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Controller {
    AnimationTimer time = new AnimationTimer("0:0:0");
    Timeline timeline = new Timeline();
    int LifeTime = 5;
    private int status = 0; // 0 = не работает 1 = работает 2 = standby
    @FXML
    private Button StartB;
    @FXML
    private Button ObjStateBtn;
    @FXML
    private Button StopB;
    @FXML
    private Label cout1;
    @FXML
    private Label cout2;
    @FXML
    private Label timer;
    @FXML
    private Label FinalTime;
    @FXML
    private Label WorkerName;
    @FXML
    private Label DroneName;
    @FXML
    private ToggleButton HideTimeB;
    @FXML
    private ToggleButton ShowTimeB;
    @FXML
    private Canvas canvas; // Добавление Canvas для отображения объектов
    @FXML
    private Button Report;
    private Habitat habitat;

    @FXML
    void Hide_Show() {
        if (status != 1)
        {
            if (timer.isVisible())
            {
                timer.setVisible(false);
            }
            else
            {
                timer.setVisible(true);
            }
        }
    }
    @FXML
    void ButtonControlVisible(){
        timer.setVisible(true);
        ShowTimeB.setSelected(true);

    }
    @FXML
    void ButtonControlNonVisible(){
        timer.setVisible(false);
        HideTimeB.setSelected(true);
    }
    @FXML
    void pauseGen() throws IOException {
        if (Report.getText().equals("Скрыть отчёт")) {
            status = 2;
            timeline.pause();
            ModalWindow.newWindow("Отчёт генерации", Controller.this, habitat);
        }
        else
        {
            exit();
        }

    }
    @FXML
    void continueGen() {
        status = 1;
        timeline.play();
    }
    @FXML
    void exit() throws IOException {
        if (status != 0) {
            StopB.setDisable(true);
            StartB.setDisable(true);
            ShowTimeB.setDisable(true);
            HideTimeB.setDisable(true);
            Report.setDisable(true);
            canvas.setVisible(false);
            timer.setVisible(false);
            cout1.setVisible(true);
            cout2.setVisible(true);
            FinalTime.setVisible(true);
            DroneName.setVisible(true);
            WorkerName.setVisible(true);
            cout1.setText(Integer.toString(habitat.getDroneCount()));
            cout2.setText(Integer.toString(habitat.getWorkerCount()));
            FinalTime.setText(time.getCurrentTime());
            timeline.stop();
        }
    }
    @FXML
    void SetReportVisibility(){
        if (Report.getText().equals("Скрыть отчёт"))
        {
            Report.setText("Показать отчёт");
        }
        else
        {
            Report.setText("Скрыть отчёт");
        }
    }

    @FXML
    void start() {
        status = 1;
        StartB.setDisable(true);
        StopB.setDisable(false);
        if (time.getCurrentTime().equals("0:0:0")) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.1),
                            e -> {
                                time.OneTick();
                                timer.setText(time.getCurrentTime());
                                if (time.MSecond == 0)
                                {
                                    habitat.update(time.Second, time, LifeTime); // Че за хуйня блять
                                }
                                drawObjects();
                            }));
            timer.setText(time.getCurrentTime());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
    @FXML
    void initialize() {
        habitat = ModalWindow.HelloWindow("Привет, пчеловод!", Controller.this);
        status = 0;
        cout1.setVisible(false);
        cout2.setVisible(false);
        FinalTime.setVisible(false);
        DroneName.setVisible(false);
        WorkerName.setVisible(false);
        StopB.setDisable(true);

    }
    @FXML
    public void ShowCurrentObjectsState() throws IOException {
        status = 2;
        timeline.pause();
        ModalWindow.ObjShow("Отчёт генерации", Controller.this, habitat);
    }
    private void drawObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (AbstractObject obj : habitat.getObjects()) {
            double x = obj.getX();
            double y = obj.getY();

            if (obj instanceof Drone) {
                drawImage(gc, "IMGDrone.png", x, y);
            }
            else if (obj instanceof Worker) {
                drawImage(gc, "IMGWorker.png", x, y);
            }
        }
    }
    private void drawImage(GraphicsContext gc, String imagePath, double x, double y) {
        Image image = new Image(imagePath);
        gc.drawImage(image, x, y, 100, 100);
    }
}
package com.example.laba1_test;

import java.io.FileWriter;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Integer.parseInt;


public class Controller {
    AnimationTimer time = new AnimationTimer("0:0:0");
    Timeline timeline = new Timeline();
    int LifeTime = 5;
    private int status = 0; // 0 = не работает 1 = работает 2 = standby
    private Boolean AIStatusDrone = true;
    private Boolean AIStatusWorker = true;
    @FXML
    private AnchorPane SceneTwo_Background;
    @FXML
    private Button StartB;
    @FXML
    private TextArea ChangeLifeTime;
    @FXML
    private TextArea ChangeInterval;
    @FXML
    private TextArea ChangeChance;
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
    private Canvas canvas;
    @FXML
    private Button Report;
    @FXML
    private Button DroneControl;
    @FXML
    private Button WorkerControl;
    @FXML
    private Button ConsoleButton;
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
    boolean CheckCngInt(TextArea x)
    {
        try
        {
            Integer.parseInt(x.getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    boolean CheckCngDouble(TextArea x)
    {
        try
        {
            Double.parseDouble(x.getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    void ConsoleCommandAdmin(TextArea text, Label label){
        String str = text.getText();
        try {
            text.clear();
        }
        catch (IllegalArgumentException e){
            System.out.println("Неудача...");
        }
        switch (str)
        {
            case("help\n"):
                label.setText(label.getText() + "help - справка\n"
                        + "get Drone amount - количество трутней\n"
                        + "get Worker amount - количество рабочих\n"
                        + "cls - очистить вывод консоли\n"
                        + "get Bee status - узнать статус пчёл\n");
                break;
            case("get Drone amount\n"):
                label.setText(label.getText() + "Количество трутней: " + habitat.getDroneCount() + " время: " + timer.getText() + '\n');
                break;
            case("get Worker amount\n"):
                label.setText(label.getText() + "Количество рабочих: " + habitat.getWorkerCount() + " время: " + timer.getText() + '\n');
                break;
            case("cls\n"):
                label.setText("");
                break;
            case("get Bee status\n"):
                if (AIStatusDrone)
                {
                    label.setText(label.getText() + "Трутни балдеют, ");
                }
                else
                {
                    label.setText(label.getText() + "Трутни спят, ");
                }
                if (AIStatusWorker)
                {
                    label.setText(label.getText() + "рабочие работают\n");
                }
                else
                {
                    label.setText(label.getText() + "у рабочих перекур\n");
                }
                break;
            case("sus\n"):
            {
                label.setText(label.getText() + "When Bee is sus\n" +
                        " ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀ ⣴⣿⡿⠛⠉⠙⠛⠛⠛⠛⠻⢿⣷⣤⡀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⠋⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠈⢻⣿⣿⡄⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣸⣿⡏⠀⠀⠀⣠⣶⣾⣿⣿⣿⠿⠿⠿⢿⣿⣿⣄⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⢰⣿⣿⣯⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣷⡄⠀\n" +
                        "⠀⠀⣀⣤⣴⣶⣶⣿⡟⠀⠀⠀⢸⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠀\n" +
                        "⠀⢰⣿⡟⠋⠉⣹⣿⡇⠀⠀⠀⠘⣿⣿⣿⣿⣷⣦⣤⣤⣤⣶⣶⣶⣶⣿⠀\n" +
                        "⠀⢸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀\n" +
                        "⠀⣸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠉⠻⠿⣿⣿⣿⣿⡿⠿⠿⠛⢻⡇⠀⠀\n" +
                        "⠀⣿⣿⠁⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣧⠀⠀\n" +
                        "⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀\n" +
                        "⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀\n" +
                        "⠀⢿⣿⡆⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀\n" +
                        "⠀⠸⣿⣧⡀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠃⠀⠀\n" +
                        "⠀⠀⠛⢿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣰⣿⣿⣷⣶⣶⣶⣶⠶⠀⢠⣿⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⣽⣿⡏⠁⠀⠀⢸⣿⡇⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⢹⣿⡆⠀⠀⠀⣸⣿⠇⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⢿⣿⣦⣄⣀⣠⣴⣿⣿⠁⠀⠈⠻⣿⣿⣿⣿⡿⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠈⠛⠻⠿⠿⠿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
                break;
            }
            default:
                label.setText(label.getText() + "Вы ввели несущестующую команду\n");
        }
    }
    @FXML
    void ConsoleWindow(){
        if (status != 0)
        {
            Font CS = new Font("Comic Sans MS Italic", 12.0);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #000000");
            TextArea text = new TextArea();
            text.setPrefHeight(25);
            text.setPrefWidth(200);
            text.setLayoutX(250);
            text.setLayoutY(450);
            text.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00;"); // Честно украдено со стак оверфлоу, потому что мне лень через ColorPicker выискивать подходящие цвета для консоли
            Label label = new Label("Для справки введите help\n");
            label.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00;");
            label.setMaxWidth(700);
            label.setMaxHeight(450);
            //
            pane.getChildren().addAll(text);
            pane.getChildren().addAll(label);
            Scene scene = new Scene(pane, 700, 500);
            window.setScene(scene);
            window.setTitle("Пчелиная консоль");
            window.setResizable(false);
            window.show();
            text.textProperty().addListener((observable, oldValue, newValue) -> {
                if (text.getText().endsWith("\n")) {
                    ConsoleCommandAdmin(text, label);
                }
            });
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
    void pauseGen() throws IOException, InterruptedException {
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
        try {
            habitat.ContinueThreads();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void exit() throws IOException {
        if (status != 0) {
            for (AbstractObject x : habitat.getObjects())
            {
                x.allstop();
            }
            StopB.setDisable(true);
            StartB.setDisable(true);
            ShowTimeB.setDisable(true);
            HideTimeB.setDisable(true);
            Report.setDisable(true);
            ConsoleButton.setDisable(true);
            canvas.setVisible(false);
            timer.setVisible(false);
            ChangeInterval.setVisible(false);
            ChangeLifeTime.setVisible(false);
            ChangeChance.setVisible(false);
            try {
                habitat.StopThreads();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cout1.setVisible(true);
            cout2.setVisible(true);
            FinalTime.setVisible(true);
            DroneName.setVisible(true);
            WorkerName.setVisible(true);
            try {
                FileWriter writer = new FileWriter("src/main/resources/save.txt");
                writer.write(Double.toString(habitat.getChance()) + '\n' // Шанс спавна
                + Integer.toString(habitat.getInterval()) + '\n' // Интервал
                + Integer.toString(LifeTime) + '\n' // Время жизни
                + Boolean.toString(AIStatusWorker) + '\n' // Статус рабочих
                + Boolean.toString(AIStatusDrone) + '\n' // Статус трутней
                );

                writer.close();
            }
            catch (Exception e){

            }
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
        if (CheckCngInt(ChangeLifeTime))
        {
            if (parseInt(ChangeLifeTime.getText()) > 0 && parseInt(ChangeLifeTime.getText()) < 60)
            {
                LifeTime = parseInt(ChangeLifeTime.getText());
            }
        }
        ChangeLifeTime.setText("Время жизни: " + LifeTime);
        ChangeLifeTime.setEditable(false);
        if (CheckCngInt(ChangeInterval))
        {
            if (parseInt(ChangeInterval.getText()) > 0 && parseInt(ChangeInterval.getText()) < 60)
            {
                habitat.setInterval(parseInt(ChangeInterval.getText()));
            }
        }
        ChangeInterval.setText("Интервал появления: " + habitat.getInterval());
        ChangeInterval.setEditable(false);
        if (CheckCngDouble(ChangeChance))
        {
            if (Double.parseDouble(ChangeChance.getText()) > 0 && Double.parseDouble(ChangeChance.getText()) <= 1) {
                habitat.setChance(parseInt(ChangeChance.getText()));
            }
        }
        ChangeChance.setText("Шанс появления: " + habitat.getChance());
        ChangeChance.setEditable(false);
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
                                    habitat.update(time.Second, time, LifeTime, SceneTwo_Background, Controller.this); // Че за хуйня блять

                                }
                            }));
            timer.setText(time.getCurrentTime());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
    @FXML
    void initialize() {
        habitat = ModalWindow.HelloWindow("Привет, пчеловод!", Controller.this);
        if (AIStatusDrone) {
            DroneControl.setText("Трутни спать");
        }
        else
        {
            DroneControl.setText("Трутни бегать");
        }
        if (AIStatusWorker) {
            WorkerControl.setText("Рабочие спать");
        }
        else
        {
            WorkerControl.setText("Рабочие работать");
        }
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

    @FXML
    public void PauseAiDrone(){
        Drone new_Drone = new Drone();
        if (AIStatusDrone) {
            DroneControl.setText("Трутни бегать");
            AIStatusDrone = false;
            for (AbstractObject x : habitat.getObjects()) {
                if (x.getClass() == new_Drone.getClass()) {
                    x.StopTransition();
                }
            }
        }
        else
        {
            DroneControl.setText("Трутни спать");
            AIStatusDrone = true;
            for (AbstractObject x : habitat.getObjects()) {
                if (x.getClass() == new_Drone.getClass()) {
                    x.ContinueTransition();
                }
            }
        }
    }
    @FXML
    public void PauseAiWorker(){
        Worker new_Worker = new Worker();
        if (AIStatusWorker) {
            WorkerControl.setText("Рабочие работать");
            AIStatusWorker = false;
            for (AbstractObject x : habitat.getObjects()) {
                if (x.getClass() == new_Worker.getClass()) {
                    x.StopTransition();
                }
            }
        }
        else
        {
            WorkerControl.setText("Рабочие спать");
            AIStatusWorker = true;
            for (AbstractObject x : habitat.getObjects()) {
                if (x.getClass() == new_Worker.getClass()) {
                    x.ContinueTransition();
                }
            }
        }
    }

    public Boolean getAIStatusWorker(){return AIStatusWorker;}
    public Boolean getAIStatusDrone(){return AIStatusDrone;}
    public void setAIStatusWorker(Boolean x){this.AIStatusWorker = x;}
    public void setAIStatusDrone(Boolean x){this.AIStatusDrone = x;}
    public void setLifeTime(int x){LifeTime = x;}
}
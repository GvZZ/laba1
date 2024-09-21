package com.example.laba1_test;

import java.io.*;
import java.util.Properties;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Integer.parseInt;


public class Controller implements Serializable {
    AnimationTimer time = new AnimationTimer("0:0:0");
    Timeline timeline = new Timeline();
    int LifeTime;
    DroneAI DAI;
    WorkerAI WAI;
    private int status = 0; // 0 = не работает 1 = работает 2 = standby
    FileChooser fileChooser = new FileChooser();
    private Boolean AIStatusDrone = true;
    private Boolean AIStatusWorker = true;
    @FXML
    private AnchorPane SceneTwo_Background;
    @FXML
    private TextArea ChangeInterval, ChangeLifeTime;
    @FXML
    private Button LoadButton, SaveButton, StartB, DroneControl, WorkerControl, ConsoleButton, StopB, ObjStateBtn;
    @FXML
    private Label cout1, cout2, timer, FinalTime, WorkerName, DroneName;
    @FXML
    private ToggleButton HideTimeB, ShowTimeB;
    @FXML
    private Canvas canvas;
    @FXML
    private CheckBox Report;
    private ComboBox<String> ChangeChance;
    private Habitat habitat = new Habitat(-1, 5, this);
    @FXML
    void LoadSavedData(){
        SettingsSet(fileChooser.showOpenDialog(new Stage()));

    }
    @FXML
    void HelloWindow() {
        Font CS = new Font("Comic Sans MS Italic", 12.0);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        TextArea text = new TextArea("Добро пожаловать, мой пчеловод. Сегодня мы займёмся разведением пчёл!\n" +
                "Для начала необходимо ознакомиться с базовыми командами программы.\n" +
                "Кнопки \"Старт\" и \"Стоп\" начинают процесс рождения пчёл и останавливают соответственно.\n" +
                "Кнопки \"Показать таймер\" и \"Скрыть таймер\" отображают и скрывают таймер в верхнем левом углу\n" +
                "Последняя кнопка отвечает за разрешение отображения модального окна при завершении симуляции\n" +
                "Ещё есть кнопки, отвечающие за интеллект пчёл." +
                "Удачи.");
        text.setEditable(false);
        text.setPrefHeight(150);
        text.setPrefWidth(610);
        text.setLayoutX(45);
        text.setLayoutY(100);
        text.setFont(CS);
        pane.getChildren().add(text);
        Scene scene = new Scene(pane, 700, 500);
        window.setScene(scene);
        window.setTitle("Help");
        window.setResizable(false);
        window.showAndWait();
    }
    @FXML
    void Hide_Show() {
        if (status != 1)
        {
            timer.setVisible(!timer.isVisible());
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

    void SettingsSet(File file) {
        File fileSer = new File(fileChooser.showOpenDialog(new Stage()).getName());
        ModalWindow.setSettings(this, habitat, file, fileSer);
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
                label.setText("help - справка\n"
                        + "get Drone amount - количество трутней\n"
                        + "get Worker amount - количество рабочих\n"
                        + "cls - очистить вывод консоли\n"
                        + "get Bee status - узнать статус пчёл\n");
                break;
            case("get Drone amount\n"):
                label.setText("Количество трутней: " + habitat.getDroneCount() + " время: " + timer.getText() + '\n');
                break;
            case("get Worker amount\n"):
                label.setText("Количество рабочих: " + habitat.getWorkerCount() + " время: " + timer.getText() + '\n');
                break;
            case("cls\n"):
                label.setText("");
                break;
            case("get Bee status\n"):
                if (AIStatusDrone)
                {
                    label.setText("Трутни балдеют, ");
                }
                else
                {
                    label.setText("Трутни спят, ");
                }
                if (AIStatusWorker)
                {
                    label.setText("рабочие работают\n");
                }
                else
                {
                    label.setText("у рабочих перекур\n");
                }
                break;
            case("sus\n"):
            {
                label.setText("When Bee is sus\n" + " ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⠀ ⣴⣿⡿⠛⠉⠙⠛⠛⠛⠛⠻⢿⣷⣤⡀⠀⠀⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⠋⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠈⢻⣿⣿⡄⠀⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⣸⣿⡏⠀⠀⠀⣠⣶⣾⣿⣿⣿⠿⠿⠿⢿⣿⣿⣄⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⢰⣿⣿⣯⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣷⡄⠀\n" + "⠀⠀⣀⣤⣴⣶⣶⣿⡟⠀⠀⠀⢸⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠀\n" + "⠀⢰⣿⡟⠋⠉⣹⣿⡇⠀⠀⠀⠘⣿⣿⣿⣿⣷⣦⣤⣤⣤⣶⣶⣶⣶⣿⠀\n" + "⠀⢸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀\n" + "⠀⣸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠉⠻⠿⣿⣿⣿⣿⡿⠿⠿⠛⢻⡇⠀⠀\n" + "⠀⣿⣿⠁⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣧⠀⠀\n" + "⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀\n" + "⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀\n" + "⠀⢿⣿⡆⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀\n" + "⠀⠸⣿⣧⡀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠃⠀⠀\n" + "⠀⠀⠛⢿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣰⣿⣿⣷⣶⣶⣶⣶⠶⠀⢠⣿⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⣽⣿⡏⠁⠀⠀⢸⣿⡇⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⢹⣿⡆⠀⠀⠀⣸⣿⠇⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⢿⣿⣦⣄⣀⣠⣴⣿⣿⠁⠀⠈⠻⣿⣿⣿⣿⡿⠀⠀⠀⠀\n" + "⠀⠀⠀⠀⠀⠀⠀⠈⠛⠻⠿⠿⠿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
                break;
            }
            default:
                label.setText("Вы ввели несущестующую команду\nЕсли вы забыли список команд введите help");
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
            text.setFont(CS);
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
        if (Report.isSelected()) {
            status = 2;
            timeline.pause();
            StopThreads();
            ModalWindow.newWindow("Отчёт генерации", Controller.this, habitat);
        }
        else {exit();}
    }
    @FXML
    void continueGen() {
        status = 1;
        timeline.play();
        ContinueThreads();
    }
    @FXML
    void SaveSettings() throws IOException, InterruptedException {
        status = 2;
        timeline.pause();
        StopThreads();
        ModalWindow.SaveNotify();
        exit();
    }
    @FXML
    void exit() throws IOException, InterruptedException {
        if (status != 0) {
            try {
                System.out.println("Начал сейвить");
                File file = fileChooser.showOpenDialog(new Stage());
                Properties prop = new Properties();
                prop.setProperty("Spawn Chance", String.valueOf(habitat.getChance()));
                prop.setProperty("Interval", String.valueOf(habitat.getInterval()));
                prop.setProperty("Life Time", String.valueOf(LifeTime));
                prop.setProperty("AI Status Worker", String.valueOf(AIStatusWorker));
                prop.setProperty("AI Status Drone", String.valueOf(AIStatusDrone));
                prop.storeToXML(new FileOutputStream(file.getPath()), "Saved data for " + " " + file.getName());
                System.out.println("Полностью засейвил");
            }
            catch (Exception ignored){
            }
            File file = new File("objSave1.ser");
            System.out.println("Размер файла: " + file.length() + " байт.");
            try {
                String name = fileChooser.showOpenDialog(new Stage()).getName();
                FileOutputStream fileOut = new FileOutputStream(name);
                System.out.println(name);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(habitat.getObjects());
                fileOut.close();
                objOut.close();
            }
            catch(Exception ignored){

            }
            System.out.println("Файл существует: " + file.exists());
            System.out.println("Размер файла: " + file.length() + " байт.");

            status = 2;
            for (AbstractObject x : habitat.objects) {x.getPathTransition().setNode(null); SceneTwo_Background.getChildren().remove(x.getImg());}
            StopB.setDisable(true);
            StartB.setDisable(true);
            ShowTimeB.setDisable(true);
            HideTimeB.setDisable(true);
            Report.setDisable(true);
            ConsoleButton.setDisable(true);
            ObjStateBtn.setDisable(true);
            DroneControl.setDisable(true);
            WorkerControl.setDisable(true);
            canvas.setVisible(false);
            timer.setVisible(false);
            ChangeInterval.setVisible(false);
            ChangeLifeTime.setVisible(false);
            StopThreads();
            cout1.setVisible(true);
            cout2.setVisible(true);
            FinalTime.setVisible(true);
            DroneName.setVisible(true);
            WorkerName.setVisible(true);
            AIStatusDrone = false;
            AIStatusWorker = false;
            cout1.setText(Integer.toString(habitat.getDroneCount()));
            cout2.setText(Integer.toString(habitat.getWorkerCount()));
            FinalTime.setText(time.getCurrentTime());
            timeline.stop();
        }
    }
    @FXML
    void start() {
        LoadButton.setDisable(true);
        SaveButton.setDisable(false);
        if (this.habitat.getInterval() == -1){
            habitat = new Habitat(parseInt(ChangeInterval.getText()), Double.parseDouble(ChangeChance.getValue().substring(0, ChangeChance.getValue().length() - 1)) / 100, this);
            LifeTime = parseInt(ChangeLifeTime.getText());
        }
        DAI = new DroneAI(this);
        WAI = new WorkerAI(this);
        DAI.start();
        WAI.start();
        ChangeInterval.setEditable(false);
        ChangeLifeTime.setEditable(false);
        ChangeChance.setDisable(true);
        status = 1;
        StartB.setDisable(true);
        StopB.setDisable(false);
        if (time.getCurrentTime().equals("0:0:0")) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.01),
                            e -> {
                                time.OneTick();
                                timer.setText(time.getCurrentTime());
                                if (time.MSecond % 1 == 0)
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
        fileChooser.setInitialDirectory(new File("C:\\Users\\suxov\\IdeaProjects\\laba1\\src\\main\\resources"));
        ObservableList<String> percents = FXCollections.observableArrayList("10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%");
        ChangeChance = new ComboBox<String>(percents);
        ChangeChance.setValue("90%");
        ChangeChance.setPrefHeight(15);
        ChangeChance.setPrefWidth(150);
        ChangeChance.setLayoutX(1600);
        ChangeChance.setLayoutY(474);
        SceneTwo_Background.getChildren().add(ChangeChance);
        /*habitat = ModalWindow.HelloWindow("Привет, пчеловод!", Controller.this);*/
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
        ChangeLifeTime.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        ChangeInterval.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
    }
    @FXML
    public void ShowCurrentObjectsState() throws IOException, InterruptedException {
        status = 2;
        timeline.pause();
        ModalWindow.ObjShow("Отчёт генерации", Controller.this, habitat);
    }

    @FXML
    public void PauseAiDrone(){
        if (AIStatusDrone) {
            DroneControl.setText("Трутни бегать");
            AIStatusDrone = false;
            DAI.setAIState(false);
            for (AbstractObject x : habitat.getObjects()) {
                if (x instanceof Drone) {
                    x.getPathTransition().pause();
                }
            }
        }
        else
        {
            DroneControl.setText("Трутни спать");
            AIStatusDrone = true;
            DAI.setAIState(true);
            for (AbstractObject x : habitat.getObjects()) {
                if (x instanceof Worker) {
                    x.getPathTransition().play();
                }
            }
        }
    }
    @FXML
    public void PauseAiWorker(){
        if (AIStatusWorker) {
            WorkerControl.setText("Рабочие работать");
            AIStatusWorker = false;

            for (AbstractObject x : habitat.getObjects()) {
                if (x instanceof Worker) {
                    x.getPathTransition().pause();
                }
            }
        }
        else
        {
            WorkerControl.setText("Рабочие спать");
            AIStatusWorker = true;

            for (AbstractObject x : habitat.getObjects()) {
                if (x instanceof Worker) {
                    x.getPathTransition().play();
                }
            }
        }
    }

    public Boolean getAIStatusWorker(){return AIStatusWorker;}
    public Boolean getAIStatusDrone(){return AIStatusDrone;}
    public Habitat getHabitat() {return habitat;}
    public int getLifeTime(){return LifeTime;}
    public void setAIStatusWorker(Boolean x){this.AIStatusWorker = x;}
    public void setAIStatusDrone(Boolean x){this.AIStatusDrone = x;}
    public void setLifeTime(int x){LifeTime = x;}
    public void setChangeLifeTime(String x){ChangeLifeTime.setText(x);}
    public void setChangeInterval(String x){ChangeInterval.setText(x);}
    public AnchorPane getSceneTwo_Background(){return SceneTwo_Background;}
    public void StopThreads(){
        DAI.setAIState(false);
        WAI.setAIState(false);
        for (int i = 0; i < habitat.objects.size(); i++)
        {
            habitat.objects.get(i).getPathTransition().pause();
        }
    }
    public void ContinueThreads(){
        System.out.println("Вызвал и должен продолжить");
        DAI.setAIState(true);
        WAI.setAIState(true);
        for (int i = 0; i < habitat.objects.size(); i++)
        {
            habitat.objects.get(i).getPathTransition().play();
        }
        System.out.println("Отработал");
    }
    public int getStatus() {return this.status;}
}
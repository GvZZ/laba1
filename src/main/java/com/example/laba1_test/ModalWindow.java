package com.example.laba1_test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ModalWindow {
    public static boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void setSettings(Controller controller, Habitat habitat){
        try {
            FileReader reader = new FileReader("src/main/resources/save.txt");
            int data = reader.read();
            String str = "";
            while (data != -1)
            {
                str += (char)data;
                data = reader.read();
            }
            reader.close();
            String[] result = str.split("\n");
            double Chance = Double.parseDouble(result[0]);
            int Interval = Integer.parseInt(result[1]);
            int lifetime = Integer.parseInt(result[2]);
            Boolean AIWorker = Boolean.parseBoolean(result[3]);
            Boolean AIDrone = Boolean.parseBoolean(result[4]);
            if (Chance >= 0 && Chance <= 1 && Interval >= 1)
            {
                controller.setAIStatusWorker(AIWorker);
                controller.setAIStatusDrone(AIDrone);
                controller.setLifeTime(lifetime);
                habitat.setChance(Chance);
                habitat.setInterval(Interval);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Файл сохранения не найден");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void newWindow(String Name, Controller Controller, Habitat habitat) throws InterruptedException {
        Font CS = new Font("Comic Sans MS Italic", 21.0);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button BtnStop = new Button("ОК");
        BtnStop.setLayoutX(300);
        BtnStop.setLayoutY(450);
        Button BtnContinue = new Button("Отмена");
        BtnContinue.setLayoutX(350);
        BtnContinue.setLayoutY(450);
        habitat.StopThreads();
        BtnStop.setOnAction(event -> {
            try {
                Controller.exit();
                window.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        BtnContinue.setOnAction(event -> {
            window.close();
            try {
                habitat.ContinueThreads();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Controller.continueGen();
        });
        TextArea text = new TextArea("Количество рабочих пчёл: " + habitat.getWorkerCount() + "\nКоличество трутней: " + habitat.getDroneCount() + "\nТекущее время симуляции: " + Controller.time.getCurrentTime());
        text.setEditable(false);
        text.setFont(CS);
        text.setMaxHeight(120);
        text.setMaxWidth(400);
        text.setLayoutX(180);
        text.setLayoutY(160);
        pane.getChildren().addAll(BtnStop);
        pane.getChildren().addAll(BtnContinue);
        pane.getChildren().addAll(text);
        Scene scene = new Scene(pane, 700, 500);
        window.setScene(scene);
        window.setTitle(Name);
        window.setResizable(false);
        window.showAndWait();
    }
    public static Habitat HelloWindow(String Name, Controller controller) {
        int b = 1;
        Habitat habitat = new Habitat(-1, 5);
        boolean check = false;
        Font CS = new Font("Comic Sans MS Italic", 12.0);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button BtnOK = new Button("ОК");
        BtnOK.setLayoutX(300);
        BtnOK.setLayoutY(450);
        Button BtnLoad = new Button("Загрузить");
        BtnLoad.setLayoutX(350);
        BtnLoad.setLayoutY(450);
        BtnOK.setOnAction(event -> window.close());
        Habitat finalHabitat = habitat;
        BtnLoad.setOnAction(event -> {
            window.close();
            setSettings(controller, finalHabitat);
        });
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
        TextArea varA = new TextArea("Введите интервал появления пчел.\nВыберите шанс их появления.");
        varA.setPrefHeight(50);
        varA.setPrefWidth(280);
        varA.setLayoutX(180);
        varA.setLayoutY(350);
        TextArea varLifeTime = new TextArea("Введите время жизни пчел.");
        varLifeTime.setPrefHeight(50);
        varLifeTime.setPrefWidth(280);
        varLifeTime.setLayoutX(180);
        varLifeTime.setLayoutY(300);
        ObservableList<String> percents = FXCollections.observableArrayList("10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%");
        ComboBox<String> varB = new ComboBox<String>(percents);
        varB.setValue("90%");
        varB.setPrefHeight(15);
        varB.setPrefWidth(280);
        varB.setLayoutX(180);
        varB.setLayoutY(400);
        //
        pane.getChildren().addAll(BtnOK, text, varA, varB, varLifeTime, BtnLoad);
        Scene scene = new Scene(pane, 700, 500);
        window.setScene(scene);
        window.setTitle(Name);
        window.setResizable(false);
        window.showAndWait();
        double a = Double.parseDouble(varB.getValue().substring(0, varB.getValue().length() - 1)) / 100;
        if (habitat.getInterval() == -1) {
            if (ModalWindow.isNumericInt(varLifeTime.getText()) && Integer.parseInt(varLifeTime.getText()) > 0) {
                controller.LifeTime = Integer.parseInt(varLifeTime.getText());
            }
            if (ModalWindow.isNumericInt(varA.getText()) && Integer.parseInt(varA.getText()) > 0) {
                b = Integer.parseInt(varA.getText());
            }
            habitat = new Habitat(b, a);
        }
        return habitat;
    }
    public static void ObjShow(String Name, Controller Controller, Habitat habitat){
        Font CS = new Font("Comic Sans MS Italic", 21.0);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button BtnContinue = new Button("Ясно.");
        try {
            habitat.StopThreads();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BtnContinue.setOnAction(event -> {
            try {
                habitat.ContinueThreads();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            window.close();
        });
        TreeMap<String, String> t = habitat.getSpawnSet();
        TextArea text;
        if (t.isEmpty())
        {
            text = new TextArea("Вперёд батьки не лезь...");
        }
        else
        {
            text = new TextArea("Время рождения\t\tID пчелы\n");
        }
        for (Map.Entry<String, String> entry : t.entrySet()) {
            text.appendText(entry.getKey() + "\t\t\t\t" + entry.getValue() + "\n");
        }
        text.setEditable(false);
        text.setFont(CS);
        BtnContinue.setLayoutX(340);
        BtnContinue.setLayoutY(450);
        text.setMaxHeight(430);
        text.setMaxWidth(700);
        pane.getChildren().addAll(BtnContinue);
        pane.getChildren().addAll(text);
        Scene scene = new Scene(pane, 700, 500);
        window.setScene(scene);
        window.setTitle(Name);
        window.setResizable(false);
        window.showAndWait();
        Controller.continueGen();
    }
}

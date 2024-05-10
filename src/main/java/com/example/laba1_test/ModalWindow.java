package com.example.laba1_test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.Random;
import java.util.TreeMap;

public class ModalWindow {
    public static void ShowAlertWindow1(Habitat habitat){
        habitat.setInterval(-1);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка данных");
        alert.setHeaderText(null);
        alert.setContentText("Вы ввели неверный тип данных для времени жизни пчёл.");
        alert.showAndWait();
    }
    public static void ShowAlertWindow2(Habitat habitat){
        habitat.setInterval(-1);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка данных");
        alert.setHeaderText(null);
        alert.setContentText("Вы ввели неверный тип данных для интервала появления пчёл.");
        alert.showAndWait();
    }
    public static void ShowAlertWindow3(Habitat habitat){
        habitat.setInterval(-1);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка чтения из файла");
        alert.setHeaderText(null);
        alert.setContentText("Возможно файл был редактирован или намеренно закорапчен.");
        alert.showAndWait();
    }
    public static boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isBoolean(String str) { // Она всё ломает при чтении файла, хз
        try {
            Boolean.parseBoolean(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void setSettings(Controller controller, Habitat habitat, AnimationTimer time) {
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
            int WorkerCount = Integer.parseInt(result[5]);
            int DroneCount = Integer.parseInt(result[6]);
            if (Chance >= 0 && Chance <= 1 && Interval >= 1 && isNumericInt(result[1]) && isNumericInt(result[2]))
            {
                controller.setAIStatusWorker(AIWorker);
                controller.setAIStatusDrone(AIDrone);
                controller.setChangeLifeTime(String.valueOf(lifetime));
                controller.setLifeTime(lifetime);
                controller.setChangeInterval(String.valueOf(Interval));
                habitat.setChance(Chance);
                habitat.setInterval(Interval);
                for (AbstractObject i : habitat.getObjects()) {
                    i.allstop();
                }
                habitat.getSpawnSet().clear();
                int WK = WorkerCount;
                int DK = DroneCount;
                for (int minutes = time.getMinute(); minutes <= 10000 & WK > 0; minutes++) {
                    for (int seconds = time.getSecond(); seconds < 60 & WK > 0; seconds += 1) {
                        for (int ms = time.getMSecond(); ms < 60 & DK > 0; ms += 10) {
                            WK--;
                            Random rand = new Random();
                            Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, lifetime, Habitat.getIDSet());
                            habitat.getObjects().addLast(new_Worker);
                            String temp = minutes + ":" + seconds + ':' + ms;
                            habitat.getSpawnSet().put(temp, habitat.getObjects().getLast().getID());
                            new_Worker.start(); // Не виновен, оправдан.
                            habitat.getThreadList().add(new_Worker.everything(new_Worker));
                            habitat.getObjects().getLast().run(controller.getSceneTwo_Background(), controller, controller.getAIStatusWorker());
                        }
                    }
                }
                habitat.setWorkerCount(WorkerCount);
                for (int minutes = time.getMinute(); minutes <= 10000 & DK > 0; minutes++) {
                    for (int seconds = time.getSecond(); seconds < 60 & DK > 0; seconds ++) {
                        for (int ms = time.getMSecond(); ms < 60 & DK > 0; ms += 10)
                        {
                            DK--;
                            Random rand = new Random();
                            Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, lifetime, Habitat.getIDSet());
                            habitat.getObjects().addLast(new_Drone);
                            String temp = minutes + ":" + seconds + ':' + ms;
                            habitat.getSpawnSet().put(temp, habitat.getObjects().getLast().getID());
                            new_Drone.start();
                            habitat.getThreadList().add(new_Drone.everything(new_Drone));
                            controller.getSceneTwo_Background().getChildren().add(habitat.getObjects().getLast().getImg());
                            habitat.getObjects().getLast().run(controller.getSceneTwo_Background(), controller, controller.getAIStatusDrone());
                        }
                    }
                }
                habitat.setDroneCount(DroneCount);
            }
            else
            {
                ShowAlertWindow3(habitat);
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
        pane.getChildren().add(BtnContinue);
        pane.getChildren().addAll(text);
        Scene scene = new Scene(pane, 700, 500);
        window.setScene(scene);
        window.setTitle(Name);
        window.setResizable(false);
        window.showAndWait();
        Controller.continueGen();
    }
}

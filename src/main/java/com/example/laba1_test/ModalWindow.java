package com.example.laba1_test;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
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
    public static void setSettings(Controller controller, Habitat habitat, File file) { // Не будет блять работать с новыми потоками, переделать максимально нахуй
        try {
            FileReader reader = new FileReader(file);
            int data = reader.read();
            String str = "";
            while (data != -1)
            {
                str += (char)data;
                data = reader.read();
            }
            reader.close();
            String[] result = str.split("\n"); // Может нужен System.lineSeparator. Хз почему, но пчёлы через раз воспринимают то "\n", то системный сепаратор.
            double Chance = Double.parseDouble(result[0]);
            int Interval = Integer.parseInt(result[1]);
            int lifetime = Integer.parseInt(result[2]);
            Boolean AIWorker = Boolean.parseBoolean(result[3]);
            Boolean AIDrone = Boolean.parseBoolean(result[4]);
            int WorkerCount = Integer.parseInt(result[5]);
            int DroneCount = Integer.parseInt(result[6]);
            if (Chance > 0 && Chance <= 1 && Interval >= 1 && isNumericInt(result[1]) && isNumericInt(result[2]))
            {
                controller.setAIStatusWorker(AIWorker);
                controller.setAIStatusDrone(AIDrone);
                controller.setChangeLifeTime(String.valueOf(lifetime));
                controller.setLifeTime(lifetime);
                controller.setChangeInterval(String.valueOf(Interval));
                habitat.setChance(Chance);
                habitat.setInterval(Interval);
                habitat.getSpawnSet().clear();
                int WK = WorkerCount;
                int DK = DroneCount;
                int minutes = 0;
                int seconds = 0;
                int ms = 0;
                while (WK > 0){
                    ms = WorkerCount - WK;
                    while (ms >= 100){
                        ms -= 100;
                        seconds++;
                    }
                    while (seconds >= 60){
                        seconds -= 60;
                        minutes++;
                    }
                    Random rand = new Random();
                    Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, lifetime, Habitat.getIDSet());
                    habitat.getObjects().addLast(new_Worker);
                    String temp = minutes + ":" + seconds + ':' + ms;
                    habitat.getSpawnSet().put(temp, habitat.getObjects().getLast().getID());
                    habitat.getObjects().getLast().getImg().setFitHeight(100);
                    habitat.getObjects().getLast().getImg().setFitWidth(100);
                    controller.getSceneTwo_Background().getChildren().add(habitat.getObjects().getLast().getImg());
                    WK--;
                }
                habitat.setWorkerCount(WorkerCount);
                while (DK > 0) {
                    ms += DroneCount - DK;
                    while (ms >= 100){
                        ms -= 100;
                        seconds++;
                    }
                    while (seconds >= 60){
                        seconds -= 60;
                        minutes++;
                    }
                    Random rand = new Random();
                    Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, lifetime, Habitat.getIDSet());
                    habitat.getObjects().addLast(new_Drone);
                    String temp = minutes + ":" + seconds + ':' + ms;
                    habitat.getSpawnSet().put(temp, habitat.getObjects().getLast().getID());
                    habitat.getObjects().getLast().getImg().setFitHeight(100);
                    habitat.getObjects().getLast().getImg().setFitWidth(100);
                    controller.getSceneTwo_Background().getChildren().add(habitat.getObjects().getLast().getImg());
                    DK--;
                    }
                habitat.setDroneCount(DroneCount);
                controller.start();
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
        catch (NumberFormatException e) {
            ShowAlertWindow3(habitat);
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
        BtnStop.setOnAction(event -> {
            try {
                Controller.exit();
                window.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        BtnContinue.setOnAction(event -> {
            window.close();
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
    public static void ObjShow(String Name, Controller Controller, Habitat habitat) throws IOException, InterruptedException {
        Font CS = new Font("Comic Sans MS Italic", 21.0);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        Button BtnContinue = new Button("Ясно.");
        Controller.StopThreads();
        BtnContinue.setOnAction(event -> {
            Controller.continueGen();
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
    public static void SaveNotify(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сохранение пчёл");
        alert.setHeaderText(null);
        alert.setContentText("Пчёлы загоняются в улей для дальнейшей эксплуатации.");
        alert.showAndWait();
    }
}

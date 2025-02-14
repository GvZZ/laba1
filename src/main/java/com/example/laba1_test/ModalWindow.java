package com.example.laba1_test;
import java.io.*;

import javafx.animation.PathTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.Socket;
import java.nio.file.Paths;
import java.util.*;

public class ModalWindow implements Serializable {
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
        alert.setContentText("Возможно файл был пустой/ редактирован или намеренно закорапчен.");
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
    public static void setSettings(Controller controller, Habitat habitat, File file, File fileSer) { // Не будет блять работать с новыми потоками, переделать максимально нахуй
        try { // Выгрузка параметров хабитата через пропы
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream(file.getPath()));
            double Chance = Double.parseDouble(prop.getProperty("Spawn Chance"));
            int Interval = Integer.parseInt(prop.getProperty("Interval"));
            int lifetime = Integer.parseInt(prop.getProperty("Life Time"));
            Boolean AIWorker = Boolean.parseBoolean(prop.getProperty("AI Status Worker"));
            Boolean AIDrone = Boolean.parseBoolean(prop.getProperty("AI Status Drone"));
            if ((Chance > 0) && (Chance <= 1) && (Interval >= 1)) {
                System.out.println("Вроде работает");
                controller.setAIStatusWorker(AIWorker);
                controller.setAIStatusDrone(AIDrone);
                controller.setChangeLifeTime(String.valueOf(lifetime));
                controller.setLifeTime(lifetime);
                controller.setChangeInterval(String.valueOf(Interval));
                habitat.setChance(Chance);
                habitat.setInterval(Interval);
                habitat.getSpawnSet().clear();
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {throw new RuntimeException(e);}

        //Десериализация habitat.objects

        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(fileSer.getName()));
            habitat.setObjects(objIn.readObject());
            objIn.close();
        }
        catch (FileNotFoundException e) {}
        catch (IOException | ClassNotFoundException e) {throw new RuntimeException(e);}
        if (habitat.getObjects() == null || habitat.getObjects().isEmpty()) {
            ShowAlertWindow3(habitat);
            System.exit(0);
        }
        int ms = 0;
        int sec = 0;
        int minutes = 0;
        int DroneCount = 0;
        int WorkerCount = 0;
        for (int i = 0; i < habitat.objects.size(); i++) {
            PathTransition pt = new PathTransition();
            ms++;
            while (ms >= 100)
            {
                sec++;
                ms -= 100;
            }
            while (sec >= 60)
            {
                minutes++;
                sec -= 60;
            }
            if (habitat.objects.get(i) instanceof Drone) {
                Image DroneIMG = new Image("IMGDrone.png");
                String temp = minutes + ":" + sec + ":" + ms;
                habitat.getObjects().get(i).SetID(habitat.getIDSet());
                habitat.getSpawnSet().put(temp, habitat.getObjects().get(i).getID());
                habitat.getObjects().get(i).setImg(new ImageView(DroneIMG));
                habitat.getObjects().get(i).getImg().setFitHeight(100);
                habitat.getObjects().get(i).getImg().setFitWidth(100);
                habitat.getObjects().get(i).setpathTransition(pt);
                habitat.getObjects().get(i).getPathTransition().pause();
                /*controller.getSceneTwo_Background().getChildren().add(habitat.getObjects().get(i).getImg());*/
                DroneCount++;
                continue;
            }
            if (habitat.objects.get(i) instanceof Worker) {
                Image WorkerIMG = new Image("IMGWorker.png");
                String temp = minutes + ":" + sec + ":" + ms;
                habitat.getObjects().get(i).SetID(habitat.getIDSet());
                habitat.getSpawnSet().put(temp, habitat.getObjects().get(i).getID());
                habitat.getObjects().get(i).setImg(new ImageView(WorkerIMG));
                habitat.getObjects().get(i).getImg().setFitHeight(100);
                habitat.getObjects().get(i).getImg().setFitWidth(100);
                habitat.getObjects().get(i).setpathTransition(pt);
                habitat.getObjects().get(i).getPathTransition().pause();
                /*controller.getSceneTwo_Background().getChildren().add(habitat.getObjects().get(i).getImg());*/
                WorkerCount++;
            }
        }
        habitat.setWorkerCount(WorkerCount);
        habitat.setDroneCount(DroneCount);

        controller.start();
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
        alert.setHeaderText("Пчёлы загоняются в улей для дальнейшей эксплуатации.");
        alert.setContentText("Сначала выберите файл с расширением .xml для общего статуса программы. Затем выберите файл с расширением .ser для сохранения состояния пчёл");
        alert.showAndWait();
    }
}

package com.example.laba1_test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.stage.WindowEvent;

public class Main extends Application implements Serializable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8081);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Подключение к серверу установлено.");
            getClientsList();
            new Thread(this::listenForServerMessages).start();
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к серверу: " + e.getMessage());
        }
    }
    private void listenForServerMessages() {
        try {
            while (true) {
                // Чтение данных от сервера
                Object response = in.readObject();
                if (response instanceof ArrayList) {
                    // Если получен список клиентов
                    ArrayList<Integer> clientInfos = (ArrayList<Integer>) response;
                    Platform.runLater(() -> {
                        System.out.println("Список подключенных клиентов:");
                        for (Integer clientInfo : clientInfos) {
                            System.out.println(clientInfo.toString());
                        }
                    });
                } else {
                    // Если получено другое сообщение
                    Platform.runLater(() -> System.out.println("Сообщение от сервера: " + response));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при чтении данных от сервера: " + e.getMessage());
        }
    }
    private void getClientsList() {
        try {
            // Отправляем запрос на получение списка клиентов
            out.writeObject("getClients");
            out.flush();

            // Получаем список клиентов
            ArrayList<Integer> ports = (ArrayList<Integer>) in.readObject();
            System.out.println("Список подключенных клиентов:");
            for (Integer port : ports) {
                System.out.println(port.toString());
            }
            System.out.println('\n');
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при получении списка клиентов: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        music();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        connectToServer();
        stage.setOnCloseRequest(new EventHandler<>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        // Отправляем команду на отключение
                        out.writeObject("exit");
                        out.flush();
                        System.exit(0);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case T:
                        controller.Hide_Show();
                        break;
                    case E:
                        try {
                            controller.exit();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case B:
                        controller.start();
                        break;
                    case ESCAPE:
                        System.exit(0);
                        break;
                }
            }
        });
        stage.setTitle("Пчелиная возня");
        stage.show();

    }
    MediaPlayer mediaPlayer;
    public void music(){
        String s = "src/main/resources/BeeMusic.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setVolume(0.00);
        mediaPlayer.play();
    }
    public static void main(String[] args) {launch(); }
}
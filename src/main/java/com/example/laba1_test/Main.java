package com.example.laba1_test;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.scene.media.Media;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        music();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
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
        stage.setScene(scene);
        stage.show();
    }
    MediaPlayer mediaPlayer;
    public void music(){
        String s = "src/main/resources/BeeMusic.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setVolume(0.08);
        mediaPlayer.play();
    }
        public static void main(String[] args) {
            launch();
        }
}
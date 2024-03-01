package com.example.laba1_test;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
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
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case B:
                        controller.start();
                        break;
                }

            }
        });
        stage.setTitle("Симулятор пчёл");
        stage.setScene(scene);
        stage.show();
    }
        public static void main(String[] args) {
            launch();
        }
}



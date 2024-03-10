package com.example.laba1_test;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.HashSet;
public class Worker extends AbstractObject{
    double speed = 20;
    Thread Worker_thread;
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }

    public void run(AnchorPane pane, Controller controller, double x, double y){
        Image image = new Image("IMGWorker.png");
        ImageView imgview = new ImageView(image);
        pane.getChildren().addAll(imgview);
        if (x == BirthX && y == BirthY)
        {
            System.out.println("Пчела пошла");
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(imgview);
            transition.setDuration(Duration.millis(10000));
            transition.setByX(200);
            transition.setByY(200);
            transition.setCycleCount(1);
            transition.play();
        }
    }
    public Thread everything(AbstractObject x){
        Worker_thread = new Thread(x);
        /*Worker_thread.start();*/
        return Worker_thread;
    }
}
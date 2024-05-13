package com.example.laba1_test;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Random;

public class DroneAI extends BaseAI{
    PathTransition pathTransition = new PathTransition();
    ImageView img;
    double x;
    double y;
    double speed = 10;
    AnchorPane pane;
    Controller controller;
    Boolean Status;
    public DroneAI(AnchorPane pane, Controller controller, Boolean Status){
        this.pane = pane;
        this.controller = controller;
        this.Status = Status;
        this.x = Math.random() * (1000 + 1);
        this.y = Math.random() * (800 + 1);

    }

    @Override
    synchronized public void run(){
        while(pathTransition.getStatus() == Animation.Status.STOPPED) {
            pathTransition.setDuration(Duration.millis(speed * 1000));
            Path path = new Path();
            MoveTo moveTo = new MoveTo(img.getX(), img.getY());
            Random rand = new Random();
            double angle = rand.nextDouble(); // случайный угол
            x = (speed * Math.cos(angle)) * 100;
            y = (speed * Math.sin(angle)) * 100;
            LineTo lineTo = new LineTo(x, y);
            pathTransition.setNode(img);
            path.getElements().addAll(moveTo, lineTo);
            pane.getChildren().add(img);
            pathTransition.setCycleCount(1);
            pathTransition.setPath(path);
            pathTransition.play();
            if (!Status){
                pathTransition.pause();
            }
            img.setX(x);
            img.setY(y);
        }
    }
    @Override
    public void everything(AbstractObject x){
        img.setFitHeight(100);
        img.setFitWidth(100);
    }
    @Override
    public void allstop(){
        this.pathTransition.setNode(null);
        this.img.setImage(null);
    }
    public void setController(Controller controller){this.controller = controller;}
    public void setStatus(Boolean Status){this.Status = Status;}
    public ImageView getImg(){return this.img;}
    public void setImg(ImageView img){this.img = img;}
    public AnchorPane getPane(){return this.pane;}

}

package com.example.laba1_test;

import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Random;

public class WorkerAI extends BaseAI{
    PathTransition pathTransition = new PathTransition();
    ImageView img;
    double BirthX;
    double BirthY;
    double speed = 10;
    AnchorPane pane;
    Controller controller;
    Boolean Status;
    public WorkerAI(AnchorPane pane, Controller controller, Boolean Status){
        Random rand = new Random();
        this.pane = pane;
        this.controller = controller;
        this.Status = Status;
        BirthX = 0.0 + (Math.random() * (1200 + 1));
        System.out.println(BirthX);
        BirthY = 0.0 + (Math.random() * (800 + 1));
        System.out.println(BirthY);

    }
    @Override
    synchronized public void run(){
        pathTransition.setDuration(Duration.millis(speed * 100));
        Path path = new Path();
        MoveTo moveTo = new MoveTo(BirthX, BirthY);
        LineTo lineTo = new LineTo(1486 - img.getFitWidth() / 2, 1000 - img.getFitHeight() / 2);
        pathTransition.setNode(img);
        path.getElements().addAll(moveTo, lineTo);
        pane.getChildren().add(img);
        pathTransition.setCycleCount(-1);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);
        pathTransition.play();
        if (!Status){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
}

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
    double speed = 10;
    Thread Worker_thread;
    PathTransition pathTransition = new PathTransition();
    ImageView img;
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Worker(){}

    public void run(AnchorPane pane, Controller controller, ImageView imgview, int Status){
        this.img = imgview;
        pathTransition.setDuration(Duration.millis(10000));
        Path path = new Path();
        MoveTo moveTo = new MoveTo(BirthX, BirthY);
        LineTo lineTo = new LineTo(1486 - imgview.getFitWidth() / 2, 1000 - imgview.getFitHeight() / 2);
        pathTransition.setNode(img);
        path.getElements().addAll(moveTo, lineTo);
        pane.getChildren().add(img);
        pathTransition.setCycleCount(5);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);
        if (Status == 1)
        {
            pathTransition.play();
        }
    }
    @Override
    public void allstop(){
        this.pathTransition.setNode(null);
        this.Worker_thread.interrupt();
        this.img.setImage(null);
    }
    public Thread everything(AbstractObject x){
        Worker_thread = new Thread(x);
        /*Worker_thread.start();*/
        return Worker_thread;
    }
    public void StopTransition() {
        this.pathTransition.pause();
    }
    public void ContinueTransition(){
        this.pathTransition.play();
    }
}
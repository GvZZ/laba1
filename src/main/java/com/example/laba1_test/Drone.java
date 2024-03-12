package com.example.laba1_test;


import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Random;

public class Drone extends AbstractObject{
    double speed = 10;
    Thread Drone_thread;
    PathTransition pathTransition = new PathTransition();
    ImageView img;
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Drone(){}
    @Override
    public void run(AnchorPane pane, Controller controller, ImageView imgview, int Status){
        this.img = imgview;
        pathTransition.setDuration(Duration.millis(speed * 100));
        Path path = new Path();
        MoveTo moveTo = new MoveTo(BirthX, BirthY);
        Random rand = new Random();
        double angle = rand.nextDouble(); // случайный угол
        x = (speed * Math.cos(angle)) * 100;
        y = (speed * Math.sin(angle)) * 100;
        LineTo lineTo = new LineTo(x, y);
        pathTransition.setNode(img);
        path.getElements().addAll(moveTo, lineTo);
        pane.getChildren().add(img);
        pathTransition.setCycleCount(pathTransition.INDEFINITE);
        pathTransition.setPath(path);
        if (Status == 1)
        {
            pathTransition.play();
        }
    }
    @Override
    public void allstop(){
        this.pathTransition.setNode(null);
        this.Drone_thread.interrupt();
        this.img.setImage(null);
    }
    public Thread everything(AbstractObject x){
        Drone_thread = new Thread(x);
        return Drone_thread;
    }
    public void StopTransition(){this.pathTransition.pause();}
    public void ContinueTransition(){this.pathTransition.play();}
}

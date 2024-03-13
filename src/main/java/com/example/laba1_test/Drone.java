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
    public void run(AnchorPane pane, Controller controller, Boolean Status){
        while(pathTransition.getStatus() == Animation.Status.STOPPED) {
            pathTransition.setDuration(Duration.millis(speed * 100));
            Path path = new Path();
            MoveTo moveTo = new MoveTo(img.getX(), img.getY());
            Random rand = new Random();
            double angle = rand.nextDouble(); // случайный угол
            x = (speed * Math.cos(angle)) * 100;
            y = (speed * Math.sin(angle)) * 100;
            LineTo lineTo = new LineTo(x, y);
            pathTransition.setNode(img);
            path.getElements().addAll(moveTo, lineTo);
            pathTransition.setCycleCount(1);
            pathTransition.setPath(path);
            if (Status) {
                pathTransition.play();
            }
            img.setX(x);
            img.setY(y);
        }
    }
    @Override
    public void allstop(){
        this.pathTransition.setNode(null);
        this.Drone_thread.interrupt();
        this.img.setImage(null);
    }
    public Thread everything(AbstractObject x){
        Image image = new Image("IMGDrone.png");
        ImageView imgv = new ImageView(image);
        imgv.setX(BirthX);
        imgv.setY(BirthY);
        imgv.setFitHeight(100);
        imgv.setFitWidth(100);
        this.img = imgv;
        Drone_thread = new Thread(x);
        return Drone_thread;
    }
    public ImageView getImg(){
        return this.img;
    }
    public void StopTransition(){this.pathTransition.pause();}
    public void ContinueTransition(){this.pathTransition.play();}
}

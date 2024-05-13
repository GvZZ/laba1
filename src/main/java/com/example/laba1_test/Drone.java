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
    ImageView img = new ImageView(new Image("IMGDrone.png"));
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Drone(){}
    public ImageView getImg(){
        return this.img;
    }
    public void StopTransition(){this.pathTransition.pause();}
    public void ContinueTransition(){this.pathTransition.play();}
}

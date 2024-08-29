package com.example.laba1_test;


import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;

public class Drone extends AbstractObject{
    PathTransition pathTransition = new PathTransition();
    ImageView img = new ImageView(new Image("IMGDrone.png"));
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public ImageView getImg(){
        return this.img;
    }
    public void StopTransition(){this.pathTransition.pause();}
    public void ContinueTransition(){this.pathTransition.play();}
    public PathTransition getPathTransition() {return pathTransition;}}

package com.example.laba1_test;


import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;

public class Worker extends AbstractObject{
    PathTransition pathTransition = new PathTransition();
    ImageView img = new ImageView(new Image("IMGWorker.png"));
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Worker(){}
    public ImageView getImg(){
        return this.img;
    }
    public void StopTransition() {
        this.pathTransition.pause();
    }
    public void ContinueTransition(){
        this.pathTransition.play();
    }
}
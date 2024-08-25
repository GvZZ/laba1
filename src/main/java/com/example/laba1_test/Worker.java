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
    PathTransition pathTransition = new PathTransition();
    ImageView img = new ImageView(new Image("IMGWorker.png"));
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Worker(){}

    @Override
    public void allstop(){
        this.img.setImage(null);
    }
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
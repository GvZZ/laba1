package com.example.laba1_test;


import javafx.animation.PathTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class BaseAI extends Thread implements Runnable{
    public void run(){

    }
    public void run(AnchorPane pane, Controller controller, Boolean s){
    }
    public void StopTransition() throws InterruptedException {}
}

package com.example.laba1_test;


import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.HashSet;

public class Worker extends AbstractObject{
    double x;
    double y;
    private static final long serialVersionUID = 1L;
    transient PathTransition pathTransition = new PathTransition();
    transient ImageView img = new ImageView(new Image("IMGWorker.png"));
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public void refreshCords() {
        if (this.pathTransition.getNode() != null)
        {
            this.x = img.getX(); this.y = img.getY();
        }
    }
    public void setImg(ImageView img){this.img = img; }
    public double getX() {return x;}
    public double getY() {return y;}
    public Worker(){}
    public ImageView getImg(){
        return this.img;
    }
    public PathTransition getPathTransition() {return pathTransition;}
    public void setpathTransition(PathTransition pathTransition) {this.pathTransition = pathTransition;}
}
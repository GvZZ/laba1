package com.example.laba1_test;


import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.HashSet;

public class Drone extends AbstractObject implements Serializable {
    double x;
    double y;
    private static final long serialVersionUID = 1L;
    transient PathTransition pathTransition = new PathTransition();
    transient ImageView img = new ImageView(new Image("IMGDrone.png"));
    public Drone(double x, double y, int LifeT, HashSet<String> Set) {
        super(x, y, LifeT, Set);
    }
    public Drone(){}
    public ImageView getImg(){
        return this.img;
    }
    public void setImg(ImageView img){this.img = img; }
    public void refreshCords() {
        if (this.pathTransition.getNode() != null)
        {
            this.x = img.getX(); this.y = img.getY();
        }
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public PathTransition getPathTransition() {return pathTransition;}
    public void setpathTransition(PathTransition pathTransition) {this.pathTransition = pathTransition;}
}

package com.example.laba1_test;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public abstract class AbstractObject extends BaseAI implements Serializable {
    private static final long serialVersionUID = 1L;
    double x;
    double y;
    double BirthX;
    double BirthY;
    int LifeTime;
    transient ImageView img = new ImageView();
    String ID;
    transient PathTransition pathTransition = new PathTransition();
    public AbstractObject(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        this.x = initialX;
        this.y = initialY;
        BirthX = x;
        BirthY = y;
        this.LifeTime = LifeT;
        SetID(Set);
    }
    public AbstractObject(){}
    public void SetID(HashSet<String> Set)
    {
        int prevSize = Set.size();
        while (prevSize == Set.size())
        {
            Random rand = new Random();
            int a = rand.nextInt();
            String StringID = Integer.toBinaryString(a);
            ID = StringID;
            Set.add(StringID);
        }
    }
    public void allstop(){
        pathTransition.setNode(null);
    }
    public void refreshCords() {
        if (this.pathTransition.getNode() != null)
        {
            this.x = img.getX(); this.y = img.getY();
        }
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public ImageView getImg(){
        return img;
    }
    public int getLifeTime() {return LifeTime;}
    public String getID() {return ID;}
    public void setImg(ImageView x){this.img = x;}
    public PathTransition getPathTransition(){return this.pathTransition;}
    public void setpathTransition(PathTransition pathTransition) {this.pathTransition = pathTransition;}
    @Override
    public void run()
    {

    }
}
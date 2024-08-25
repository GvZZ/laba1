package com.example.laba1_test;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public abstract class AbstractObject extends BaseAI{
    double x;
    double y;
    double BirthX;
    double BirthY;
    int LifeTime;
    ImageView img;
    String ID;
    PathTransition pathTransition = new PathTransition();
    public AbstractObject(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        this.x = initialX;
        this.y = initialY;
        BirthX = x;
        BirthY = y;
        this.LifeTime = LifeT;
        SetID(Set);
    }
    public AbstractObject(){}
    public boolean VibeChecker(){
        return pathTransition.getStatus() == Animation.Status.RUNNING;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
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
    }
    public ImageView getImg(){
        return img;
    }
    public int getLifeTime() {return LifeTime;}
    public String getID() {return ID;}
    public void StopTransition(){}
    public void ContinueTransition(){}
    public void setImg(ImageView x){this.img = x;}
    public PathTransition getPathTransition(){return this.pathTransition;}
    @Override
    public void run()
    {

    }
}
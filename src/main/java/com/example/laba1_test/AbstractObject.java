package com.example.laba1_test;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public abstract class AbstractObject extends BaseAI{
    double x;
    double y;
    double BirthX;
    double BirthY;
    int LifeTime;
    String ID;
    public AbstractObject(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        this.x = initialX;
        this.y = initialY;
        BirthX = x;
        BirthY = y;
        this.LifeTime = LifeT;
        SetID(Set);
    }
    public AbstractObject(){}
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
    public int getLifeTime() {return LifeTime;}
    public String getID() {return ID;}
    public void StopTransition(){}
    public void ContinueTransition(){}
}
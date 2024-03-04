package com.example.laba1_test;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public abstract class AbstractObject {
    private double x;
    private double y;
    private int LifeTime;
    private String ID;

    public AbstractObject(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        this.x = initialX;
        this.y = initialY;
        this.LifeTime = LifeT;
        SetID(Set);

    }
    public abstract void move(double elapsedTime);
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
            Set.add(StringID);
        }
    }
    public int getLifeTime() {return LifeTime;}
    public String getID() {return ID;}

    // Примерная реализация метода move для всех объектов
    protected void moveRandomly(double distance) {
        Random rand = new Random();
        double angle = rand.nextDouble() * 2 * Math.PI; // случайный угол
        double deltaX = distance * Math.cos(angle);
        double deltaY = distance * Math.sin(angle);

        // Перемещаем объект на новые координаты
        x += deltaX;
        y += deltaY;
    }
}
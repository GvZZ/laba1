package com.example.laba1_test;
import java.util.Random;

public abstract class AbstractObject {
    private double x;
    private double y;

    public AbstractObject(double initialX, double initialY) {
        this.x = initialX;
        this.y = initialY;
    }

    public abstract void move(double elapsedTime);

    // Общие методы для получения координат
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

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
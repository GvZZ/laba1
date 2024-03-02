package com.example.laba1_test;


public class Worker extends AbstractObject {
    public Worker(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для юридических лиц
        moveRandomly(20);
    }
}
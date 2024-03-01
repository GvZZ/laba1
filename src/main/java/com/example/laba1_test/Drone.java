package com.example.laba1_test;


public class Drone extends AbstractObject implements IBehaviour {
    public Drone(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для физических лиц
        moveRandomly(20);
    }
}

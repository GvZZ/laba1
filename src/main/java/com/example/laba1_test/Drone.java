package com.example.laba1_test;


public class Drone extends AbstractObject{
    public Drone(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        moveRandomly(20);
    }
}

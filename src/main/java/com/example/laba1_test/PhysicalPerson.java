package com.example.laba1_test;


public class PhysicalPerson extends AbstractObject {
    public PhysicalPerson(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        moveRandomly(100);
    }
}

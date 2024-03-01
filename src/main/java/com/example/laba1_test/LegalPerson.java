package com.example.laba1_test;


public class LegalPerson extends AbstractObject {
    public LegalPerson(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        moveRandomly(10);
    }
}
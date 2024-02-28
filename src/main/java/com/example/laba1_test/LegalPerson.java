package com.example.laba1_test;


public class LegalPerson extends AbstractObject implements IBehaviour {
    public LegalPerson(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для юридических лиц
        moveRandomly(5);
    }
}
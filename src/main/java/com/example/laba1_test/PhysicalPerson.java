package com.example.laba1_test;


public class PhysicalPerson extends AbstractObject implements IBehaviour {
    public PhysicalPerson(double initialX, double initialY) {
        super(initialX, initialY);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для физических лиц
        moveRandomly(10);
    }
}

package com.example.laba1_test;


import java.util.HashSet;

public class Drone extends AbstractObject{
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для физических лиц
        moveRandomly(20);
    }
}

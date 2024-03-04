package com.example.laba1_test;


import java.util.HashSet;

public class Worker extends AbstractObject{
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }

    @Override
    public void move(double elapsedTime) {
        // Реализация движения для юридических лиц
        moveRandomly(20);
    }
}
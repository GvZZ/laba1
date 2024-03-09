package com.example.laba1_test;


import java.util.HashSet;
import java.util.Random;

public class Drone extends AbstractObject{
    double speed = 20;
    Thread Drone_thread;
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    @Override
    public void run(){
        while (true)
        {
            Random rand = new Random();
            double angle = rand.nextDouble() * 2 * Math.PI; // случайный угол
            x += (speed * Math.cos(angle)) / 100;
            y += (speed * Math.sin(angle)) / 100;
            // Перемещаем объект на новые координаты
            while (x > 1386 || x < 0 || y < 0 || y > 900) {
                x -= (speed * Math.cos(angle)) / 100;
                y -= (speed * Math.sin(angle)) / 100;
                rand = new Random();
                angle = rand.nextDouble() * 2 * Math.PI; // случайный угол
                x += (speed * Math.cos(angle)) / 100;
                y += (speed * Math.sin(angle)) / 100;
            }
        }
    }
    public Thread everything(AbstractObject x){
        Drone_thread = new Thread(x);
        Drone_thread.start();
        return Drone_thread;
    }
}

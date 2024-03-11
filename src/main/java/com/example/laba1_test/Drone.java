package com.example.laba1_test;


import java.util.HashSet;
import java.util.Random;

public class Drone extends AbstractObject{
    double speed = 20;
    Thread Drone_thread;
    public Drone(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }
    public Drone(){}
    @Override
    public void run(){
        while (true)
        {
            Random rand = new Random();
            double angle = rand.nextDouble() * 2 * Math.PI; // случайный угол
            x += (speed * Math.cos(angle)) / 5000;
            y += (speed * Math.sin(angle)) / 5000;
            // Перемещаем объект на новые координаты
            while (x > 1386 || x < 0 || y < 0 || y > 900) {
                x -= (speed * Math.cos(angle)) / 5000;
                y -= (speed * Math.sin(angle)) / 5000;
                rand = new Random();
                angle = rand.nextDouble() * 2 * Math.PI; // случайный угол
                x += (speed * Math.cos(angle)) / 5000;
                y += (speed * Math.sin(angle)) / 5000;
            }
        }
    }
    @Override
    public void allstop(){
        this.Drone_thread.interrupt();
    }
    public Thread everything(AbstractObject x){
        Drone_thread = new Thread(x);
        Drone_thread.start();
        return Drone_thread;
    }
    public void ContinueTransition(){
        Drone_thread.notify();
    }
    public void StopTransition(){
        try {
            Drone_thread.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

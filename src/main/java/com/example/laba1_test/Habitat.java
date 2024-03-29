package com.example.laba1_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Habitat {
    private static final int K = 30;
    private static final int N2 =1; // интервал для рабочих в секундах
    private static final double P2 = 1; // вероятность спавна рабочих

    private List<AbstractObject> objects;
    private int DroneCount;
    private int WorkerCount;
    public Habitat() {
        objects = new ArrayList<>();
        DroneCount = 0;
        WorkerCount = 0;
    }

    public void update(int second) {
        Random rand = new Random();
        if ((rand.nextDouble() < P2) && (second % N2 == 0)) {
            objects.add(new Worker(rand.nextDouble() * 500, rand.nextDouble() * 500));
            WorkerCount++;
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            objects.add(new Drone(rand.nextDouble() * 500, rand.nextDouble() * 500));
            DroneCount++;
        }
        for (AbstractObject obj : objects) {
            obj.move(second);
        }
    }
    public int getDroneCount() {
        return DroneCount;
    }
    public int getWorkerCount() {
        return WorkerCount;
    }
    public List<AbstractObject> getObjects() {
        return objects;
    }

}

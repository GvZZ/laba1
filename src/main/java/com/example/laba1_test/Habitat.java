package com.example.laba1_test;

import java.util.*;

public class Habitat {
    private static final int K = 30;
    private int N = 1; // интервал для рабочих в секундах
    private double P = 0.9; // вероятность спавна рабочих

    private ArrayList<AbstractObject> objects;
    private HashSet<String> IDSet;
    private TreeMap<AbstractObject, String> SpawnSet;
    private int DroneCount;
    private int WorkerCount;
    public Habitat(int a, double b) {
        SpawnSet = new TreeMap<AbstractObject, String>();
        IDSet = new HashSet<String>();
        objects = new ArrayList<>();
        DroneCount = 0;
        WorkerCount = 0;
        this.N = a;
        this.P = b;

    }

    public void update(int second, AnimationTimer time, int LifeT) {
        Random rand = new Random();
        if ((rand.nextDouble() < P) && (second % N == 0)) {
            objects.add(new Worker(rand.nextDouble() * 500, rand.nextDouble() * 500, LifeT, IDSet));
            SpawnSet.put(objects.getLast(), time.getCurrentTime());
            WorkerCount++;
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            objects.add(new Drone(rand.nextDouble() * 500, rand.nextDouble() * 500, LifeT, IDSet));
            SpawnSet.put(objects.getLast(), time.getCurrentTime());
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
    public ArrayList<AbstractObject> getObjects() {
        return objects;
    }
    public HashSet<String> getIDSet() {return IDSet;}
    public TreeMap<AbstractObject, String> getSpawnSet() {return SpawnSet;}
}

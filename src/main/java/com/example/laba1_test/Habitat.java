package com.example.laba1_test;

import java.util.*;

import static java.lang.Math.abs;

public class Habitat {
    private static final int K = 30;
    private int N = 1; // интервал для рабочих в секундах
    private double P = 0.9; // вероятность спавна рабочих

    private ArrayList<AbstractObject> objects;
    private HashSet<String> IDSet;
    private TreeMap<String, String> SpawnSet;
    private int DroneCount;
    private int WorkerCount;
    public Habitat(int a, double b) {
        SpawnSet = new TreeMap<String, String>();
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
            objects.addLast(new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet));
            SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
            WorkerCount++;
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            objects.addLast(new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet));
            String doptime = time.Minute + ":" + time.Second + ":" + 15;
            SpawnSet.put(doptime, objects.getLast().getID());
            DroneCount++;
        }
        String[] vremya = time.getCurrentTime().split(":");
        int min = Integer.parseInt(vremya[0]);
        int sec = Integer.parseInt(vremya[1]);
        int ms = Integer.parseInt(vremya[2]);
        int finmin = min;
        int finsec = sec - objects.getFirst().getLifeTime();
        if (finsec < 0)
        {
            finsec = 60 - objects.getFirst().getLifeTime();
            finmin--;
        }
        if (finmin >= 0)
        {
            String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", ms);
            if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
            {
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.remove(objects.getFirst());
            }
            fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
            if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
            {
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.remove(objects.getFirst());
            }
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
    public TreeMap<String, String> getSpawnSet() {return SpawnSet;}
}

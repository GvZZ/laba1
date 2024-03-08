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
            String doptime = time.Minute + ":" + time.Second + ":" + abs(rand.nextInt() % 20) + 1;
            SpawnSet.putLast(doptime, objects.getLast().getID());
            DroneCount++;
        }
        String[] vremya = SpawnSet.firstKey().split(":");
        int min = Integer.parseInt(vremya[0]);
        int sec = Integer.parseInt(vremya[1]);
        int ms = Integer.parseInt(vremya[2]);
        int finmin = min;
        int finsec = sec + 5;
        if (finsec >= 60)
        {
            finsec %= 60;
            finmin++;
        }
        String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", ms);
        System.out.println(fintime);
        while (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
        {
            IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
            SpawnSet.remove(fintime);
            objects.remove(objects.getFirst());
            vremya = SpawnSet.firstKey().split(":");
            min = Integer.parseInt(vremya[0]);
            sec = Integer.parseInt(vremya[1]);
            ms = Integer.parseInt(vremya[2]);
            finmin = min;
            finsec = sec + 5;
            if (finsec >= 60)
            {
                finsec %= 60;
                finmin++;
            }
            fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", ms);
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

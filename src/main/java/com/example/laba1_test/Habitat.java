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
            SpawnSet.put(doptime, objects.getLast().getID());
            DroneCount++;
        }
        String[] vremya = SpawnSet.firstKey().split(":");
        int min = Integer.parseInt(vremya[0]);
        int sec = Integer.parseInt(vremya[1]);
        int ms = Integer.parseInt(vremya[2]);
        while (LifeT <= ((second + time.Minute * 60) - (min * 60 + sec)))
        {
            objects.remove(objects.getFirst());
            System.out.println(SpawnSet.firstKey());
            SpawnSet.remove(SpawnSet.firstKey());
            Iterator<String> iterator = IDSet.iterator();
            if (iterator.hasNext())
            {
                iterator.next();
                iterator.remove();
            }
            vremya = SpawnSet.firstKey().split(":");
            min = Integer.parseInt(vremya[0]);
            sec = Integer.parseInt(vremya[1]);
            ms = Integer.parseInt(vremya[2]);
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

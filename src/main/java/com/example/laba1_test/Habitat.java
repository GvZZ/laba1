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
    private ArrayList<Thread> ThreadList;
    private int DroneCount;
    private int WorkerCount;
    public Habitat(int a, double b) {
        SpawnSet = new TreeMap<String, String>();
        IDSet = new HashSet<String>();
        objects = new ArrayList<>();
        DroneCount = 0;
        WorkerCount = 0;
        ThreadList = new ArrayList<Thread>();
        this.N = a;
        this.P = b;

    }

    public void update(int second, AnimationTimer time, int LifeT) {
        Random rand = new Random();
        if ((rand.nextDouble() < P) && (second % N == 0)) {
            Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Worker);
            SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
            WorkerCount++;
            ThreadList.add(new_Worker.everything(new_Worker));
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Drone);
            String doptime = time.Minute + ":" + time.Second + ":" + 15;
            SpawnSet.put(doptime, objects.getLast().getID());
            DroneCount++;
            ThreadList.add(new_Drone.everything(new_Drone));
        }
        if (!objects.isEmpty())
        {
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
            if (finsec >= 60)
            {
                finsec -= 60;
                finmin++;
            }
            if (finmin >= 0) {
                String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", ms);
                if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
                {
                    IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                    SpawnSet.remove(fintime);
                    objects.remove(objects.getFirst());
                    System.out.println("Ликвидирован");
                }
                fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
                if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
                {
                    IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                    SpawnSet.remove(fintime);
                    objects.remove(objects.getFirst());
                    System.out.println("Ликвидирован");

                }
            }
        }
        /*for (Thread obj : ThreadList) {
            if (!obj.isAlive()) {
                System.out.println("Поток повис...");
                obj.start();
            }
        }*/
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

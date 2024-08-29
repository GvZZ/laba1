package com.example.laba1_test;

import javafx.scene.layout.AnchorPane;
import java.util.*;

import static java.lang.Math.abs;

public class Habitat extends Thread implements Runnable{
    Controller controller;
    private static final int K = 30;
    private int N = 1; // интервал для рабочих в секундах
    private double P = 0.9; // вероятность спавна рабочих

    public ArrayList<AbstractObject> objects;
    private static HashSet<String> IDSet;
    private TreeMap<String, String> SpawnSet;
    private int DroneCount;
    private int WorkerCount;
    private int finmin;
    private int finsec;
    private int finms;
    Runnable Runnie = new Runnable() {
        @Override
        public void run() {
            String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", finms);
            while (SpawnSet.remove(fintime) != null)
            {
                controller.getSceneTwo_Background().getChildren().remove(objects.getFirst().getImg());
                objects.getFirst().getPathTransition().setNode(null);
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.getFirst().setImg(null);
                objects.remove(objects.getFirst());
                WorkerCount--;
            }
            fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
            while (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
            {
                controller.getSceneTwo_Background().getChildren().remove(objects.getFirst().getImg());
                objects.getFirst().getPathTransition().setNode(null);
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.getFirst().setImg(null);
                objects.remove(objects.getFirst());
                DroneCount--;
            }
            BeeDelete.interrupt();
        }
    };
    Thread BeeDelete = new Thread(Runnie);
    public Habitat(int a, double b, Controller ctr) {
        controller = ctr;
        SpawnSet = new TreeMap<String, String>();
        IDSet = new HashSet<String>();
        objects = new ArrayList<>();
        DroneCount = 0;
        WorkerCount = 0;
        this.N = a;
        this.P = b;
    }
    public void update(int second, AnimationTimer time, int LifeT, AnchorPane Scene, Controller controller) {
        if (time.getMSecond() % 100 == 0) {
            Random rand = new Random();
            if ((rand.nextDouble() < P) && (second % N == 0)) {
                Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
                objects.addLast(new_Worker);
                SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
                WorkerCount++;
            }
            if (DroneCount <= WorkerCount * K * 0.01) {
                Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
                objects.addLast(new_Drone);
                String doptime = time.Minute + ":" + time.Second + ":" + 15;
                SpawnSet.put(doptime, objects.getLast().getID());
                DroneCount++;
            }
        }
        if (!objects.isEmpty())
        {
            int min = time.getMinute();
            int sec = time.getSecond();
            this.finms = time.getMSecond();
            this.finmin = min;
            this.finsec = sec - objects.getFirst().getLifeTime();
            if (finsec < 0)
            {
                finsec =  60 - abs(finsec);
                finmin--;
                if (finmin < 0)
                {
                    finmin = 0;
                }
            }
            if (finmin >= 0) {
                Runnie.run();
            }
        }
    }
    public int getDroneCount() {
        return DroneCount;
    }
    public int getWorkerCount() {
        return WorkerCount;
    }
    public double getChance(){return P;}
    public int getInterval(){return N;}
    public void setChance(double x){P = x;}
    public void setInterval(int x){N = x;}
    public ArrayList<AbstractObject> getObjects() {
        return objects;
    }
    public static HashSet<String> getIDSet() {return IDSet;}
    public TreeMap<String, String> getSpawnSet() {return SpawnSet;}
    public void setWorkerCount(int workerCount) {this.WorkerCount = workerCount;}
    public void setDroneCount(int droneCount) {this.DroneCount = droneCount;}
}

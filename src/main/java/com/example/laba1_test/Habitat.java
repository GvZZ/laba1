package com.example.laba1_test;

import javafx.scene.layout.AnchorPane;
import java.util.*;

import static java.lang.Math.abs;

public class Habitat extends Thread implements Runnable{
    private static final int K = 30;
    private int N = 1; // интервал для рабочих в секундах
    private double P = 0.9; // вероятность спавна рабочих

    private ArrayList<AbstractObject> objects;
    private static HashSet<String> IDSet;
    private TreeMap<String, String> SpawnSet;
    private ArrayList<Thread> ThreadList;
    private int DroneCount;
    private int WorkerCount;
    private int finmin;
    private int finsec;
    private int finms;
    Thread BeeAdd = new Thread();
    Runnable Runnie = new Runnable() {
        @Override
        public void run() {
            String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", finms);
            while (SpawnSet.remove(fintime) != null)
            {
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.getFirst().interrupt();
                objects.getFirst().allstop();
                objects.remove(objects.getFirst());
                DroneCount--;
            }
            fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
            while (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
            {
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                objects.getFirst().allstop();
                objects.remove(objects.getFirst());
                WorkerCount--;
            }
            BeeDelete.interrupt();
        }
    };
    Runnable Radke = new Runnable() {
        private AbstractObject x = new AbstractObject() {};
        @Override
        public void run() {
            System.out.println(AbstractObject.currentThread());
            x.run();
        }
    };
    Thread BeeDelete = new Thread();
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
    public void update(int second, AnimationTimer time, int LifeT, AnchorPane Scene, Controller controller) {
        Random rand = new Random();
        if ((rand.nextDouble() < P) && (second % N == 0)) {
            Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Worker);
            SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
            WorkerCount++;
            new_Worker.start();
            ThreadList.add(new_Worker.everything(new_Worker));
            objects.getLast().run(Scene, controller, controller.getAIStatusWorker());
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Drone);
            String doptime = time.Minute + ":" + time.Second + ":" + 15;
            SpawnSet.put(doptime, objects.getLast().getID());
            DroneCount++;
            new_Drone.start();
            ThreadList.add(new_Drone.everything(new_Drone));
            Scene.getChildren().add(objects.getLast().getImg());
            objects.getLast().run(Scene, controller, controller.getAIStatusDrone());
        }
        if (!objects.isEmpty())
        {
            String[] vremya = time.getCurrentTime().split(":");
            int min = Integer.parseInt(vremya[0]);
            int sec = Integer.parseInt(vremya[1]);
            this.finms = Integer.parseInt(vremya[2]);
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
            String[] checktime = time.getCurrentTime().split(":");
            int checkmin = Integer.parseInt(vremya[0]);
            int checksec = Integer.parseInt(vremya[1]);
            int checkms = Integer.parseInt(vremya[2]);
            if (checkms == 0 && controller.getAIStatusDrone())
            {
                for (AbstractObject x : objects)
                {
                    if (x.getClass() == Drone.class) {
                        x.run(Scene, controller, controller.getAIStatusWorker());
                    }
                }
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
    public ArrayList<Thread> getThreadList() {return ThreadList;}
    public void setWorkerCount(int workerCount) {this.WorkerCount = workerCount;}
    public void setDroneCount(int droneCount) {this.DroneCount = droneCount;}
    public void StopThreads() throws InterruptedException {
        for (AbstractObject x : objects)
        {
            x.StopTransition();
        }
    }
    public void ContinueThreads() throws InterruptedException {
        for (AbstractObject x : objects)
        {
            x.ContinueTransition();
        }
    }
}

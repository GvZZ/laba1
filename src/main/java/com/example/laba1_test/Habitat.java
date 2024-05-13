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
    private ArrayList<BaseAI> ThreadList;
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
                System.out.println("Одного ёбнул");
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                ThreadList.getFirst().getPane().getChildren().remove(ThreadList.getFirst());
                objects.getFirst().setImg(null);
                ThreadList.getFirst().allstop();
                objects.remove(objects.getFirst());
                ThreadList.remove(ThreadList.getFirst());
                DroneCount--;
            }
            fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
            while (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
            {
                System.out.println("Одного ёбнул");
                IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                SpawnSet.remove(fintime);
                ThreadList.getFirst().getPane().getChildren().remove(ThreadList.getFirst());
                objects.getFirst().setImg(null);
                ThreadList.getFirst().allstop();
                ThreadList.getFirst().interrupt();
                ThreadList.remove(ThreadList.getFirst());
                objects.remove(objects.getFirst());
                WorkerCount--;
            }
            BeeDelete.interrupt();
        }
    };
    Thread BeeDelete = new Thread(Runnie);
    public Habitat(int a, double b) {
        SpawnSet = new TreeMap<String, String>();
        IDSet = new HashSet<String>();
        objects = new ArrayList<>();
        DroneCount = 0;
        WorkerCount = 0;
        ThreadList = new ArrayList<BaseAI>();
        this.N = a;
        this.P = b;
    }
    public void update(int second, AnimationTimer time, int LifeT, AnchorPane Scene, Controller controller) {
        for (BaseAI x : ThreadList) {
            if (x.getClass() == DroneAI.class) {
                x.setStatus(controller.getAIStatusDrone());
            }
            else{
                x.setStatus(controller.getAIStatusWorker());
            }
        }
        if (time.getMSecond() % 100 == 0) {
            Random rand = new Random();
            if ((rand.nextDouble() < P) && (second % N == 0)) {
                WorkerAI WAI = new WorkerAI(Scene, controller, controller.getAIStatusWorker());
                Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
                WAI.setImg(new_Worker.getImg());
                WAI.everything(new_Worker);
                objects.addLast(new_Worker);
                SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
                WorkerCount++;
                ThreadList.add(WAI);
                ThreadList.getLast().run();
            }
            if (DroneCount <= WorkerCount * K * 0.01) {
                DroneAI DAI = new DroneAI(Scene, controller, controller.getAIStatusDrone());
                Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
                DAI.setImg(new_Drone.getImg());
                DAI.everything(new_Drone);
                objects.addLast(new_Drone);
                String doptime = time.Minute + ":" + time.Second + ":" + 15;
                SpawnSet.put(doptime, objects.getLast().getID());
                DroneCount++;
                ThreadList.add(DAI);
                ThreadList.getLast().run();
            }
        }
        if (!objects.isEmpty())
        {
            String[] vremya = time.getCurrentTime().split(":");
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
            int checkms = Integer.parseInt(vremya[2]);
            if (checkms == 0 && controller.getAIStatusDrone())
            {
                for (AbstractObject x : objects)
                {
                    if (x.getClass() == Drone.class) {

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
    public ArrayList<BaseAI> getThreadList() {return ThreadList;}
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

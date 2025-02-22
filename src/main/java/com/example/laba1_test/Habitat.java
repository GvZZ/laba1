package com.example.laba1_test;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.util.*;

import static java.lang.Math.abs;

public class Habitat extends Thread implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;
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
                if (objects.getFirst() instanceof Drone){
                    DroneCount--;
                }
                else {
                    WorkerCount--;
                }
                objects.remove(objects.getFirst());
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

    public void OrderRemove(ArrayList <AbstractObject> SendingBees, HashSet<String> SendingID, TreeMap<String, String> SendingSpawn){ // Удаляет всё о пчёлах
        for (AbstractObject x : SendingBees) {
            for (int i = 0; i < objects.size(); i++){
                if (x.equals(objects.get(i))) {
                    controller.getSceneTwo_Background().getChildren().remove(objects.get(i).getImg());
                    objects.get(i).getPathTransition().setNode(null);
                    IDSet.remove(objects.get(i).getID());
                    SpawnSet.remove(objects.get(i).getID());
                    objects.get(i).setImg(null);
                    if (objects.get(i) instanceof Drone){
                        DroneCount--;
                    }
                    else {
                        WorkerCount--;
                    }
                    objects.remove(objects.get(i));
                }
            }
        }
    }
    public void OrderAdd(ArrayList <AbstractObject> AddBees , HashSet<String> AddID, TreeMap<String, String> AddSpawn){ // Добавляет всё что нужно пчёлам на клиенте. По какой-то причине спавнит всех в одной точке(виним PathTransition)
        int CurrMin = Integer.parseInt(controller.getMinutes());
        String CurrMinS = String.valueOf(CurrMin);
        int CurrSec = Integer.parseInt(controller.getSeconds());
        String CurrSecS = String.valueOf(CurrSec);
        String AccTime;
        int k = 1;
        for (AbstractObject x : AddBees) {

            if (k >= 100) {CurrSec++; k = 1;}
            if (CurrSec >= 100) {CurrMin++; CurrSec = 0;}
            if (k == 15) {k++;}
            if (CurrMin < 10){CurrMinS = "0" + CurrMin;}
            if (CurrSec < 10){CurrSecS = "0" + CurrSec;}
            if (k < 10) {
                AccTime = CurrMinS + ":" + CurrSecS + ":0" + k;
            }
            else {
                AccTime = CurrMinS + ":" + CurrSecS + ":" + k;
            }
            if (x instanceof Drone){
                x.setpathTransition(new PathTransition());
                x.setImg(new ImageView(new Image("IMGDrone.png")));
                objects.addLast(x);
                SpawnSet.put(AccTime, objects.getLast().getID());
                DroneCount++;
            }
            else{
                x.setpathTransition(new PathTransition());
                x.setImg(new ImageView(new Image("IMGWorker.png")));
                objects.addLast(x);
                SpawnSet.put(AccTime, objects.getLast().getID());
                WorkerCount++;
            }
            k++;
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
    public HashSet<String> getIDSet() {return IDSet;}
    public TreeMap<String, String> getSpawnSet() {return SpawnSet;}
    public void setObjects(Object objects) {
        this.objects = (ArrayList<AbstractObject>) objects;
    }
    public void setWorkerCount(int workerCount) {this.WorkerCount = workerCount;}
    public void setDroneCount(int droneCount) {this.DroneCount = droneCount;}
}

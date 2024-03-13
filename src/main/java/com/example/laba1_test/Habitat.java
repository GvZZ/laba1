package com.example.laba1_test;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

    public void update(int second, AnimationTimer time, int LifeT, AnchorPane Scene, Controller controller) {
        Random rand = new Random();
        if ((rand.nextDouble() < P) && (second % N == 0)) {
            Worker new_Worker = new Worker(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Worker);
            SpawnSet.put(time.getCurrentTime(), objects.getLast().getID());
            WorkerCount++;
            ThreadList.add(new_Worker.everything(new_Worker));
/*            Image image = new Image("IMGWorker.png");
            ImageView imgview = new ImageView(image);
            imgview.setFitHeight(100);
            imgview.setFitWidth(100);
            imgview.setX(objects.getLast().x);
            imgview.setY(objects.getLast().y);*/
            objects.getLast().run(Scene, controller, controller.getAIStatusWorker());
        }
        if (DroneCount <= WorkerCount * K * 0.01) {
            Drone new_Drone = new Drone(rand.nextDouble() * 1200, rand.nextDouble() * 900, LifeT, IDSet);
            objects.addLast(new_Drone);
            String doptime = time.Minute + ":" + time.Second + ":" + 15;
            SpawnSet.put(doptime, objects.getLast().getID());
            DroneCount++;
            ThreadList.add(new_Drone.everything(new_Drone));
            /*Image image = new Image("IMGDrone.png");
            ImageView imgview = new ImageView(image);
            imgview.setFitHeight(100);
            imgview.setFitWidth(100);
            imgview.setX(objects.getLast().x);
            imgview.setY(objects.getLast().y);
            Scene.getChildren().add(imgview);*/
            Scene.getChildren().add(objects.getLast().getImg());
            objects.getLast().run(Scene, controller, controller.getAIStatusWorker());
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
                finsec =  60 - abs(finsec);
                finmin--;
                if (finmin < 0)
                {
                    finmin = 0;
                }
            }
            if (finmin >= 0) {
                String fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + String.format("%d", ms);
                if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
                {
                    IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                    SpawnSet.remove(fintime);
                    objects.getFirst().interrupt();
                    objects.getFirst().allstop();
                    objects.remove(objects.getFirst());
                }
                fintime = String.format("%d", finmin) + ":" + String.format("%d", finsec) + ":" + "15";
                if (SpawnSet.remove(fintime) != null) // if а не while потому что тут не может храниться несколько объектов с одинаковым временем(свойство set)
                {
                    IDSet.remove(objects.getFirst().getID()); // Находим ид объекта, который надо удалить и удаляем ид перед удалением объекта
                    SpawnSet.remove(fintime);
                    objects.getFirst().allstop();
                    objects.remove(objects.getFirst());

                }
            }
            String[] checktime = time.getCurrentTime().split(":");
            int checkmin = Integer.parseInt(vremya[0]);
            int checksec = Integer.parseInt(vremya[1]);
            int checkms = Integer.parseInt(vremya[2]);
            if (checkms == 0 && controller.getAIStatusDrone())
            {
                for (AbstractObject x : objects)
                {
                    Drone drn = new Drone();
                    if (x.getClass() == drn.getClass()) {
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
    public ArrayList<AbstractObject> getObjects() {
        return objects;
    }
    public HashSet<String> getIDSet() {return IDSet;}
    public TreeMap<String, String> getSpawnSet() {return SpawnSet;}
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

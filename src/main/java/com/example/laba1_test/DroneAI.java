package com.example.laba1_test;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class DroneAI extends BaseAI{
    public static Habitat habitat;
    ArrayList <Drone> Dobjects = new ArrayList<Drone>();
    ImageView img;
    double x;
    double y;
    double speed = 10;
    AnchorPane pane;
    Controller controller;
    Boolean Status;
    public DroneAI(Controller controller){
        this.pane = controller.getSceneTwo_Background();
        this.controller = controller;
        this.Status = controller.getAIStatusDrone();
        this.x = Math.random() * (1200 + 1);
        this.y = Math.random() * (800 + 1);
        habitat = controller.getHabitat();
    }
    // работаем с habitat.objects чтобы работать напрямую с оригиналом всех пчёл, а не копией(спойлер, всё равно нахуярено на дубликатах).
    @Override
    public void run(){
        while(true) {
            synchronized (habitat.objects) {
                if (Status) {
                    for (int i = 0; i < habitat.objects.size(); i++) {
                        if (habitat.objects.get(i) instanceof Drone) {
                            Drone Dbee = (Drone) habitat.objects.get(i);
                            img = habitat.objects.get(i).getImg();
                            //System.out.println(i);
                            System.out.println(habitat.objects.get(i).getPathTransition().getStatus());
                            if (habitat.objects.get(i).getPathTransition().getStatus() == Animation.Status.RUNNING){System.out.println("биба"); continue;}
                            System.out.println(habitat.objects.get(i).getPathTransition().getStatus());
                            habitat.objects.get(i).getPathTransition().setDuration(Duration.millis(controller.getLifeTime() * 1000)); // Для i элемента всех пчёл задаём PT прямо в абстрактный объект
                            Path path = new Path();
                            MoveTo moveTo = new MoveTo(x, y);
                            Random rand = new Random();
                            double angle = rand.nextDouble(); // случайный угол
                            x = (speed * Math.cos(angle)) * 100;
                            y = (speed * Math.sin(angle)) * 100;
                            LineTo lineTo = new LineTo(x, y);
                            habitat.objects.get(i).getPathTransition().setNode(img);
                            path.getElements().addAll(moveTo, lineTo);
                            Platform.runLater(() -> {
                                pane.getChildren().add(img);
                            });
                            System.out.println("Пу-Пу-пу");
                            habitat.objects.get(i).getPathTransition().setCycleCount(1);
                            habitat.objects.get(i).getPathTransition().setPath(path);
                            habitat.objects.get(i).getPathTransition().play();
                            Dobjects.addLast(Dbee);
                            if (!Status) {
                                habitat.objects.get(i).getPathTransition().pause();
                            }
                            img.setFitWidth(100);
                            img.setFitHeight(100);
                            img.setX(x);
                            img.setY(y);
                        }
                    }
                }
            }
            try {
                Thread.sleep(100); // Время задержки остается тем же
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void everything(AbstractObject x){
        img.setFitHeight(100);
        img.setFitWidth(100);
    }
    @Override
    public void allstop(){
        for (Drone object : Dobjects) {
            object.setPathTransition(null);
        }
    }

    public void setStatus(Boolean Status){this.Status = Status;}
    public ImageView getImg(){return this.img;}
    public void setImg(ImageView img){this.img = img;}
    public AnchorPane getPane(){return this.pane;}

}

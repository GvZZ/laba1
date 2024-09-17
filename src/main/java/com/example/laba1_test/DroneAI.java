package com.example.laba1_test;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.util.Random;

public class DroneAI extends BaseAI{
    public static Habitat habitat;
    ImageView img;
    double x;
    double y;
    double speed = 10;
    AnchorPane pane;
    Controller controller;
    public DroneAI(Controller controller){
        this.pane = controller.getSceneTwo_Background();
        this.controller = controller;
        this.x = Math.random() * (1200 + 1);
        this.y = Math.random() * (800 + 1);
        habitat = controller.getHabitat();
    }
    // работаем с habitat.objects чтобы работать напрямую с оригиналом всех пчёл, а не копией(спойлер, всё равно нахуярено на дубликатах).
    @Override
    public void run(){
        while(true) {
            for (int i = 0; i < habitat.objects.size(); i++) {
                if (habitat.objects.get(i) instanceof Drone) {
                    if (pane.getChildren().contains(habitat.objects.get(i).getImg()) || habitat.objects.get(i).getPathTransition().getStatus() == Animation.Status.RUNNING) {
                        continue;
                    }
                    img = habitat.objects.get(i).getImg();
                    Platform.runLater(() -> {
                        try {
                            pane.getChildren().add(img);
                        } catch (IllegalArgumentException e) {}
                    });
                    Random rand = new Random();
                    double angle = rand.nextDouble();
                    x = (speed * Math.cos(angle)) * 100;
                    y = (speed * Math.sin(angle)) * 100;
                    img.setFitWidth(100);
                    img.setFitHeight(100);
                    img.setX(x);
                    img.setY(y);
                }
            }
            if (AIState){
                synchronized (habitat.objects) {
                    for (int i = 0; i < habitat.objects.size(); i++) {
                        if (habitat.objects.get(i) instanceof Drone) {
                            if (habitat.objects.get(i).getPathTransition().getStatus() == Animation.Status.RUNNING || controller.getStatus() == 2) {
                                continue;
                            }
                            img = habitat.objects.get(i).getImg();
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
                            habitat.objects.get(i).getPathTransition().setCycleCount(1);
                            habitat.objects.get(i).getPathTransition().setPath(path);
                            habitat.objects.get(i).getPathTransition().play();
                            if (!controller.getAIStatusDrone()) {
                                habitat.objects.get(i).getPathTransition().pause();
                            }

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
    public AnchorPane getPane(){return this.pane;}
}

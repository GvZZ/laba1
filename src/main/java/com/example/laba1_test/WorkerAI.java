package com.example.laba1_test;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;

public class WorkerAI extends BaseAI{
    public static Habitat habitat;
    ImageView img;
    double BirthX;
    double BirthY;
    double speed = 10;
    AnchorPane pane;
    Controller controller;
    ArrayList<AbstractObject> BeeBees;
    public WorkerAI(Controller controller){
        this.pane = controller.getSceneTwo_Background();
        this.controller = controller;
        habitat = controller.getHabitat();
    }
    @Override
    public void run(){
        while(true) {
            if (AIState){
                synchronized (habitat.objects) {
                    if (controller.getAIStatusWorker()) { // Если жожни дыргаются, то поток течёт
                        for (int i = 0; i < habitat.objects.size(); i++) {
                            if (habitat.objects.get(i) instanceof Worker) {
                                if (habitat.objects.get(i).getPathTransition().getStatus() == Animation.Status.RUNNING || controller.getStatus() == 2) {
                                    continue;
                                }
                                BirthX = Math.random() * (1200 + 1);
                                BirthY = Math.random() * (800 + 1);
                                img = habitat.objects.get(i).getImg();
                                habitat.getObjects().get(i).getPathTransition().setDuration(Duration.millis(speed * 150));
                                Path path = new Path();
                                MoveTo moveTo = new MoveTo(BirthX, BirthY);
                                LineTo lineTo = new LineTo(1486 - img.getFitWidth() / 2, 1000 - img.getFitHeight() / 2);
                                habitat.objects.get(i).getPathTransition().setNode(img);
                                path.getElements().addAll(moveTo, lineTo);
                                Platform.runLater(() -> {
                                    pane.getChildren().add(img);
                                });
                                habitat.objects.get(i).getPathTransition().setCycleCount(-1);
                                habitat.objects.get(i).getPathTransition().setAutoReverse(true);
                                habitat.objects.get(i).getPathTransition().setPath(path);
                                habitat.objects.get(i).getPathTransition().play();
                                if (!controller.getAIStatusWorker()) {
                                    habitat.objects.get(i).getPathTransition().pause();
                                }
                                img.setFitWidth(100);
                                img.setFitHeight(100);
                                img.setX(BirthX);
                                img.setY(BirthY);
                            }
                        }
                    }
                }
            }
            try{
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public AnchorPane getPane(){return this.pane;}
}

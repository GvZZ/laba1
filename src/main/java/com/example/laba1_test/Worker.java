package com.example.laba1_test;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import java.util.HashSet;
public class Worker extends AbstractObject{
    double speed = 20;
    Thread Worker_thread;
    public Worker(double initialX, double initialY, int LifeT, HashSet<String> Set) {
        super(initialX, initialY, LifeT, Set);
    }

    public void run(Controller controller){
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(BirthX);
        moveTo.setY(BirthY);
        LineTo lineTo = new LineTo(180.0f, 180.0f);
        path.getElements().addAll(moveTo, lineTo);
    }
    public Thread everything(AbstractObject x){
        Worker_thread = new Thread(x);
        Worker_thread.start();
        return Worker_thread;
    }
}
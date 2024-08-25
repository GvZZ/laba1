package com.example.laba1_test;

import javafx.scene.layout.AnchorPane;

public abstract class BaseAI extends Thread{

    public void StopTransition() throws InterruptedException {}
    public void everything(AbstractObject x){}
    public void allstop(){}
    public void setStatus(Boolean Status){}
    public AnchorPane getPane(){return null;}

}

package com.example.laba1_test;

import javafx.scene.layout.AnchorPane;

public abstract class BaseAI extends Thread{
    boolean AIState = true;
    @Override
    public void interrupt() {
        super.interrupt();
    }
    public void everything(AbstractObject x){}
    public void allstop(){}
    public AnchorPane getPane(){return null;}
    public void setAIState(boolean AIState) {this.AIState = AIState;}
}
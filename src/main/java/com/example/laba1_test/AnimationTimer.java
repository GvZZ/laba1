package com.example.laba1_test;


import javafx.animation.Timeline;

import java.util.Timer;

public class AnimationTimer {
    int Minute;
    int Second;
    int MSecond;

    public AnimationTimer(int minute, int second, int msecond){
        this.Minute = minute;
        this.Second = second;
        this.MSecond = msecond;
    }

    public AnimationTimer(String CurrentTime){
        String[] time = CurrentTime.split(":");
        Minute = Integer.parseInt(time[0]);
        Second = Integer.parseInt(time[1]);
        MSecond = Integer.parseInt(time[2]);
    }
    public String getCurrentTime(){
        return Minute + ":" + Second + ":" + MSecond;
    }

    public void OneSecondPassed(){
        MSecond++;
        if (MSecond == 100) {
            Second++;
            MSecond = 0;
            if (Second == 60) {
                Minute++;
                Second = 0;
            }
        }
    }
}

package com.example.laba1_test;

public class AnimationTimer {
    int Minute;
    int Second;

    public AnimationTimer(int minute, int second){
        this.Minute = minute;
        this.Second = second;
    }

    public AnimationTimer(String CurrentTime){
        String[] time = CurrentTime.split(":");
        Minute = Integer.parseInt(time[0]);
        Second = Integer.parseInt(time[1]);
    }
    public String getCurrentTime(){
        return Minute + ":" + Second;
    }

    public void OneSecondPassed(){
        Second++;
        if (Second == 60)
        {
            Minute++;
            Second = 0;
        }
    }
}

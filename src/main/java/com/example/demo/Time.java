package com.example.demo;

public class Time {
    int Minute;
    int Second;

    public Time(int minute, int second){
        this.Minute = minute;
        this.Second = second;
    }

    public Time(String CurrentTime){
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
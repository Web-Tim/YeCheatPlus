package me.tim.utils;

public class Timer {
    private long startTime;

    public Timer()
    {
        this.startTime = System.currentTimeMillis();
    }

    public boolean hasTimePassed(long time)
    {
        return (System.currentTimeMillis() - this.startTime) >= time;
    }

    public void reset()
    {
        this.startTime = System.currentTimeMillis();
    }
}

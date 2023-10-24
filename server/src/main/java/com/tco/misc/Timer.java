package com.tco.misc;

import java.time.LocalDateTime;

public class Timer {

    long startTime;
    double runTime = 0;

    public Timer(double runTime) {
        this.runTime = runTime;
        createTimer();
    }

    public void createTimer() {
        startTime = System.currentTimeMillis();
    }

    public long maxTime() {
        return (long)(runTime * 1000.0);
    }

    public long timeElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean timeLeft() {
        if (System.currentTimeMillis() - startTime >= maxTime()) {
            return false;
        }
        return true;
    }

}
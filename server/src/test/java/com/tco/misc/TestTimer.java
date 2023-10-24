package com.tco.misc;

import com.tco.misc.Timer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.lang.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTimer {
    
    @Test
    @DisplayName("witticht: creating a timer")
    public void testTimer() {
        Timer timer = new Timer(5);
    }

    @Test
    @DisplayName("witticht: testing max timer")
    public void testTimerMax() {
        Timer timerMax = new Timer(5);
        assertEquals(5000, timerMax.maxTime());
    }

    @Test
    @DisplayName("witticht: testing timeleft t")
    public void testTimerTimeLeftT() {
        Timer timerLeft = new Timer(5);
        assertEquals(true, timerLeft.timeLeft());
    }

    @Test
    @DisplayName("witticht: testing timeleft f")
    public void testTimerTimeLeftF() {
        Timer timerLeft = new Timer(1);
        try {
            for (int i = 0; i < 1; i++){
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(false, timerLeft.timeLeft());
    }

    @Test
    @DisplayName("witticht: testing timeElapsed")
    public void testTimerElapse() {
        boolean flag = false;
        Timer timerElapse = new Timer(5);
        try {
            for (int i = 0; i < 1; i++){
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (timerElapse.timeElapsed() > 0){
            flag = true;
        }
        assertEquals(true, flag);
    }

    @Test
    @DisplayName("witticht: testing create timer")
    public void testTimerCreate() {
        Timer timer = new Timer(5);
        timer.createTimer();
        boolean flag = false;
        try {
            for (int i = 0; i < 1; i++){
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (timer.timeElapsed() > 0)
            flag = true;
        assertEquals(true, flag);
    }

}

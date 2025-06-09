package org.mockito.internal.util;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.Timer;

public class TimerTest {

    private static final long DURATION = 1000; // 1 second
    private Timer timer;

    @Before
    public void setUp() {
        timer = new Timer(DURATION);
    }

    @Test
    public void testTimerIsNotCountingBeforeStart() {
        try {
            timer.isCounting();
            fail("Expected an AssertionError because the timer has not been started yet.");
        } catch (AssertionError e) {
            // Expected behavior
        }
    }

    @Test
    public void testTimerIsCountingAfterStart() {
        timer.start();
        assertTrue("Timer should be counting immediately after start.", timer.isCounting());
    }

    @Test
    public void testTimerStopsCountingAfterDuration() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION + 100); // Sleep for a bit longer than the duration
        assertFalse("Timer should not be counting after the duration has passed.", timer.isCounting());
    }

    @Test
    public void testTimerIsCountingJustBeforeDurationEnds() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION - 100); // Sleep for a bit less than the duration
        assertTrue("Timer should still be counting just before the duration ends.", timer.isCounting());
    }
}
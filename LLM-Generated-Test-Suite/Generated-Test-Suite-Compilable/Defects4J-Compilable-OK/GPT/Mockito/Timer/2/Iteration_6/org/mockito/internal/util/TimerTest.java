package org.mockito.internal.util;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.Timer;

public class TimerTest {

    private static final long DURATION_MILLIS = 1000; // 1 second
    private Timer timer;

    @Before
    public void setUp() {
        timer = new Timer(DURATION_MILLIS);
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
        Thread.sleep(DURATION_MILLIS + 100); // Sleep for a bit longer than the duration
        assertFalse("Timer should not be counting after the duration has passed.", timer.isCounting());
    }

    @Test
    public void testTimerIsCountingDuringDuration() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION_MILLIS / 2); // Sleep for half the duration
        assertTrue("Timer should still be counting halfway through the duration.", timer.isCounting());
    }

    @Test
    public void testMultipleStarts() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION_MILLIS / 2);
        timer.start(); // Restart the timer
        assertTrue("Timer should be counting after being restarted.", timer.isCounting());
        Thread.sleep(DURATION_MILLIS / 2);
        assertTrue("Timer should still be counting after being restarted and half the duration has passed.", timer.isCounting());
        Thread.sleep(DURATION_MILLIS / 2 + 100);
        assertFalse("Timer should not be counting after the full duration has passed since the last start.", timer.isCounting());
    }
}
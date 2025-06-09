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
        // Since startTime is -1 initially, isCounting should throw an AssertionError
        try {
            timer.isCounting();
            fail("Expected an AssertionError to be thrown");
        } catch (AssertionError e) {
            // Expected behavior
        }
    }

    @Test
    public void testTimerIsCountingAfterStart() {
        timer.start();
        assertTrue("Timer should be counting immediately after start", timer.isCounting());
    }

    @Test
    public void testTimerStopsCountingAfterDuration() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION_MILLIS + 100); // Sleep for a bit longer than the duration
        assertFalse("Timer should stop counting after the duration has passed", timer.isCounting());
    }

    @Test
    public void testTimerIsCountingWithinDuration() throws InterruptedException {
        timer.start();
        Thread.sleep(DURATION_MILLIS / 2); // Sleep for half the duration
        assertTrue("Timer should still be counting within the duration", timer.isCounting());
    }
}
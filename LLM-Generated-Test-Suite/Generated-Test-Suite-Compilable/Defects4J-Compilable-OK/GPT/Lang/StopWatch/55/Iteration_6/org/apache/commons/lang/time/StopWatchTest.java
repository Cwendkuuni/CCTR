package org.apache.commons.lang.time;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StopWatchTest {

    private StopWatch stopWatch;

    @Before
    public void setUp() {
        stopWatch = new StopWatch();
    }

    @Test
    public void testStartStopAndGetTime() {
        stopWatch.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        long time = stopWatch.getTime();
        assertTrue("Time should be at least 100ms", time >= 100);
    }

    @Test(expected = IllegalStateException.class)
    public void testStartTwiceWithoutReset() {
        stopWatch.start();
        stopWatch.start(); // Should throw IllegalStateException
    }

    @Test
    public void testReset() {
        stopWatch.start();
        stopWatch.stop();
        stopWatch.reset();
        assertEquals("Time should be 0 after reset", 0, stopWatch.getTime());
    }

    @Test(expected = IllegalStateException.class)
    public void testStopWithoutStart() {
        stopWatch.stop(); // Should throw IllegalStateException
    }

    @Test
    public void testSplitAndUnsplit() {
        stopWatch.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.split();
        long splitTime = stopWatch.getSplitTime();
        assertTrue("Split time should be at least 100ms", splitTime >= 100);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.unsplit();
        stopWatch.stop();
        long totalTime = stopWatch.getTime();
        assertTrue("Total time should be at least 200ms", totalTime >= 200);
    }

    @Test(expected = IllegalStateException.class)
    public void testUnsplitWithoutSplit() {
        stopWatch.start();
        stopWatch.unsplit(); // Should throw IllegalStateException
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitWithoutStart() {
        stopWatch.split(); // Should throw IllegalStateException
    }

    @Test
    public void testSuspendAndResume() {
        stopWatch.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.suspend();
        long suspendedTime = stopWatch.getTime();
        assertTrue("Suspended time should be at least 100ms", suspendedTime >= 100);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.resume();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        long totalTime = stopWatch.getTime();
        assertTrue("Total time should be at least 200ms", totalTime >= 200);
    }

    @Test(expected = IllegalStateException.class)
    public void testResumeWithoutSuspend() {
        stopWatch.start();
        stopWatch.resume(); // Should throw IllegalStateException
    }

    @Test(expected = IllegalStateException.class)
    public void testSuspendWithoutStart() {
        stopWatch.suspend(); // Should throw IllegalStateException
    }

    @Test
    public void testToString() {
        stopWatch.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        String timeString = stopWatch.toString();
        assertNotNull("toString should not return null", timeString);
    }

    @Test
    public void testToSplitString() {
        stopWatch.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.split();
        String splitString = stopWatch.toSplitString();
        assertNotNull("toSplitString should not return null", splitString);
    }
}
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
    public void testInitialState() {
        assertEquals(0, stopWatch.getTime());
    }

    @Test(expected = IllegalStateException.class)
    public void testStopWithoutStart() {
        stopWatch.stop();
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitWithoutStart() {
        stopWatch.split();
    }

    @Test(expected = IllegalStateException.class)
    public void testUnsplitWithoutSplit() {
        stopWatch.unsplit();
    }

    @Test(expected = IllegalStateException.class)
    public void testSuspendWithoutStart() {
        stopWatch.suspend();
    }

    @Test(expected = IllegalStateException.class)
    public void testResumeWithoutSuspend() {
        stopWatch.resume();
    }

    @Test
    public void testStartStop() throws InterruptedException {
        stopWatch.start();
        Thread.sleep(100);
        stopWatch.stop();
        assertTrue(stopWatch.getTime() >= 100);
    }

    @Test
    public void testStartReset() {
        stopWatch.start();
        stopWatch.reset();
        assertEquals(0, stopWatch.getTime());
    }

    @Test
    public void testSplitUnsplit() throws InterruptedException {
        stopWatch.start();
        Thread.sleep(100);
        stopWatch.split();
        long splitTime = stopWatch.getSplitTime();
        Thread.sleep(100);
        stopWatch.unsplit();
        stopWatch.stop();
        assertTrue(stopWatch.getTime() >= 200);
        assertTrue(splitTime >= 100);
    }

    @Test
    public void testSuspendResume() throws InterruptedException {
        stopWatch.start();
        Thread.sleep(100);
        stopWatch.suspend();
        long suspendTime = stopWatch.getTime();
        Thread.sleep(100);
        stopWatch.resume();
        Thread.sleep(100);
        stopWatch.stop();
        assertTrue(stopWatch.getTime() >= 200);
        assertEquals(suspendTime, stopWatch.getTime() - 100);
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleStart() {
        stopWatch.start();
        stopWatch.start();
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleStop() {
        stopWatch.start();
        stopWatch.stop();
        stopWatch.stop();
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleSplit() {
        stopWatch.start();
        stopWatch.split();
        stopWatch.split();
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleSuspend() {
        stopWatch.start();
        stopWatch.suspend();
        stopWatch.suspend();
    }

    @Test
    public void testToString() throws InterruptedException {
        stopWatch.start();
        Thread.sleep(100);
        stopWatch.stop();
        String timeString = stopWatch.toString();
        assertNotNull(timeString);
        assertTrue(timeString.contains(":"));
    }

    @Test
    public void testToSplitString() throws InterruptedException {
        stopWatch.start();
        Thread.sleep(100);
        stopWatch.split();
        String splitString = stopWatch.toSplitString();
        assertNotNull(splitString);
        assertTrue(splitString.contains(":"));
    }
}
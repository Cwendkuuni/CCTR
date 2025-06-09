package com.google.common.util.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Monitor.Guard;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MonitorTest {

    private Monitor monitor;
    private TestGuard guard;

    @Before
    public void setUp() {
        monitor = new Monitor();
        guard = new TestGuard(monitor);
    }

    @Test
    public void testEnter() {
        monitor.enter();
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterInterruptibly() throws InterruptedException {
        monitor.enterInterruptibly();
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWithTimeout() {
        boolean entered = monitor.enter(1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterInterruptiblyWithTimeout() throws InterruptedException {
        boolean entered = monitor.enterInterruptibly(1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testTryEnter() {
        boolean entered = monitor.tryEnter();
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhen() throws InterruptedException {
        monitor.enterWhen(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenUninterruptibly() {
        monitor.enterWhenUninterruptibly(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenWithTimeout() throws InterruptedException {
        boolean entered = monitor.enterWhen(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenUninterruptiblyWithTimeout() {
        boolean entered = monitor.enterWhenUninterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIf() {
        boolean entered = monitor.enterIf(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptibly() throws InterruptedException {
        boolean entered = monitor.enterIfInterruptibly(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfWithTimeout() {
        boolean entered = monitor.enterIf(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptiblyWithTimeout() throws InterruptedException {
        boolean entered = monitor.enterIfInterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testTryEnterIf() {
        boolean entered = monitor.tryEnterIf(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitFor() throws InterruptedException {
        monitor.enter();
        monitor.waitFor(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptibly() {
        monitor.enter();
        monitor.waitForUninterruptibly(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForWithTimeout() throws InterruptedException {
        monitor.enter();
        boolean satisfied = monitor.waitFor(guard, 1, TimeUnit.SECONDS);
        assertTrue(satisfied);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptiblyWithTimeout() {
        monitor.enter();
        boolean satisfied = monitor.waitForUninterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(satisfied);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testLeave() {
        monitor.enter();
        monitor.leave();
        assertFalse(monitor.isOccupiedByCurrentThread());
    }

    @Test
    public void testIsFair() {
        assertFalse(monitor.isFair());
    }

    @Test
    public void testIsOccupied() {
        assertFalse(monitor.isOccupied());
        monitor.enter();
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testIsOccupiedByCurrentThread() {
        assertFalse(monitor.isOccupiedByCurrentThread());
        monitor.enter();
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testGetOccupiedDepth() {
        assertEquals(0, monitor.getOccupiedDepth());
        monitor.enter();
        assertEquals(1, monitor.getOccupiedDepth());
        monitor.leave();
    }

    @Test
    public void testGetQueueLength() {
        assertEquals(0, monitor.getQueueLength());
    }

    @Test
    public void testHasQueuedThreads() {
        assertFalse(monitor.hasQueuedThreads());
    }

    @Test
    public void testHasQueuedThread() {
        Thread thread = new Thread();
        assertFalse(monitor.hasQueuedThread(thread));
    }

    @Test
    public void testHasWaiters() {
        assertFalse(monitor.hasWaiters(guard));
    }

    @Test
    public void testGetWaitQueueLength() {
        assertEquals(0, monitor.getWaitQueueLength(guard));
    }

    private static class TestGuard extends Guard {
        protected TestGuard(Monitor monitor) {
            super(monitor);
        }

        @Override
        public boolean isSatisfied() {
            return true;
        }
    }
}
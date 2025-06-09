package com.google.common.util.concurrent;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Monitor.Guard;

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
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterInterruptibly() throws InterruptedException {
        monitor.enterInterruptibly();
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterWithTimeout() {
        boolean entered = monitor.enter(1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterInterruptiblyWithTimeout() throws InterruptedException {
        boolean entered = monitor.enterInterruptibly(1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testTryEnter() {
        boolean entered = monitor.tryEnter();
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterWhen() throws InterruptedException {
        guard.setSatisfied(true);
        monitor.enterWhen(guard);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test(expected = IllegalMonitorStateException.class)
    public void testEnterWhenWithWrongGuard() throws InterruptedException {
        Monitor anotherMonitor = new Monitor();
        TestGuard anotherGuard = new TestGuard(anotherMonitor);
        monitor.enterWhen(anotherGuard);
    }

    @Test
    public void testEnterWhenUninterruptibly() {
        guard.setSatisfied(true);
        monitor.enterWhenUninterruptibly(guard);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterWhenWithTimeout() throws InterruptedException {
        guard.setSatisfied(true);
        boolean entered = monitor.enterWhen(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterWhenUninterruptiblyWithTimeout() {
        guard.setSatisfied(true);
        boolean entered = monitor.enterWhenUninterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterIf() {
        guard.setSatisfied(true);
        boolean entered = monitor.enterIf(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptibly() throws InterruptedException {
        guard.setSatisfied(true);
        boolean entered = monitor.enterIfInterruptibly(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterIfWithTimeout() {
        guard.setSatisfied(true);
        boolean entered = monitor.enterIf(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptiblyWithTimeout() throws InterruptedException {
        guard.setSatisfied(true);
        boolean entered = monitor.enterIfInterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testTryEnterIf() {
        guard.setSatisfied(true);
        boolean entered = monitor.tryEnterIf(guard);
        assertTrue(entered);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testWaitFor() throws InterruptedException {
        monitor.enter();
        guard.setSatisfied(true);
        monitor.waitFor(guard);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptibly() {
        monitor.enter();
        guard.setSatisfied(true);
        monitor.waitForUninterruptibly(guard);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testWaitForWithTimeout() throws InterruptedException {
        monitor.enter();
        guard.setSatisfied(true);
        boolean satisfied = monitor.waitFor(guard, 1, TimeUnit.SECONDS);
        assertTrue(satisfied);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptiblyWithTimeout() {
        monitor.enter();
        guard.setSatisfied(true);
        boolean satisfied = monitor.waitForUninterruptibly(guard, 1, TimeUnit.SECONDS);
        assertTrue(satisfied);
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testLeave() {
        monitor.enter();
        monitor.leave();
        assertFalse(monitor.isOccupied());
    }

    @Test
    public void testIsFair() {
        assertFalse(monitor.isFair());
        Monitor fairMonitor = new Monitor(true);
        assertTrue(fairMonitor.isFair());
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

    // A simple implementation of Guard for testing purposes
    private static class TestGuard extends Guard {
        private boolean satisfied;

        protected TestGuard(Monitor monitor) {
            super(monitor);
        }

        public void setSatisfied(boolean satisfied) {
            this.satisfied = satisfied;
        }

        @Override
        public boolean isSatisfied() {
            return satisfied;
        }
    }
}
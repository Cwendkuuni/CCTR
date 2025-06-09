package com.google.common.util.concurrent;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import com.google.common.util.concurrent.Monitor;

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
        assertTrue(monitor.enter(1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterInterruptiblyWithTimeout() throws InterruptedException {
        assertTrue(monitor.enterInterruptibly(1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testTryEnter() {
        assertTrue(monitor.tryEnter());
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenGuardSatisfied() throws InterruptedException {
        guard.setSatisfied(true);
        monitor.enterWhen(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test(expected = IllegalMonitorStateException.class)
    public void testEnterWhenGuardNotSatisfied() throws InterruptedException {
        guard.setSatisfied(false);
        monitor.enterWhen(guard);
    }

    @Test
    public void testEnterWhenUninterruptiblyGuardSatisfied() {
        guard.setSatisfied(true);
        monitor.enterWhenUninterruptibly(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenWithTimeoutGuardSatisfied() throws InterruptedException {
        guard.setSatisfied(true);
        assertTrue(monitor.enterWhen(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenUninterruptiblyWithTimeoutGuardSatisfied() {
        guard.setSatisfied(true);
        assertTrue(monitor.enterWhenUninterruptibly(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfGuardSatisfied() {
        guard.setSatisfied(true);
        assertTrue(monitor.enterIf(guard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptiblyGuardSatisfied() throws InterruptedException {
        guard.setSatisfied(true);
        assertTrue(monitor.enterIfInterruptibly(guard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfWithTimeoutGuardSatisfied() {
        guard.setSatisfied(true);
        assertTrue(monitor.enterIf(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptiblyWithTimeoutGuardSatisfied() throws InterruptedException {
        guard.setSatisfied(true);
        assertTrue(monitor.enterIfInterruptibly(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testTryEnterIfGuardSatisfied() {
        guard.setSatisfied(true);
        assertTrue(monitor.tryEnterIf(guard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForGuardSatisfied() throws InterruptedException {
        monitor.enter();
        guard.setSatisfied(true);
        monitor.waitFor(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptiblyGuardSatisfied() {
        monitor.enter();
        guard.setSatisfied(true);
        monitor.waitForUninterruptibly(guard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForWithTimeoutGuardSatisfied() throws InterruptedException {
        monitor.enter();
        guard.setSatisfied(true);
        assertTrue(monitor.waitFor(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptiblyWithTimeoutGuardSatisfied() {
        monitor.enter();
        guard.setSatisfied(true);
        assertTrue(monitor.waitForUninterruptibly(guard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
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

    // TestGuard class for testing purposes
    private static class TestGuard extends Monitor.Guard {
        private boolean satisfied;

        protected TestGuard(Monitor monitor) {
            super(monitor);
        }

        @Override
        public boolean isSatisfied() {
            return satisfied;
        }

        public void setSatisfied(boolean satisfied) {
            this.satisfied = satisfied;
        }
    }
}
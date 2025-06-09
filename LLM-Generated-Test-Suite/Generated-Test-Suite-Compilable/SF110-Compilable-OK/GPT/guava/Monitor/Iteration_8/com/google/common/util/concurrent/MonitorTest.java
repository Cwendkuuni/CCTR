package com.google.common.util.concurrent;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.Monitor;

public class MonitorTest {

    private Monitor monitor;
    private Monitor.Guard satisfiedGuard;
    private Monitor.Guard unsatisfiedGuard;

    @Before
    public void setUp() {
        monitor = new Monitor();
        satisfiedGuard = new Monitor.Guard(monitor) {
            @Override
            public boolean isSatisfied() {
                return true;
            }
        };
        unsatisfiedGuard = new Monitor.Guard(monitor) {
            @Override
            public boolean isSatisfied() {
                return false;
            }
        };
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
    public void testEnterWhen() throws InterruptedException {
        monitor.enterWhen(satisfiedGuard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test(expected = IllegalMonitorStateException.class)
    public void testEnterWhenWithUnsatisfiedGuard() throws InterruptedException {
        monitor.enterWhen(unsatisfiedGuard);
    }

    @Test
    public void testEnterWhenUninterruptibly() {
        monitor.enterWhenUninterruptibly(satisfiedGuard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenWithTimeout() throws InterruptedException {
        assertTrue(monitor.enterWhen(satisfiedGuard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterWhenUninterruptiblyWithTimeout() {
        assertTrue(monitor.enterWhenUninterruptibly(satisfiedGuard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIf() {
        assertTrue(monitor.enterIf(satisfiedGuard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptibly() throws InterruptedException {
        assertTrue(monitor.enterIfInterruptibly(satisfiedGuard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfWithTimeout() {
        assertTrue(monitor.enterIf(satisfiedGuard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testEnterIfInterruptiblyWithTimeout() throws InterruptedException {
        assertTrue(monitor.enterIfInterruptibly(satisfiedGuard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testTryEnterIf() {
        assertTrue(monitor.tryEnterIf(satisfiedGuard));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitFor() throws InterruptedException {
        monitor.enter();
        monitor.waitFor(satisfiedGuard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptibly() {
        monitor.enter();
        monitor.waitForUninterruptibly(satisfiedGuard);
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForWithTimeout() throws InterruptedException {
        monitor.enter();
        assertTrue(monitor.waitFor(satisfiedGuard, 1, TimeUnit.SECONDS));
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testWaitForUninterruptiblyWithTimeout() {
        monitor.enter();
        assertTrue(monitor.waitForUninterruptibly(satisfiedGuard, 1, TimeUnit.SECONDS));
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
    }

    @Test
    public void testIsOccupied() {
        monitor.enter();
        assertTrue(monitor.isOccupied());
        monitor.leave();
    }

    @Test
    public void testIsOccupiedByCurrentThread() {
        monitor.enter();
        assertTrue(monitor.isOccupiedByCurrentThread());
        monitor.leave();
    }

    @Test
    public void testGetOccupiedDepth() {
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
        monitor.enter();
        assertFalse(monitor.hasWaiters(satisfiedGuard));
        monitor.leave();
    }

    @Test
    public void testGetWaitQueueLength() {
        monitor.enter();
        assertEquals(0, monitor.getWaitQueueLength(satisfiedGuard));
        monitor.leave();
    }
}
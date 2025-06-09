package weka.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import weka.core.Memory;

public class MemoryTest {

    private Memory memory;

    @Before
    public void setUp() {
        memory = new Memory();
    }

    @Test
    public void testIsEnabled() {
        assertTrue(memory.isEnabled());
    }

    @Test
    public void testSetEnabled() {
        memory.setEnabled(false);
        assertFalse(memory.isEnabled());
        memory.setEnabled(true);
        assertTrue(memory.isEnabled());
    }

    @Test
    public void testGetUseGUI() {
        assertFalse(memory.getUseGUI());
    }

    @Test
    public void testGetInitial() {
        long initialMemory = Runtime.getRuntime().totalMemory();
        assertEquals(initialMemory, memory.getInitial());
    }

    @Test
    public void testGetCurrent() {
        long currentMemory = Runtime.getRuntime().totalMemory();
        assertEquals(currentMemory, memory.getCurrent());
    }

    @Test
    public void testGetMax() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        assertEquals(maxMemory, memory.getMax());
    }

    @Test
    public void testIsOutOfMemory() {
        long initialMemory = memory.getInitial();
        long currentMemory = memory.getCurrent();
        long maxMemory = memory.getMax();

        boolean outOfMemory = memory.isEnabled() && maxMemory - currentMemory < initialMemory + 200000L;
        assertEquals(outOfMemory, memory.isOutOfMemory());
    }

    @Test
    public void testToMegaByte() {
        long bytes = 10485760; // 10 MB
        double megabytes = Memory.toMegaByte(bytes);
        assertEquals(10.0, megabytes, 0.001);
    }

    @Test
    public void testShowOutOfMemory() {
        // This test is more about ensuring the method runs without exceptions
        memory.showOutOfMemory();
    }

    @Test
    public void testStopThreads() {
        // This test is more about ensuring the method runs without exceptions
        memory.stopThreads();
    }

    @Test
    public void testGetRevision() {
        String revision = memory.getRevision();
        assertNotNull(revision);
        assertFalse(revision.isEmpty());
    }

    @Test
    public void testMain() {
        // This test is more about ensuring the method runs without exceptions
        Memory.main(new String[]{});
    }
}
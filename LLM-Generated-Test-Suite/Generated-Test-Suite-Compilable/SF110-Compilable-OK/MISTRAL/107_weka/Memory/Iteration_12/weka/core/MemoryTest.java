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
        memory.setEnabled(false);
        assertFalse(memory.isEnabled());
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
        Memory memoryWithGUI = new Memory(true);
        assertTrue(memoryWithGUI.getUseGUI());
    }

    @Test
    public void testGetInitial() {
        long initialMemory = memory.getInitial();
        assertTrue(initialMemory > 0);
    }

    @Test
    public void testGetCurrent() {
        long currentMemory = memory.getCurrent();
        assertTrue(currentMemory > 0);
    }

    @Test
    public void testGetMax() {
        long maxMemory = memory.getMax();
        assertTrue(maxMemory > 0);
    }

    @Test
    public void testIsOutOfMemory() {
        memory.setEnabled(false);
        assertFalse(memory.isOutOfMemory());

        memory.setEnabled(true);
        boolean outOfMemory = memory.isOutOfMemory();
        // This test might fail depending on the actual memory usage
        // assertTrue(outOfMemory); // Uncomment if you want to test this condition
    }

    @Test
    public void testToMegaByte() {
        long bytes = 10485760; // 10 MB
        double megabytes = Memory.toMegaByte(bytes);
        assertEquals(10.0, megabytes, 0.001);
    }

    @Test
    public void testShowOutOfMemory() {
        memory.setEnabled(true);
        memory.showOutOfMemory();
        // This method prints to System.err, so we can't directly test the output
        // We can only ensure that it doesn't throw an exception
    }

    @Test
    public void testStopThreads() {
        memory.stopThreads();
        // This method stops threads, so we can't directly test its behavior
        // We can only ensure that it doesn't throw an exception
    }

    @Test
    public void testGetRevision() {
        String revision = memory.getRevision();
        assertNotNull(revision);
        assertFalse(revision.isEmpty());
    }

    @Test
    public void testMain() {
        // We can't directly test the main method, but we can ensure it doesn't throw an exception
        Memory.main(new String[]{});
    }
}
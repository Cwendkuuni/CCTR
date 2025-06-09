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
    public void testConstructor() {
        Memory memoryWithGUI = new Memory(true);
        assertTrue(memoryWithGUI.getUseGUI());

        Memory memoryWithoutGUI = new Memory(false);
        assertFalse(memoryWithoutGUI.getUseGUI());
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
        // This test is highly dependent on the actual memory usage and may not be reliable
        // assertTrue(outOfMemory); // Uncomment if you want to test this, but be cautious
    }

    @Test
    public void testToMegaByte() {
        long bytes = 10485760; // 10 MB
        double megabytes = Memory.toMegaByte(bytes);
        assertEquals(10.0, megabytes, 0.001);
    }

    @Test
    public void testShowOutOfMemory() {
        // This method prints to System.err and shows a GUI dialog, so it's hard to test directly
        // We can only ensure it doesn't throw an exception
        memory.setEnabled(true);
        memory.showOutOfMemory();
    }

    @Test
    public void testStopThreads() {
        // This method stops threads, which is hard to test directly
        // We can only ensure it doesn't throw an exception
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
        // This method prints to System.out, so it's hard to test directly
        // We can only ensure it doesn't throw an exception
        Memory.main(new String[]{});
    }
}
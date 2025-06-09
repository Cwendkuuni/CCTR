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
        // This test might fail if the system is actually out of memory
        assertFalse(outOfMemory);
    }

    @Test
    public void testToMegaByte() {
        long bytes = 1048576; // 1 MB
        double megaBytes = Memory.toMegaByte(bytes);
        assertEquals(1.0, megaBytes, 0.0001);
    }

    @Test
    public void testShowOutOfMemory() {
        memory.setEnabled(false);
        memory.showOutOfMemory(); // No output expected

        memory.setEnabled(true);
        memory.showOutOfMemory(); // Output expected, but not verifiable in unit test
    }

    @Test
    public void testStopThreads() {
        // This method is not easily testable in a unit test due to its nature
        // of stopping threads. It's more of an integration test scenario.
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
        // This method is not easily testable in a unit test due to its nature
        // of being a main method. It's more of an integration test scenario.
        Memory.main(new String[]{});
    }
}
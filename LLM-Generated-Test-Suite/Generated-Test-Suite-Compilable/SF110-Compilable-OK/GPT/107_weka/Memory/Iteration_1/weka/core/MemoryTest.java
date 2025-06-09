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
        assertEquals(Runtime.getRuntime().totalMemory(), memory.getInitial());
    }

    @Test
    public void testGetCurrent() {
        long currentMemory = Runtime.getRuntime().totalMemory();
        assertEquals(currentMemory, memory.getCurrent());
    }

    @Test
    public void testGetMax() {
        assertEquals(Runtime.getRuntime().maxMemory(), memory.getMax());
    }

    @Test
    public void testIsOutOfMemory() {
        memory.setEnabled(true);
        Memory.m_Initial = memory.getMax() - 100000L; // Set initial to a high value
        assertTrue(memory.isOutOfMemory());
        
        Memory.m_Initial = 0; // Reset initial to a low value
        assertFalse(memory.isOutOfMemory());
    }

    @Test
    public void testToMegaByte() {
        assertEquals(1.0, Memory.toMegaByte(1048576L), 0.0001);
        assertEquals(0.5, Memory.toMegaByte(524288L), 0.0001);
    }

    @Test
    public void testShowOutOfMemory() {
        memory.setEnabled(true);
        Memory.m_Initial = memory.getMax() - 100000L; // Set initial to a high value
        memory.showOutOfMemory(); // This will print to System.err and possibly show a dialog
    }

    @Test
    public void testStopThreads() {
        // This method is difficult to test due to its nature of stopping threads.
        // We can only ensure it runs without exceptions.
        memory.stopThreads();
    }

    @Test
    public void testGetRevision() {
        assertNotNull(memory.getRevision());
    }
}
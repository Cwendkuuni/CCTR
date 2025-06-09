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
        Memory memoryWithGUI = new Memory(true);
        assertTrue(memoryWithGUI.getUseGUI());
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
        memory.setEnabled(true);
        Memory.m_Initial = memory.getMax() - 100000L;
        assertTrue(memory.isOutOfMemory());

        Memory.m_Initial = 0;
        assertFalse(memory.isOutOfMemory());
    }

    @Test
    public void testToMegaByte() {
        assertEquals(1.0, Memory.toMegaByte(1048576L), 0.0001);
        assertEquals(0.5, Memory.toMegaByte(524288L), 0.0001);
    }

    @Test
    public void testShowOutOfMemory() {
        // This test is difficult to automate due to its reliance on GUI and system output.
        // It is recommended to test this manually or use a mocking framework to capture System.err and JOptionPane.
    }

    @Test
    public void testStopThreads() {
        // This test is difficult to automate due to its reliance on stopping threads.
        // It is recommended to test this manually or use a mocking framework to simulate thread behavior.
    }

    @Test
    public void testGetRevision() {
        assertNotNull(memory.getRevision());
    }
}
package weka.core;

import org.junit.Before;
import org.junit.Test;
import weka.core.Memory;

import static org.junit.Assert.*;

public class MemoryTest {

    private Memory memory;

    @Before
    public void setUp() {
        memory = new Memory();
    }

    @Test
    public void testDefaultConstructor() {
        assertFalse(memory.getUseGUI());
        assertTrue(memory.isEnabled());
    }

    @Test
    public void testConstructorWithParameter() {
        Memory memoryWithGUI = new Memory(true);
        assertTrue(memoryWithGUI.getUseGUI());
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
        Memory memoryWithGUI = new Memory(true);
        assertTrue(memoryWithGUI.getUseGUI());
    }

    @Test
    public void testGetInitial() {
        assertEquals(Runtime.getRuntime().totalMemory(), memory.getInitial());
    }

    @Test
    public void testGetCurrent() {
        assertEquals(Runtime.getRuntime().totalMemory(), memory.getCurrent());
    }

    @Test
    public void testGetMax() {
        assertEquals(Runtime.getRuntime().maxMemory(), memory.getMax());
    }

    @Test
    public void testIsOutOfMemory() {
        memory.setEnabled(true);
        Memory.m_Initial = memory.getMax() - 100000L;
        assertTrue(memory.isOutOfMemory());

        Memory.m_Initial = memory.getMax() - 300000L;
        assertFalse(memory.isOutOfMemory());
    }

    @Test
    public void testToMegaByte() {
        assertEquals(1.0, Memory.toMegaByte(1048576L), 0.0001);
        assertEquals(0.5, Memory.toMegaByte(524288L), 0.0001);
    }

    @Test
    public void testShowOutOfMemory() {
        // This method prints to System.err and shows a dialog if GUI is enabled.
        // It's difficult to test GUI dialogs and System.err output in unit tests.
        // However, we can ensure no exceptions are thrown.
        memory.showOutOfMemory();
    }

    @Test
    public void testStopThreads() {
        // This method stops threads, which is deprecated and not recommended.
        // We can only ensure no exceptions are thrown during its execution.
        memory.stopThreads();
    }

    @Test
    public void testGetRevision() {
        assertNotNull(memory.getRevision());
    }
}
package org.apache.commons.cli;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommandLineTest {

    private CommandLine commandLine;

    @Before
    public void setUp() {
        commandLine = new CommandLine();
    }

    @Test
    public void testHasOption() {
        assertFalse(commandLine.hasOption("test"));
    }

    @Test
    public void testHasOptionChar() {
        assertFalse(commandLine.hasOption('t'));
    }

    @Test
    public void testGetOptionObject() {
        assertNull(commandLine.getOptionObject("test"));
    }

    @Test
    public void testGetOptionObjectChar() {
        assertNull(commandLine.getOptionObject('t'));
    }

    @Test
    public void testGetOptionValue() {
        assertNull(commandLine.getOptionValue("test"));
    }

    @Test
    public void testGetOptionValueChar() {
        assertNull(commandLine.getOptionValue('t'));
    }

    @Test
    public void testGetOptionValues() {
        assertNull(commandLine.getOptionValues("test"));
    }

    @Test
    public void testGetOptionValuesChar() {
        assertNull(commandLine.getOptionValues('t'));
    }

    @Test
    public void testGetOptionValueWithDefaultValue() {
        assertEquals("default", commandLine.getOptionValue("test", "default"));
    }

    @Test
    public void testGetOptionValueCharWithDefaultValue() {
        assertEquals("default", commandLine.getOptionValue('t', "default"));
    }

    @Test
    public void testGetArgs() {
        assertArrayEquals(new String[]{}, commandLine.getArgs());
    }

    @Test
    public void testGetArgList() {
        assertEquals(0, commandLine.getArgList().size());
    }
}
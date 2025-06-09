package org.apache.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CommandLineTest {

    private CommandLine commandLine;
    private Option option;

    @Before
    public void setUp() {
        commandLine = new CommandLine();
        option = new Option("t", "test", true, "test option");
    }

    @Test
    public void testHasOptionString() {
        commandLine.addOption(option);
        assertTrue(commandLine.hasOption("t"));
        assertFalse(commandLine.hasOption("x"));
    }

    @Test
    public void testHasOptionChar() {
        commandLine.addOption(option);
        assertTrue(commandLine.hasOption('t'));
        assertFalse(commandLine.hasOption('x'));
    }

    @Test
    public void testGetOptionObjectString() {
        option.addValue("value");
        commandLine.addOption(option);
        assertEquals("value", commandLine.getOptionObject("t"));
        assertNull(commandLine.getOptionObject("x"));
    }

    @Test
    public void testGetOptionObjectChar() {
        option.addValue("value");
        commandLine.addOption(option);
        assertEquals("value", commandLine.getOptionObject('t'));
        assertNull(commandLine.getOptionObject('x'));
    }

    @Test
    public void testGetOptionValueString() {
        option.addValue("value");
        commandLine.addOption(option);
        assertEquals("value", commandLine.getOptionValue("t"));
        assertNull(commandLine.getOptionValue("x"));
    }

    @Test
    public void testGetOptionValueChar() {
        option.addValue("value");
        commandLine.addOption(option);
        assertEquals("value", commandLine.getOptionValue('t'));
        assertNull(commandLine.getOptionValue('x'));
    }

    @Test
    public void testGetOptionValuesString() {
        option.addValue("value1");
        option.addValue("value2");
        commandLine.addOption(option);
        assertArrayEquals(new String[]{"value1", "value2"}, commandLine.getOptionValues("t"));
        assertNull(commandLine.getOptionValues("x"));
    }

    @Test
    public void testGetOptionValuesChar() {
        option.addValue("value1");
        option.addValue("value2");
        commandLine.addOption(option);
        assertArrayEquals(new String[]{"value1", "value2"}, commandLine.getOptionValues('t'));
        assertNull(commandLine.getOptionValues('x'));
    }

    @Test
    public void testGetOptionValueStringDefault() {
        commandLine.addOption(option);
        assertEquals("default", commandLine.getOptionValue("t", "default"));
        assertEquals("default", commandLine.getOptionValue("x", "default"));
    }

    @Test
    public void testGetOptionValueCharDefault() {
        commandLine.addOption(option);
        assertEquals("default", commandLine.getOptionValue('t', "default"));
        assertEquals("default", commandLine.getOptionValue('x', "default"));
    }

    @Test
    public void testGetArgs() {
        commandLine.addArg("arg1");
        commandLine.addArg("arg2");
        assertArrayEquals(new String[]{"arg1", "arg2"}, commandLine.getArgs());
    }

    @Test
    public void testGetArgList() {
        commandLine.addArg("arg1");
        commandLine.addArg("arg2");
        List<String> args = commandLine.getArgList();
        assertEquals(2, args.size());
        assertEquals("arg1", args.get(0));
        assertEquals("arg2", args.get(1));
    }

    @Test
    public void testIterator() {
        commandLine.addOption(option);
        Iterator<Option> iterator = commandLine.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(option, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetOptions() {
        commandLine.addOption(option);
        Option[] options = commandLine.getOptions();
        assertEquals(1, options.length);
        assertEquals(option, options[0]);
    }
}
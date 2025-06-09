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
    private Option optionA;
    private Option optionB;

    @Before
    public void setUp() {
        commandLine = new CommandLine();
        optionA = new Option("a", "alpha", true, "Alpha option");
        optionB = new Option("b", "beta", false, "Beta option");
        commandLine.addOption(optionA);
        commandLine.addOption(optionB);
    }

    @Test
    public void testHasOptionWithString() {
        assertTrue(commandLine.hasOption("a"));
        assertTrue(commandLine.hasOption("alpha"));
        assertFalse(commandLine.hasOption("c"));
    }

    @Test
    public void testHasOptionWithChar() {
        assertTrue(commandLine.hasOption('a'));
        assertFalse(commandLine.hasOption('c'));
    }

    @Test
    public void testGetOptionObjectWithString() {
        assertNull(commandLine.getOptionObject("a")); // No value set
        assertNull(commandLine.getOptionObject("c")); // Option does not exist
    }

    @Test
    public void testGetOptionObjectWithChar() {
        assertNull(commandLine.getOptionObject('a')); // No value set
        assertNull(commandLine.getOptionObject('c')); // Option does not exist
    }

    @Test
    public void testGetOptionValueWithString() {
        assertNull(commandLine.getOptionValue("a")); // No value set
        assertNull(commandLine.getOptionValue("c")); // Option does not exist
    }

    @Test
    public void testGetOptionValueWithChar() {
        assertNull(commandLine.getOptionValue('a')); // No value set
        assertNull(commandLine.getOptionValue('c')); // Option does not exist
    }

    @Test
    public void testGetOptionValuesWithString() {
        assertNull(commandLine.getOptionValues("a")); // No value set
        assertNull(commandLine.getOptionValues("c")); // Option does not exist
    }

    @Test
    public void testGetOptionValuesWithChar() {
        assertNull(commandLine.getOptionValues('a')); // No value set
        assertNull(commandLine.getOptionValues('c')); // Option does not exist
    }

    @Test
    public void testGetOptionValueWithDefault() {
        assertEquals("default", commandLine.getOptionValue("a", "default"));
        assertEquals("default", commandLine.getOptionValue("c", "default"));
    }

    @Test
    public void testGetOptionValueWithCharAndDefault() {
        assertEquals("default", commandLine.getOptionValue('a', "default"));
        assertEquals("default", commandLine.getOptionValue('c', "default"));
    }

    @Test
    public void testGetArgs() {
        commandLine.addArg("arg1");
        commandLine.addArg("arg2");
        String[] args = commandLine.getArgs();
        assertEquals(2, args.length);
        assertEquals("arg1", args[0]);
        assertEquals("arg2", args[1]);
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
        Iterator<Option> iterator = commandLine.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(optionA, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(optionB, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetOptions() {
        Option[] options = commandLine.getOptions();
        assertEquals(2, options.length);
        assertEquals(optionA, options[0]);
        assertEquals(optionB, options[1]);
    }
}
package org.apache.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.TypeHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CommandLineTest {

    private CommandLine commandLine;

    @Before
    public void setUp() {
        commandLine = new CommandLine();
    }

    @Test
    public void testHasOptionWithString() {
        Option option = new Option("a", "alpha", true, "Alpha option");
        commandLine.addOption(option);

        assertTrue(commandLine.hasOption("a"));
        assertTrue(commandLine.hasOption("alpha"));
        assertFalse(commandLine.hasOption("b"));
    }

    @Test
    public void testHasOptionWithChar() {
        Option option = new Option("b", "beta", true, "Beta option");
        commandLine.addOption(option);

        assertTrue(commandLine.hasOption('b'));
        assertFalse(commandLine.hasOption('c'));
    }

    @Test
    public void testGetOptionObjectWithString() {
        Option option = new Option("c", "charlie", true, "Charlie option");
        option.setType(Integer.class);
        commandLine.addOption(option);
        commandLine.addArg("123");

        assertEquals(123, commandLine.getOptionObject("c"));
        assertNull(commandLine.getOptionObject("d"));
    }

    @Test
    public void testGetOptionObjectWithChar() {
        Option option = new Option("d", "delta", true, "Delta option");
        option.setType(Double.class);
        commandLine.addOption(option);
        commandLine.addArg("123.45");

        assertEquals(123.45, commandLine.getOptionObject('d'));
        assertNull(commandLine.getOptionObject('e'));
    }

    @Test
    public void testGetOptionValueWithString() {
        Option option = new Option("e", "echo", true, "Echo option");
        commandLine.addOption(option);
        commandLine.addArg("value");

        assertEquals("value", commandLine.getOptionValue("e"));
        assertNull(commandLine.getOptionValue("f"));
    }

    @Test
    public void testGetOptionValueWithChar() {
        Option option = new Option("f", "foxtrot", true, "Foxtrot option");
        commandLine.addOption(option);
        commandLine.addArg("value");

        assertEquals("value", commandLine.getOptionValue('f'));
        assertNull(commandLine.getOptionValue('g'));
    }

    @Test
    public void testGetOptionValuesWithString() {
        Option option = new Option("g", "golf", true, "Golf option");
        commandLine.addOption(option);
        commandLine.addArg("value1");
        commandLine.addArg("value2");

        String[] values = commandLine.getOptionValues("g");
        assertNotNull(values);
        assertEquals(2, values.length);
        assertEquals("value1", values[0]);
        assertEquals("value2", values[1]);
    }

    @Test
    public void testGetOptionValuesWithChar() {
        Option option = new Option("h", "hotel", true, "Hotel option");
        commandLine.addOption(option);
        commandLine.addArg("value1");
        commandLine.addArg("value2");

        String[] values = commandLine.getOptionValues('h');
        assertNotNull(values);
        assertEquals(2, values.length);
        assertEquals("value1", values[0]);
        assertEquals("value2", values[1]);
    }

    @Test
    public void testGetOptionValueWithDefault() {
        Option option = new Option("i", "india", true, "India option");
        commandLine.addOption(option);

        assertEquals("default", commandLine.getOptionValue("i", "default"));
        commandLine.addArg("value");
        assertEquals("value", commandLine.getOptionValue("i", "default"));
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

        List args = commandLine.getArgList();
        assertEquals(2, args.size());
        assertEquals("arg1", args.get(0));
        assertEquals("arg2", args.get(1));
    }

    @Test
    public void testIterator() {
        Option option1 = new Option("j", "juliet", true, "Juliet option");
        Option option2 = new Option("k", "kilo", true, "Kilo option");
        commandLine.addOption(option1);
        commandLine.addOption(option2);

        Iterator iterator = commandLine.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(option1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(option2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetOptions() {
        Option option1 = new Option("l", "lima", true, "Lima option");
        Option option2 = new Option("m", "mike", true, "Mike option");
        commandLine.addOption(option1);
        commandLine.addOption(option2);

        Option[] options = commandLine.getOptions();
        assertEquals(2, options.length);
        assertEquals(option1, options[0]);
        assertEquals(option2, options[1]);
    }
}
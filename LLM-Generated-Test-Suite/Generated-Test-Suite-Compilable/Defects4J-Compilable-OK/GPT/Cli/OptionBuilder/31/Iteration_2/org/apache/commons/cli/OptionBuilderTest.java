package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionBuilderTest {

    @Before
    public void setUp() {
        // Reset OptionBuilder before each test to ensure no state leakage
        OptionBuilder.withLongOpt(null);
    }

    @Test
    public void testWithLongOpt() {
        Option option = OptionBuilder.withLongOpt("testLongOpt").create('t');
        assertEquals("testLongOpt", option.getLongOpt());
    }

    @Test
    public void testHasArg() {
        Option option = OptionBuilder.hasArg().create('a');
        assertTrue(option.hasArg());
        assertEquals(1, option.getArgs());
    }

    @Test
    public void testHasArgBooleanTrue() {
        Option option = OptionBuilder.hasArg(true).create('b');
        assertTrue(option.hasArg());
        assertEquals(1, option.getArgs());
    }

    @Test
    public void testHasArgBooleanFalse() {
        Option option = OptionBuilder.hasArg(false).create('c');
        assertFalse(option.hasArg());
        assertEquals(Option.UNINITIALIZED, option.getArgs());
    }

    @Test
    public void testWithArgName() {
        Option option = OptionBuilder.withArgName("argName").create('d');
        assertEquals("argName", option.getArgName());
    }

    @Test
    public void testIsRequired() {
        Option option = OptionBuilder.isRequired().create('e');
        assertTrue(option.isRequired());
    }

    @Test
    public void testWithValueSeparatorChar() {
        Option option = OptionBuilder.withValueSeparator(':').create('f');
        assertEquals(':', option.getValueSeparator());
    }

    @Test
    public void testWithValueSeparator() {
        Option option = OptionBuilder.withValueSeparator().create('g');
        assertEquals('=', option.getValueSeparator());
    }

    @Test
    public void testIsRequiredBooleanTrue() {
        Option option = OptionBuilder.isRequired(true).create('h');
        assertTrue(option.isRequired());
    }

    @Test
    public void testIsRequiredBooleanFalse() {
        Option option = OptionBuilder.isRequired(false).create('i');
        assertFalse(option.isRequired());
    }

    @Test
    public void testHasArgs() {
        Option option = OptionBuilder.hasArgs().create('j');
        assertEquals(Option.UNLIMITED_VALUES, option.getArgs());
    }

    @Test
    public void testHasArgsInt() {
        Option option = OptionBuilder.hasArgs(3).create('k');
        assertEquals(3, option.getArgs());
    }

    @Test
    public void testHasOptionalArg() {
        Option option = OptionBuilder.hasOptionalArg().create('l');
        assertTrue(option.hasOptionalArg());
        assertEquals(1, option.getArgs());
    }

    @Test
    public void testHasOptionalArgs() {
        Option option = OptionBuilder.hasOptionalArgs().create('m');
        assertTrue(option.hasOptionalArg());
        assertEquals(Option.UNLIMITED_VALUES, option.getArgs());
    }

    @Test
    public void testHasOptionalArgsInt() {
        Option option = OptionBuilder.hasOptionalArgs(2).create('n');
        assertTrue(option.hasOptionalArg());
        assertEquals(2, option.getArgs());
    }

    @Test
    public void testWithType() {
        Option option = OptionBuilder.withType(String.class).create('o');
        assertEquals(String.class, option.getType());
    }

    @Test
    public void testWithDescription() {
        Option option = OptionBuilder.withDescription("This is a test option").create('p');
        assertEquals("This is a test option", option.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutLongOpt() {
        OptionBuilder.create();
    }

    @Test
    public void testCreateWithChar() {
        Option option = OptionBuilder.withLongOpt("test").create('q');
        assertEquals("test", option.getLongOpt());
        assertEquals("q", option.getOpt());
    }

    @Test
    public void testCreateWithString() {
        Option option = OptionBuilder.withLongOpt("test").create("r");
        assertEquals("test", option.getLongOpt());
        assertEquals("r", option.getOpt());
    }
}
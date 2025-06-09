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
        OptionBuilder.withDescription(null);
        OptionBuilder.withArgName("arg");
        OptionBuilder.isRequired(false);
        OptionBuilder.hasArgs(Option.UNINITIALIZED);
        OptionBuilder.withType(null);
        OptionBuilder.withValueSeparator((char) 0);
    }

    @Test
    public void testWithLongOpt() {
        Option option = OptionBuilder.withLongOpt("testLongOpt").create('t');
        assertEquals("testLongOpt", option.getLongOpt());
    }

    @Test
    public void testHasArg() {
        Option option = OptionBuilder.hasArg().create('a');
        assertEquals(1, option.getArgs());
    }

    @Test
    public void testHasArgBooleanTrue() {
        Option option = OptionBuilder.hasArg(true).create('b');
        assertEquals(1, option.getArgs());
    }

    @Test
    public void testHasArgBooleanFalse() {
        Option option = OptionBuilder.hasArg(false).create('c');
        assertEquals(Option.UNINITIALIZED, option.getArgs());
    }

    @Test
    public void testWithArgName() {
        Option option = OptionBuilder.withArgName("testArgName").create('d');
        assertEquals("testArgName", option.getArgName());
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
        assertEquals(1, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasOptionalArgs() {
        Option option = OptionBuilder.hasOptionalArgs().create('m');
        assertEquals(Option.UNLIMITED_VALUES, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasOptionalArgsInt() {
        Option option = OptionBuilder.hasOptionalArgs(2).create('n');
        assertEquals(2, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testWithType() {
        Option option = OptionBuilder.withType(String.class).create('o');
        assertEquals(String.class, option.getType());
    }

    @Test
    public void testWithDescription() {
        Option option = OptionBuilder.withDescription("test description").create('p');
        assertEquals("test description", option.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutLongOpt() {
        OptionBuilder.create();
    }

    @Test
    public void testCreateWithChar() {
        Option option = OptionBuilder.withLongOpt("test").create('q');
        assertEquals("q", option.getOpt());
    }

    @Test
    public void testCreateWithString() {
        Option option = OptionBuilder.withLongOpt("test").create("r");
        assertEquals("r", option.getOpt());
    }
}
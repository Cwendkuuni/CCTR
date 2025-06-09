package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionBuilderTest {

    @Before
    public void setUp() {
        // Reset the OptionBuilder before each test to ensure no state is carried over
        OptionBuilder.withLongOpt(null);
        OptionBuilder.withDescription(null);
        OptionBuilder.withArgName(null);
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
    public void testHasArgBoolean() {
        Option option = OptionBuilder.hasArg(true).create('b');
        assertEquals(1, option.getArgs());

        option = OptionBuilder.hasArg(false).create('b');
        assertEquals(Option.UNINITIALIZED, option.getArgs());
    }

    @Test
    public void testWithArgName() {
        Option option = OptionBuilder.withArgName("argName").create('c');
        assertEquals("argName", option.getArgName());
    }

    @Test
    public void testIsRequired() {
        Option option = OptionBuilder.isRequired().create('d');
        assertTrue(option.isRequired());
    }

    @Test
    public void testWithValueSeparatorChar() {
        Option option = OptionBuilder.withValueSeparator(':').create('e');
        assertEquals(':', option.getValueSeparator());
    }

    @Test
    public void testWithValueSeparator() {
        Option option = OptionBuilder.withValueSeparator().create('f');
        assertEquals('=', option.getValueSeparator());
    }

    @Test
    public void testIsRequiredBoolean() {
        Option option = OptionBuilder.isRequired(true).create('g');
        assertTrue(option.isRequired());

        option = OptionBuilder.isRequired(false).create('g');
        assertFalse(option.isRequired());
    }

    @Test
    public void testHasArgs() {
        Option option = OptionBuilder.hasArgs().create('h');
        assertEquals(Option.UNLIMITED_VALUES, option.getArgs());
    }

    @Test
    public void testHasArgsInt() {
        Option option = OptionBuilder.hasArgs(3).create('i');
        assertEquals(3, option.getArgs());
    }

    @Test
    public void testHasOptionalArg() {
        Option option = OptionBuilder.hasOptionalArg().create('j');
        assertEquals(1, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasOptionalArgs() {
        Option option = OptionBuilder.hasOptionalArgs().create('k');
        assertEquals(Option.UNLIMITED_VALUES, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasOptionalArgsInt() {
        Option option = OptionBuilder.hasOptionalArgs(2).create('l');
        assertEquals(2, option.getArgs());
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testWithType() {
        Option option = OptionBuilder.withType(String.class).create('m');
        assertEquals(String.class, option.getType());
    }

    @Test
    public void testWithDescription() {
        Option option = OptionBuilder.withDescription("This is a test description").create('n');
        assertEquals("This is a test description", option.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutLongOpt() {
        OptionBuilder.create();
    }

    @Test
    public void testCreateWithChar() {
        Option option = OptionBuilder.withLongOpt("test").create('o');
        assertEquals("o", option.getOpt());
    }

    @Test
    public void testCreateWithString() {
        Option option = OptionBuilder.withLongOpt("test").create("opt");
        assertEquals("opt", option.getOpt());
    }
}
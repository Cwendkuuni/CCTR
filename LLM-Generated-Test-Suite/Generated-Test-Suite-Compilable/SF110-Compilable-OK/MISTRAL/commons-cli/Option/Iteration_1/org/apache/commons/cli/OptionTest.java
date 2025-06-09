package org.apache.commons.cli;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OptionTest {

    private Option option;

    @Before
    public void setUp() {
        option = new Option("o", "description");
    }

    @Test
    public void testConstructorWithOptAndDescription() {
        Option opt = new Option("o", "description");
        assertEquals("o", opt.getOpt());
        assertEquals("description", opt.getDescription());
    }

    @Test
    public void testConstructorWithOptHasArgAndDescription() {
        Option opt = new Option("o", true, "description");
        assertEquals("o", opt.getOpt());
        assertTrue(opt.hasArg());
        assertEquals("description", opt.getDescription());
    }

    @Test
    public void testConstructorWithOptLongOptHasArgAndDescription() {
        Option opt = new Option("o", "longOpt", true, "description");
        assertEquals("o", opt.getOpt());
        assertEquals("longOpt", opt.getLongOpt());
        assertTrue(opt.hasArg());
        assertEquals("description", opt.getDescription());
    }

    @Test
    public void testGetId() {
        assertEquals('o', option.getId());
    }

    @Test
    public void testGetKey() {
        assertEquals("o", option.getKey());
    }

    @Test
    public void testGetOpt() {
        assertEquals("o", option.getOpt());
    }

    @Test
    public void testGetType() {
        assertNull(option.getType());
    }

    @Test
    public void testSetType() {
        option.setType("type");
        assertEquals("type", option.getType());
    }

    @Test
    public void testGetLongOpt() {
        assertNull(option.getLongOpt());
    }

    @Test
    public void testSetLongOpt() {
        option.setLongOpt("longOpt");
        assertEquals("longOpt", option.getLongOpt());
    }

    @Test
    public void testSetOptionalArg() {
        option.setOptionalArg(true);
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasOptionalArg() {
        assertFalse(option.hasOptionalArg());
    }

    @Test
    public void testHasLongOpt() {
        assertFalse(option.hasLongOpt());
    }

    @Test
    public void testHasArg() {
        assertFalse(option.hasArg());
    }

    @Test
    public void testGetDescription() {
        assertEquals("description", option.getDescription());
    }

    @Test
    public void testSetDescription() {
        option.setDescription("new description");
        assertEquals("new description", option.getDescription());
    }

    @Test
    public void testIsRequired() {
        assertFalse(option.isRequired());
    }

    @Test
    public void testSetRequired() {
        option.setRequired(true);
        assertTrue(option.isRequired());
    }

    @Test
    public void testSetArgName() {
        option.setArgName("argName");
        assertEquals("argName", option.getArgName());
    }

    @Test
    public void testGetArgName() {
        assertEquals("arg", option.getArgName());
    }

    @Test
    public void testHasArgName() {
        assertTrue(option.hasArgName());
    }

    @Test
    public void testHasArgs() {
        assertFalse(option.hasArgs());
    }

    @Test
    public void testSetArgs() {
        option.setArgs(2);
        assertEquals(2, option.getArgs());
    }

    @Test
    public void testSetValueSeparator() {
        option.setValueSeparator('=');
        assertEquals('=', option.getValueSeparator());
    }

    @Test
    public void testGetValueSeparator() {
        assertEquals('\0', option.getValueSeparator());
    }

    @Test
    public void testHasValueSeparator() {
        assertFalse(option.hasValueSeparator());
    }

    @Test
    public void testGetArgs() {
        assertEquals(-1, option.getArgs());
    }

    @Test
    public void testAddValueForProcessing() {
        option.setArgs(1);
        option.addValueForProcessing("value");
        assertEquals("value", option.getValue());
    }

    @Test(expected = RuntimeException.class)
    public void testAddValueForProcessingNoArgsAllowed() {
        option.addValueForProcessing("value");
    }

    @Test
    public void testGetValue() {
        assertNull(option.getValue());
    }

    @Test
    public void testGetValueWithIndex() {
        assertNull(option.getValue(0));
    }

    @Test
    public void testGetValueWithDefaultValue() {
        assertEquals("default", option.getValue("default"));
    }

    @Test
    public void testGetValues() {
        assertNull(option.getValues());
    }

    @Test
    public void testGetValuesList() {
        assertTrue(option.getValuesList().isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals("[ option: o  [ARG] :: description ]", option.toString());
    }

    @Test
    public void testEquals() {
        Option other = new Option("o", "description");
        assertTrue(option.equals(other));
    }

    @Test
    public void testHashCode() {
        Option other = new Option("o", "description");
        assertEquals(option.hashCode(), other.hashCode());
    }

    @Test
    public void testClone() {
        Option clone = (Option) option.clone();
        assertEquals(option, clone);
        assertNotSame(option, clone);
    }

    @Test
    public void testClearValues() {
        option.setArgs(1);
        option.addValueForProcessing("value");
        option.clearValues();
        assertTrue(option.getValuesList().isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValue() {
        option.addValue("value");
    }
}
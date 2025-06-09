package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OptionTest {

    private Option option;

    @Before
    public void setUp() {
        option = new Option("a", "test option");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidOption() {
        new Option("", "description");
    }

    @Test
    public void testConstructorWithValidOption() {
        Option opt = new Option("b", "long", true, "description");
        assertEquals("b", opt.getOpt());
        assertEquals("long", opt.getLongOpt());
        assertTrue(opt.hasArg());
        assertEquals("description", opt.getDescription());
    }

    @Test
    public void testGetId() {
        assertEquals('a', option.getId());
    }

    @Test
    public void testGetKey() {
        assertEquals("a", option.getKey());
    }

    @Test
    public void testGetOpt() {
        assertEquals("a", option.getOpt());
    }

    @Test
    public void testGetType() {
        assertNull(option.getType());
        option.setType(String.class);
        assertEquals(String.class, option.getType());
    }

    @Test
    public void testSetType() {
        option.setType(Integer.class);
        assertEquals(Integer.class, option.getType());
    }

    @Test
    public void testGetLongOpt() {
        assertNull(option.getLongOpt());
        option.setLongOpt("longOpt");
        assertEquals("longOpt", option.getLongOpt());
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
        option.setOptionalArg(true);
        assertTrue(option.hasOptionalArg());
    }

    @Test
    public void testHasLongOpt() {
        assertFalse(option.hasLongOpt());
        option.setLongOpt("longOpt");
        assertTrue(option.hasLongOpt());
    }

    @Test
    public void testHasArg() {
        assertFalse(option.hasArg());
        option.setArgs(1);
        assertTrue(option.hasArg());
    }

    @Test
    public void testGetDescription() {
        assertEquals("test option", option.getDescription());
    }

    @Test
    public void testSetDescription() {
        option.setDescription("new description");
        assertEquals("new description", option.getDescription());
    }

    @Test
    public void testIsRequired() {
        assertFalse(option.isRequired());
        option.setRequired(true);
        assertTrue(option.isRequired());
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
        option.setArgName("argName");
        assertEquals("argName", option.getArgName());
    }

    @Test
    public void testHasArgName() {
        assertTrue(option.hasArgName());
        option.setArgName("");
        assertFalse(option.hasArgName());
    }

    @Test
    public void testHasArgs() {
        assertFalse(option.hasArgs());
        option.setArgs(2);
        assertTrue(option.hasArgs());
    }

    @Test
    public void testSetArgs() {
        option.setArgs(2);
        assertEquals(2, option.getArgs());
    }

    @Test
    public void testSetValueSeparator() {
        option.setValueSeparator(',');
        assertEquals(',', option.getValueSeparator());
    }

    @Test
    public void testGetValueSeparator() {
        assertEquals('\0', option.getValueSeparator());
        option.setValueSeparator(';');
        assertEquals(';', option.getValueSeparator());
    }

    @Test
    public void testHasValueSeparator() {
        assertFalse(option.hasValueSeparator());
        option.setValueSeparator(',');
        assertTrue(option.hasValueSeparator());
    }

    @Test
    public void testGetArgs() {
        assertEquals(-1, option.getArgs());
        option.setArgs(3);
        assertEquals(3, option.getArgs());
    }

    @Test(expected = RuntimeException.class)
    public void testAddValueForProcessingNoArgsAllowed() {
        option.addValueForProcessing("value");
    }

    @Test
    public void testAddValueForProcessingWithArgs() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        assertEquals("value1", option.getValue(0));
        assertEquals("value2", option.getValue(1));
    }

    @Test
    public void testGetValue() {
        option.setArgs(1);
        option.addValueForProcessing("value");
        assertEquals("value", option.getValue());
    }

    @Test
    public void testGetValueWithIndex() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        assertEquals("value1", option.getValue(0));
        assertEquals("value2", option.getValue(1));
    }

    @Test
    public void testGetValueWithDefault() {
        assertNull(option.getValue("default"));
        option.setArgs(1);
        option.addValueForProcessing("value");
        assertEquals("value", option.getValue("default"));
    }

    @Test
    public void testGetValues() {
        assertNull(option.getValues());
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        String[] values = option.getValues();
        assertEquals(2, values.length);
        assertEquals("value1", values[0]);
        assertEquals("value2", values[1]);
    }

    @Test
    public void testGetValuesList() {
        List valuesList = option.getValuesList();
        assertTrue(valuesList.isEmpty());
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        valuesList = option.getValuesList();
        assertEquals(2, valuesList.size());
        assertEquals("value1", valuesList.get(0));
        assertEquals("value2", valuesList.get(1));
    }

    @Test
    public void testToString() {
        String expected = "[ option: a  :: test option ]";
        assertEquals(expected, option.toString());
    }

    @Test
    public void testEquals() {
        Option option1 = new Option("a", "description");
        Option option2 = new Option("a", "description");
        assertTrue(option1.equals(option2));
        assertFalse(option1.equals(null));
        assertFalse(option1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Option option1 = new Option("a", "description");
        Option option2 = new Option("a", "description");
        assertEquals(option1.hashCode(), option2.hashCode());
    }

    @Test
    public void testClone() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        Option clonedOption = (Option) option.clone();
        assertEquals(option, clonedOption);
        assertNotSame(option, clonedOption);
        assertEquals(option.getValuesList(), clonedOption.getValuesList());
    }

    @Test
    public void testClearValues() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.clearValues();
        assertTrue(option.getValuesList().isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValue() {
        option.addValue("value");
    }
}
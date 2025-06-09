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
        option = new Option("a", "testOption");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidOption() {
        new Option("", "description");
    }

    @Test
    public void testConstructorWithValidOption() {
        Option opt = new Option("b", "longOpt", true, "description");
        assertEquals("b", opt.getOpt());
        assertEquals("longOpt", opt.getLongOpt());
        assertEquals("description", opt.getDescription());
        assertTrue(opt.hasArg());
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
    public void testGetTypeAndSetType() {
        option.setType(String.class);
        assertEquals(String.class, option.getType());
    }

    @Test
    public void testGetLongOptAndSetLongOpt() {
        option.setLongOpt("newLongOpt");
        assertEquals("newLongOpt", option.getLongOpt());
    }

    @Test
    public void testSetOptionalArgAndHasOptionalArg() {
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
    public void testGetDescriptionAndSetDescription() {
        option.setDescription("newDescription");
        assertEquals("newDescription", option.getDescription());
    }

    @Test
    public void testIsRequiredAndSetRequired() {
        option.setRequired(true);
        assertTrue(option.isRequired());
    }

    @Test
    public void testSetArgNameAndGetArgName() {
        option.setArgName("newArgName");
        assertEquals("newArgName", option.getArgName());
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
    public void testSetArgsAndGetArgs() {
        option.setArgs(3);
        assertEquals(3, option.getArgs());
    }

    @Test
    public void testSetValueSeparatorAndGetValueSeparator() {
        option.setValueSeparator(',');
        assertEquals(',', option.getValueSeparator());
    }

    @Test
    public void testHasValueSeparator() {
        assertFalse(option.hasValueSeparator());
        option.setValueSeparator(',');
        assertTrue(option.hasValueSeparator());
    }

    @Test
    public void testAddValueForProcessing() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        assertEquals("value1", option.getValue(0));
        assertEquals("value2", option.getValue(1));
    }

    @Test(expected = RuntimeException.class)
    public void testAddValueForProcessingNoArgsAllowed() {
        option.addValueForProcessing("value");
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
        assertEquals("value2", option.getValue(1));
    }

    @Test
    public void testGetValueWithDefault() {
        assertEquals("default", option.getValue("default"));
        option.setArgs(1);
        option.addValueForProcessing("value");
        assertEquals("value", option.getValue("default"));
    }

    @Test
    public void testGetValues() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        String[] values = option.getValues();
        assertArrayEquals(new String[]{"value1", "value2"}, values);
    }

    @Test
    public void testGetValuesList() {
        option.setArgs(2);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        List valuesList = option.getValuesList();
        assertEquals(2, valuesList.size());
        assertEquals("value1", valuesList.get(0));
        assertEquals("value2", valuesList.get(1));
    }

    @Test
    public void testToString() {
        option.setDescription("description");
        option.setArgs(1);
        String expected = "[ option: a  [ARG] :: description ]";
        assertEquals(expected, option.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Option option1 = new Option("a", "longOpt", true, "description");
        Option option2 = new Option("a", "longOpt", true, "description");
        assertTrue(option1.equals(option2));
        assertEquals(option1.hashCode(), option2.hashCode());
    }

    @Test
    public void testClone() {
        option.setArgs(1);
        option.addValueForProcessing("value");
        Option clonedOption = (Option) option.clone();
        assertEquals(option, clonedOption);
        assertNotSame(option, clonedOption);
        assertEquals(option.getValuesList(), clonedOption.getValuesList());
    }

    @Test
    public void testClearValues() {
        option.setArgs(1);
        option.addValueForProcessing("value");
        option.clearValues();
        assertNull(option.getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValue() {
        option.addValue("value");
    }
}
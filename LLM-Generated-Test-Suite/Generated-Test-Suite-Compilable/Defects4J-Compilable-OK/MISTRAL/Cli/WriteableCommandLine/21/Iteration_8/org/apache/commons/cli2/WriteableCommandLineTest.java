package org.apache.commons.cli2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WriteableCommandLineTest {

    private WriteableCommandLine writeableCommandLine;
    private Option option;

    @Before
    public void setUp() {
        writeableCommandLine = mock(WriteableCommandLine.class);
        option = mock(Option.class);
    }

    @Test
    public void testAddOption() {
        writeableCommandLine.addOption(option);
        verify(writeableCommandLine).addOption(option);
    }

    @Test
    public void testAddValue() {
        Object value = "testValue";
        writeableCommandLine.addValue(option, value);
        verify(writeableCommandLine).addValue(option, value);
    }

    @Test
    public void testGetUndefaultedValues() {
        List<Object> values = new ArrayList<>();
        when(writeableCommandLine.getUndefaultedValues(option)).thenReturn(values);
        List<Object> result = writeableCommandLine.getUndefaultedValues(option);
        assertEquals(values, result);
    }

    @Test
    public void testSetDefaultValues() {
        List<Object> defaultValues = new ArrayList<>();
        writeableCommandLine.setDefaultValues(option, defaultValues);
        verify(writeableCommandLine).setDefaultValues(option, defaultValues);
    }

    @Test
    public void testAddSwitch() {
        boolean value = true;
        writeableCommandLine.addSwitch(option, value);
        verify(writeableCommandLine).addSwitch(option, value);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSwitchThrowsException() {
        doThrow(new IllegalStateException()).when(writeableCommandLine).addSwitch(option, true);
        writeableCommandLine.addSwitch(option, true);
    }

    @Test
    public void testSetDefaultSwitch() {
        Boolean defaultSwitch = true;
        writeableCommandLine.setDefaultSwitch(option, defaultSwitch);
        verify(writeableCommandLine).setDefaultSwitch(option, defaultSwitch);
    }

    @Test
    public void testAddPropertyWithOption() {
        String property = "propertyName";
        String value = "propertyValue";
        writeableCommandLine.addProperty(option, property, value);
        verify(writeableCommandLine).addProperty(option, property, value);
    }

    @Test
    public void testAddPropertyWithoutOption() {
        String property = "propertyName";
        String value = "propertyValue";
        writeableCommandLine.addProperty(property, value);
        verify(writeableCommandLine).addProperty(property, value);
    }

    @Test
    public void testLooksLikeOption() {
        String argument = "-option";
        when(writeableCommandLine.looksLikeOption(argument)).thenReturn(true);
        boolean result = writeableCommandLine.looksLikeOption(argument);
        assertTrue(result);
    }

    @Test
    public void testLooksLikeOptionFalse() {
        String argument = "notAnOption";
        when(writeableCommandLine.looksLikeOption(argument)).thenReturn(false);
        boolean result = writeableCommandLine.looksLikeOption(argument);
        assertFalse(result);
    }

    // Note: The method to set the current option is not implemented in the interface,
    // so we cannot test it directly. If it were implemented, we would add tests for it here.
}
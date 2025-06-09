package org.apache.commons.cli2;

import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.WriteableCommandLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WriteableCommandLineTest {

    private WriteableCommandLine commandLine;
    private Option mockOption;

    @Before
    public void setUp() {
        commandLine = Mockito.mock(WriteableCommandLine.class);
        mockOption = Mockito.mock(Option.class);
    }

    @Test
    public void testAddOption() {
        commandLine.addOption(mockOption);
        verify(commandLine, times(1)).addOption(mockOption);
    }

    @Test
    public void testAddValue() {
        Object value = "testValue";
        commandLine.addValue(mockOption, value);
        verify(commandLine, times(1)).addValue(mockOption, value);
    }

    @Test
    public void testSetDefaultValues() {
        List<Object> defaultValues = new ArrayList<>();
        defaultValues.add("default1");
        defaultValues.add("default2");

        commandLine.setDefaultValues(mockOption, defaultValues);
        verify(commandLine, times(1)).setDefaultValues(mockOption, defaultValues);
    }

    @Test
    public void testAddSwitch() {
        commandLine.addSwitch(mockOption, true);
        verify(commandLine, times(1)).addSwitch(mockOption, true);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSwitchThrowsException() {
        doThrow(new IllegalStateException()).when(commandLine).addSwitch(mockOption, true);
        commandLine.addSwitch(mockOption, true);
    }

    @Test
    public void testSetDefaultSwitch() {
        commandLine.setDefaultSwitch(mockOption, Boolean.TRUE);
        verify(commandLine, times(1)).setDefaultSwitch(mockOption, Boolean.TRUE);
    }

    @Test
    public void testAddProperty() {
        String property = "propertyName";
        String value = "propertyValue";

        commandLine.addProperty(property, value);
        verify(commandLine, times(1)).addProperty(property, value);
    }

    @Test
    public void testLooksLikeOption() {
        String argument = "--option";
        when(commandLine.looksLikeOption(argument)).thenReturn(true);

        boolean result = commandLine.looksLikeOption(argument);
        assertTrue(result);
        verify(commandLine, times(1)).looksLikeOption(argument);
    }

    @Test
    public void testDoesNotLookLikeOption() {
        String argument = "notAnOption";
        when(commandLine.looksLikeOption(argument)).thenReturn(false);

        boolean result = commandLine.looksLikeOption(argument);
        assertFalse(result);
        verify(commandLine, times(1)).looksLikeOption(argument);
    }
}
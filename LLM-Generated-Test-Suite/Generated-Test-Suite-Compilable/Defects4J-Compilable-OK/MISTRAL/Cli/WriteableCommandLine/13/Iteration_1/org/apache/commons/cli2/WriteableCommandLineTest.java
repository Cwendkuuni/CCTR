package org.apache.commons.cli2;

import org.apache.commons.cli2.CommandLine;
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

    private WriteableCommandLine writeableCommandLine;
    private Option mockOption;

    @Before
    public void setUp() {
        // Create a mock implementation of WriteableCommandLine
        writeableCommandLine = mock(WriteableCommandLine.class);
        mockOption = mock(Option.class);
    }

    @Test
    public void testAddOption() {
        writeableCommandLine.addOption(mockOption);
        verify(writeableCommandLine).addOption(mockOption);
    }

    @Test
    public void testAddValue() {
        Object value = "testValue";
        writeableCommandLine.addValue(mockOption, value);
        verify(writeableCommandLine).addValue(mockOption, value);
    }

    @Test
    public void testSetDefaultValues() {
        List<Object> defaultValues = new ArrayList<>();
        defaultValues.add("default1");
        defaultValues.add("default2");
        writeableCommandLine.setDefaultValues(mockOption, defaultValues);
        verify(writeableCommandLine).setDefaultValues(mockOption, defaultValues);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSwitchThrowsException() {
        doThrow(new IllegalStateException()).when(writeableCommandLine).addSwitch(mockOption, true);
        writeableCommandLine.addSwitch(mockOption, true);
    }

    @Test
    public void testAddSwitch() {
        writeableCommandLine.addSwitch(mockOption, true);
        verify(writeableCommandLine).addSwitch(mockOption, true);
    }

    @Test
    public void testSetDefaultSwitch() {
        Boolean defaultSwitch = true;
        writeableCommandLine.setDefaultSwitch(mockOption, defaultSwitch);
        verify(writeableCommandLine).setDefaultSwitch(mockOption, defaultSwitch);
    }

    @Test
    public void testAddProperty() {
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
        verify(writeableCommandLine).looksLikeOption(argument);
    }
}
package org.apache.commons.cli2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WriteableCommandLineTest {

    @Mock
    private WriteableCommandLine writeableCommandLine;

    @Mock
    private Option option;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddOption() {
        writeableCommandLine.addOption(option);
        verify(writeableCommandLine).addOption(option);
    }

    @Test
    public void testAddValue() {
        Object value = new Object();
        writeableCommandLine.addValue(option, value);
        verify(writeableCommandLine).addValue(option, value);
    }

    @Test
    public void testSetDefaultValues() {
        List<Object> defaultValues = new ArrayList<>();
        writeableCommandLine.setDefaultValues(option, defaultValues);
        verify(writeableCommandLine).setDefaultValues(option, defaultValues);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSwitchThrowsException() {
        doThrow(new IllegalStateException()).when(writeableCommandLine).addSwitch(option, true);
        writeableCommandLine.addSwitch(option, true);
    }

    @Test
    public void testAddSwitch() {
        writeableCommandLine.addSwitch(option, true);
        verify(writeableCommandLine).addSwitch(option, true);
    }

    @Test
    public void testSetDefaultSwitch() {
        writeableCommandLine.setDefaultSwitch(option, true);
        verify(writeableCommandLine).setDefaultSwitch(option, true);
    }

    @Test
    public void testAddProperty() {
        writeableCommandLine.addProperty("property", "value");
        verify(writeableCommandLine).addProperty("property", "value");
    }

    @Test
    public void testLooksLikeOption() {
        when(writeableCommandLine.looksLikeOption("argument")).thenReturn(true);
        boolean result = writeableCommandLine.looksLikeOption("argument");
        assertTrue(result);
        verify(writeableCommandLine).looksLikeOption("argument");
    }
}
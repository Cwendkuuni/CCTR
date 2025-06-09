package org.apache.commons.cli2.commandline;

import org.apache.commons.cli2.Argument;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.commandline.WriteableCommandLineImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WriteableCommandLineImplTest {

    private Option mockOption;
    private Option mockArgument;
    private List<String> arguments;
    private WriteableCommandLineImpl commandLine;

    @Before
    public void setUp() {
        mockOption = mock(Option.class);
        mockArgument = mock(Argument.class);
        when(mockOption.getPrefixes()).thenReturn(Collections.singleton("-"));
        when(mockOption.getPreferredName()).thenReturn("--option");
        when(mockOption.getTriggers()).thenReturn(Collections.singleton("--option"));
        when(mockArgument.getPreferredName()).thenReturn("--arg");
        when(mockArgument.getTriggers()).thenReturn(Collections.singleton("--arg"));

        arguments = new ArrayList<>(Arrays.asList("arg1", "arg2"));
        commandLine = new WriteableCommandLineImpl(mockOption, arguments);
    }

    @Test
    public void testAddOption() {
        commandLine.addOption(mockOption);
        assertTrue(commandLine.hasOption(mockOption));
    }

    @Test
    public void testAddValue() {
        commandLine.addValue(mockArgument, "value1");
        List values = commandLine.getValues(mockArgument, null);
        assertEquals(1, values.size());
        assertEquals("value1", values.get(0));
    }

    @Test
    public void testAddSwitch() {
        commandLine.addSwitch(mockOption, true);
        assertTrue(commandLine.getSwitch(mockOption, false));
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSwitchAlreadySet() {
        commandLine.addSwitch(mockOption, true);
        commandLine.addSwitch(mockOption, false); // should throw exception
    }

    @Test
    public void testHasOption() {
        commandLine.addOption(mockOption);
        assertTrue(commandLine.hasOption(mockOption));
    }

    @Test
    public void testGetOption() {
        commandLine.addOption(mockOption);
        assertEquals(mockOption, commandLine.getOption("--option"));
    }

    @Test
    public void testGetValuesWithDefaults() {
        List<String> defaultValues = Arrays.asList("default1", "default2");
        List values = commandLine.getValues(mockOption, defaultValues);
        assertEquals(defaultValues, values);
    }

    @Test
    public void testGetSwitchWithDefaults() {
        Boolean defaultSwitch = Boolean.TRUE;
        Boolean switchValue = commandLine.getSwitch(mockOption, defaultSwitch);
        assertEquals(defaultSwitch, switchValue);
    }

    @Test
    public void testAddProperty() {
        commandLine.addProperty("key", "value");
        assertEquals("value", commandLine.getProperty("key", "default"));
    }

    @Test
    public void testGetProperties() {
        commandLine.addProperty("key", "value");
        Set properties = commandLine.getProperties();
        assertTrue(properties.contains("key"));
    }

    @Test
    public void testLooksLikeOption() {
        assertTrue(commandLine.looksLikeOption("--option"));
        assertFalse(commandLine.looksLikeOption("arg"));
    }

    @Test
    public void testToString() {
        String expected = "arg1 arg2";
        assertEquals(expected, commandLine.toString());
    }

    @Test
    public void testGetOptions() {
        commandLine.addOption(mockOption);
        List options = commandLine.getOptions();
        assertTrue(options.contains(mockOption));
    }

    @Test
    public void testGetOptionTriggers() {
        commandLine.addOption(mockOption);
        Set triggers = commandLine.getOptionTriggers();
        assertTrue(triggers.contains("--option"));
    }

    @Test
    public void testSetDefaultValues() {
        List<String> defaults = Arrays.asList("default1", "default2");
        commandLine.setDefaultValues(mockOption, defaults);
        List values = commandLine.getValues(mockOption, null);
        assertEquals(defaults, values);
    }

    @Test
    public void testSetDefaultSwitch() {
        commandLine.setDefaultSwitch(mockOption, Boolean.TRUE);
        assertTrue(commandLine.getSwitch(mockOption, null));
    }

    @Test
    public void testGetNormalised() {
        List normalised = commandLine.getNormalised();
        assertEquals(arguments, normalised);
    }
}
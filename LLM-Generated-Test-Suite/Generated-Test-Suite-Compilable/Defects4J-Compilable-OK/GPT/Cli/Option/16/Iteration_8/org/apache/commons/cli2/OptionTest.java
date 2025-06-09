package org.apache.commons.cli2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OptionTest {

    private Option option;
    private WriteableCommandLine commandLine;
    private ListIterator<String> args;
    private Set<String> helpSettings;
    private Comparator<Option> comparator;

    @Before
    public void setUp() {
        // Assuming OptionImpl is a concrete implementation of Option
        option = mock(Option.class);
        commandLine = mock(WriteableCommandLine.class);
        args = mock(ListIterator.class);
        helpSettings = new HashSet<>();
        comparator = mock(Comparator.class);
    }

    @Test
    public void testProcess() throws OptionException {
        doNothing().when(option).process(commandLine, args);
        option.process(commandLine, args);
        verify(option, times(1)).process(commandLine, args);
    }

    @Test
    public void testDefaults() {
        doNothing().when(option).defaults(commandLine);
        option.defaults(commandLine);
        verify(option, times(1)).defaults(commandLine);
    }

    @Test
    public void testCanProcessWithString() {
        when(option.canProcess(commandLine, "arg")).thenReturn(true);
        assertTrue(option.canProcess(commandLine, "arg"));
        verify(option, times(1)).canProcess(commandLine, "arg");
    }

    @Test
    public void testCanProcessWithIterator() {
        when(option.canProcess(commandLine, args)).thenReturn(true);
        assertTrue(option.canProcess(commandLine, args));
        verify(option, times(1)).canProcess(commandLine, args);
    }

    @Test
    public void testGetTriggers() {
        Set<String> triggers = new HashSet<>(Arrays.asList("--option", "-o"));
        when(option.getTriggers()).thenReturn(triggers);
        assertEquals(triggers, option.getTriggers());
        verify(option, times(1)).getTriggers();
    }

    @Test
    public void testGetPrefixes() {
        Set<String> prefixes = new HashSet<>(Arrays.asList("--", "-"));
        when(option.getPrefixes()).thenReturn(prefixes);
        assertEquals(prefixes, option.getPrefixes());
        verify(option, times(1)).getPrefixes();
    }

    @Test
    public void testValidate() throws OptionException {
        doNothing().when(option).validate(commandLine);
        option.validate(commandLine);
        verify(option, times(1)).validate(commandLine);
    }

    @Test
    public void testHelpLines() {
        List<HelpLine> helpLines = new ArrayList<>();
        when(option.helpLines(0, helpSettings, comparator)).thenReturn(helpLines);
        assertEquals(helpLines, option.helpLines(0, helpSettings, comparator));
        verify(option, times(1)).helpLines(0, helpSettings, comparator);
    }

    @Test
    public void testAppendUsage() {
        StringBuffer buffer = new StringBuffer();
        doNothing().when(option).appendUsage(buffer, helpSettings, comparator);
        option.appendUsage(buffer, helpSettings, comparator);
        verify(option, times(1)).appendUsage(buffer, helpSettings, comparator);
    }

    @Test
    public void testGetPreferredName() {
        when(option.getPreferredName()).thenReturn("optionName");
        assertEquals("optionName", option.getPreferredName());
        verify(option, times(1)).getPreferredName();
    }

    @Test
    public void testGetDescription() {
        when(option.getDescription()).thenReturn("This is a description.");
        assertEquals("This is a description.", option.getDescription());
        verify(option, times(1)).getDescription();
    }

    @Test
    public void testGetId() {
        when(option.getId()).thenReturn(123);
        assertEquals(123, option.getId());
        verify(option, times(1)).getId();
    }

    @Test
    public void testFindOption() {
        Option foundOption = mock(Option.class);
        when(option.findOption("trigger")).thenReturn(foundOption);
        assertEquals(foundOption, option.findOption("trigger"));
        verify(option, times(1)).findOption("trigger");
    }

    @Test
    public void testIsRequired() {
        when(option.isRequired()).thenReturn(true);
        assertTrue(option.isRequired());
        verify(option, times(1)).isRequired();
    }
}
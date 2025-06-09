package org.apache.commons.cli2.option;

import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.option.GroupImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private List<Option> options;
    private GroupImpl group;
    private WriteableCommandLine commandLine;

    @Before
    public void setUp() {
        options = new ArrayList<>();
        Option mockOption = mock(Option.class);
        when(mockOption.getTriggers()).thenReturn(Collections.singleton("--mock"));
        when(mockOption.getPrefixes()).thenReturn(Collections.singleton("-"));
        options.add(mockOption);

        group = new GroupImpl(options, "testGroup", "A test group", 1, 2);
        commandLine = mock(WriteableCommandLine.class);
    }

    @Test
    public void testCanProcessWithValidOption() {
        when(commandLine.looksLikeOption("--mock")).thenReturn(true);
        assertTrue(group.canProcess(commandLine, "--mock"));
    }

    @Test
    public void testCanProcessWithInvalidOption() {
        when(commandLine.looksLikeOption("--invalid")).thenReturn(true);
        assertFalse(group.canProcess(commandLine, "--invalid"));
    }

    @Test
    public void testGetPrefixes() {
        Set<String> prefixes = group.getPrefixes();
        assertTrue(prefixes.contains("-"));
    }

    @Test
    public void testGetTriggers() {
        Set<String> triggers = group.getTriggers();
        assertTrue(triggers.contains("--mock"));
    }

    @Test
    public void testProcessValidOption() throws OptionException {
        ListIterator<String> arguments = mock(ListIterator.class);
        when(arguments.hasNext()).thenReturn(true, false);
        when(arguments.next()).thenReturn("--mock");

        group.process(commandLine, arguments);
        verify(options.get(0), times(1)).process(commandLine, arguments);
    }

    @Test
    public void testValidateWithValidOptions() throws OptionException {
        when(commandLine.hasOption(options.get(0))).thenReturn(true);
        group.validate(commandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateWithTooFewOptions() throws OptionException {
        group = new GroupImpl(options, "testGroup", "A test group", 2, 3);
        group.validate(commandLine);
    }

    @Test
    public void testGetPreferredName() {
        assertEquals("testGroup", group.getPreferredName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A test group", group.getDescription());
    }

    @Test
    public void testAppendUsage() {
        StringBuffer buffer = new StringBuffer();
        Set<DisplaySetting> helpSettings = new HashSet<>();
        helpSettings.add(DisplaySetting.DISPLAY_OPTIONAL);
        group.appendUsage(buffer, helpSettings, null);
        assertTrue(buffer.toString().contains("testGroup"));
    }

    @Test
    public void testHelpLines() {
        Set<DisplaySetting> helpSettings = new HashSet<>();
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_NAME);
        List<?> helpLines = group.helpLines(0, helpSettings, null);
        assertFalse(helpLines.isEmpty());
    }

    @Test
    public void testGetOptions() {
        List<Option> opts = group.getOptions();
        assertEquals(1, opts.size());
    }

    @Test
    public void testGetAnonymous() {
        List<Option> anon = group.getAnonymous();
        assertTrue(anon.isEmpty());
    }

    @Test
    public void testFindOption() {
        Option found = group.findOption("--mock");
        assertNotNull(found);
    }

    @Test
    public void testGetMinimum() {
        assertEquals(1, group.getMinimum());
    }

    @Test
    public void testGetMaximum() {
        assertEquals(2, group.getMaximum());
    }

    @Test
    public void testIsRequired() {
        assertTrue(group.isRequired());
    }

    @Test
    public void testDefaults() {
        group.defaults(commandLine);
        verify(options.get(0), times(1)).defaults(commandLine);
    }
}
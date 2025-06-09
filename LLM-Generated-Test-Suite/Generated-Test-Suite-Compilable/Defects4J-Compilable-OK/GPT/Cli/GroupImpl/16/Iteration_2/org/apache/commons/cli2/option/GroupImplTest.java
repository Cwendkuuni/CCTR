package org.apache.commons.cli2.option;

import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.WriteableCommandLine;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private GroupImpl group;
    private List<Option> options;
    private List<Option> anonymous;
    private WriteableCommandLine commandLine;

    @Before
    public void setUp() {
        options = new ArrayList<>();
        anonymous = new ArrayList<>();
        commandLine = mock(WriteableCommandLine.class);

        Option option1 = mock(Option.class);
        when(option1.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--option1")));
        when(option1.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        options.add(option1);

        Option option2 = mock(Option.class);
        when(option2.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--option2")));
        when(option2.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        options.add(option2);

        group = new GroupImpl(options, "testGroup", "A test group", 0, 2);
    }

    @Test
    public void testCanProcessWithValidOption() {
        when(commandLine.looksLikeOption("--option1")).thenReturn(true);
        assertTrue(group.canProcess(commandLine, "--option1"));
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
        assertTrue(triggers.contains("--option1"));
        assertTrue(triggers.contains("--option2"));
    }

    @Test
    public void testProcessValidOption() throws OptionException {
        ListIterator<String> arguments = mock(ListIterator.class);
        when(arguments.hasNext()).thenReturn(true);
        when(arguments.next()).thenReturn("--option1");

        group.process(commandLine, arguments);
        verify(options.get(0)).process(eq(commandLine), eq(arguments));
    }

    @Test(expected = OptionException.class)
    public void testValidateWithTooManyOptions() throws OptionException {
        when(commandLine.hasOption(options.get(0))).thenReturn(true);
        when(commandLine.hasOption(options.get(1))).thenReturn(true);

        group.validate(commandLine);
    }

    @Test
    public void testValidateWithValidOptions() throws OptionException {
        when(commandLine.hasOption(options.get(0))).thenReturn(true);

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
        assertEquals(2, opts.size());
    }

    @Test
    public void testGetAnonymous() {
        List<Option> anon = group.getAnonymous();
        assertTrue(anon.isEmpty());
    }

    @Test
    public void testFindOption() {
        Option found = group.findOption("--option1");
        assertNotNull(found);
    }

    @Test
    public void testGetMinimum() {
        assertEquals(0, group.getMinimum());
    }

    @Test
    public void testGetMaximum() {
        assertEquals(2, group.getMaximum());
    }

    @Test
    public void testIsRequired() {
        assertFalse(group.isRequired());
    }

    @Test
    public void testDefaults() {
        group.defaults(commandLine);
        verify(options.get(0)).defaults(commandLine);
        verify(options.get(1)).defaults(commandLine);
    }
}
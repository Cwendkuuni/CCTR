package org.apache.commons.cli2.option;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.OptionException;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private List<Option> options;
    private List<Option> anonymous;
    private GroupImpl group;
    private WriteableCommandLine commandLine;

    @Before
    public void setUp() {
        options = new ArrayList<>();
        anonymous = new ArrayList<>();
        commandLine = mock(WriteableCommandLine.class);

        Option option1 = mock(Option.class);
        when(option1.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--opt1")));
        when(option1.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        options.add(option1);

        Option option2 = mock(Option.class);
        when(option2.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--opt2")));
        when(option2.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        options.add(option2);

        group = new GroupImpl(options, "testGroup", "A test group", 1, 2);
    }

    @Test
    public void testCanProcess() {
        when(commandLine.looksLikeOption("--opt1")).thenReturn(true);
        assertTrue(group.canProcess(commandLine, "--opt1"));
        assertFalse(group.canProcess(commandLine, "--unknown"));
    }

    @Test
    public void testGetPrefixes() {
        Set<String> prefixes = group.getPrefixes();
        assertTrue(prefixes.contains("-"));
    }

    @Test
    public void testGetTriggers() {
        Set<String> triggers = group.getTriggers();
        assertTrue(triggers.contains("--opt1"));
        assertTrue(triggers.contains("--opt2"));
    }

    @Test
    public void testProcess() throws OptionException {
        ListIterator<String> arguments = Arrays.asList("--opt1", "--opt2").listIterator();
        group.process(commandLine, arguments);
        verify(options.get(0)).process(commandLine, arguments);
        verify(options.get(1)).process(commandLine, arguments);
    }

    @Test
    public void testValidate() throws OptionException {
        when(commandLine.hasOption(any(Option.class))).thenReturn(true);
        group.validate(commandLine);
        verify(options.get(0)).validate(commandLine);
        verify(options.get(1)).validate(commandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateThrowsExceptionForTooManyOptions() throws OptionException {
        when(commandLine.hasOption(any(Option.class))).thenReturn(true);
        group = new GroupImpl(options, "testGroup", "A test group", 1, 1);
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
        Option found = group.findOption("--opt1");
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
        verify(options.get(0)).defaults(commandLine);
        verify(options.get(1)).defaults(commandLine);
    }
}
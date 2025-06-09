package org.apache.commons.cli2.option;

import org.apache.commons.cli2.Argument;
import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.HelpLine;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.resource.ResourceConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private List<Option> options;
    private List<Argument> anonymous;
    private GroupImpl group;
    private WriteableCommandLine commandLine;

    @Before
    public void setUp() {
        options = new ArrayList<>();
        anonymous = new ArrayList<>();
        commandLine = mock(WriteableCommandLine.class);

        Option option1 = mock(Option.class);
        when(option1.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("-a", "--alpha")));
        when(option1.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        options.add(option1);

        Argument argument1 = mock(Argument.class);
        anonymous.add(argument1);

        group = new GroupImpl(options, "testGroup", "A test group", 1, 2, true);
    }

    @Test
    public void testCanProcess() {
        when(commandLine.looksLikeOption("-a")).thenReturn(true);
        assertTrue(group.canProcess(commandLine, "-a"));
        assertFalse(group.canProcess(commandLine, "-unknown"));
    }

    @Test
    public void testGetPrefixes() {
        Set<String> prefixes = group.getPrefixes();
        assertTrue(prefixes.contains("-"));
    }

    @Test
    public void testGetTriggers() {
        Set<String> triggers = group.getTriggers();
        assertTrue(triggers.contains("-a"));
        assertTrue(triggers.contains("--alpha"));
    }

    @Test
    public void testProcess() throws OptionException {
        List<String> args = new ArrayList<>(Arrays.asList("-a", "value"));
        ListIterator<String> iterator = args.listIterator();

        group.process(commandLine, iterator);

        verify(options.get(0), times(1)).process(commandLine, iterator);
    }

    @Test
    public void testValidate() throws OptionException {
        when(commandLine.hasOption(options.get(0))).thenReturn(true);
        group.validate(commandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateThrowsExceptionForTooManyOptions() throws OptionException {
        when(commandLine.hasOption(options.get(0))).thenReturn(true);
        when(commandLine.hasOption(anonymous.get(0))).thenReturn(true);
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

        List<HelpLine> helpLines = group.helpLines(0, helpSettings, null);

        assertFalse(helpLines.isEmpty());
    }

    @Test
    public void testGetOptions() {
        List<Option> opts = group.getOptions();
        assertEquals(1, opts.size());
    }

    @Test
    public void testGetAnonymous() {
        List<Argument> anon = group.getAnonymous();
        assertEquals(0, anon.size());
    }

    @Test
    public void testFindOption() {
        Option found = group.findOption("-a");
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
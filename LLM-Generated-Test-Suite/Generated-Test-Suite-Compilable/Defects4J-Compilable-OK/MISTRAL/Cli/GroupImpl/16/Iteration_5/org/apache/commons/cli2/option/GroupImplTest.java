package org.apache.commons.cli2.option;

import org.apache.commons.cli2.Argument;
import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.HelpLine;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.option.GroupImpl;
import org.apache.commons.cli2.resource.ResourceConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private GroupImpl group;
    private List<Option> options;
    private String name;
    private String description;
    private int minimum;
    private int maximum;

    @Before
    public void setUp() {
        options = new ArrayList<>();
        name = "testGroup";
        description = "Test Group Description";
        minimum = 1;
        maximum = 3;
        group = new GroupImpl(options, name, description, minimum, maximum);
    }

    @Test
    public void testConstructor() {
        assertNotNull(group);
        assertEquals(name, group.getPreferredName());
        assertEquals(description, group.getDescription());
        assertEquals(minimum, group.getMinimum());
        assertEquals(maximum, group.getMaximum());
    }

    @Test
    public void testCanProcess() {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        Option option = mock(Option.class);
        when(option.getTriggers()).thenReturn(Collections.singleton("test"));
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        assertTrue(group.canProcess(commandLine, "test"));
        assertFalse(group.canProcess(commandLine, "unknown"));
    }

    @Test
    public void testGetPrefixes() {
        Option option = mock(Option.class);
        when(option.getPrefixes()).thenReturn(Collections.singleton("--"));
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        Set<String> prefixes = group.getPrefixes();
        assertNotNull(prefixes);
        assertTrue(prefixes.contains("--"));
    }

    @Test
    public void testGetTriggers() {
        Option option = mock(Option.class);
        when(option.getTriggers()).thenReturn(Collections.singleton("test"));
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        Set<String> triggers = group.getTriggers();
        assertNotNull(triggers);
        assertTrue(triggers.contains("test"));
    }

    @Test
    public void testProcess() throws OptionException {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        ListIterator<String> arguments = mock(ListIterator.class);
        Option option = mock(Option.class);
        when(option.getTriggers()).thenReturn(Collections.singleton("test"));
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        when(arguments.hasNext()).thenReturn(true, false);
        when(arguments.next()).thenReturn("test");

        group.process(commandLine, arguments);

        verify(option).process(commandLine, arguments);
    }

    @Test
    public void testValidate() throws OptionException {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        Option option = mock(Option.class);
        when(option.isRequired()).thenReturn(true);
        when(commandLine.hasOption(option)).thenReturn(true);
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        group.validate(commandLine);

        verify(option).validate(commandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateTooManyOptions() throws OptionException {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        Option option1 = mock(Option.class);
        Option option2 = mock(Option.class);
        when(option1.isRequired()).thenReturn(true);
        when(option2.isRequired()).thenReturn(true);
        when(commandLine.hasOption(option1)).thenReturn(true);
        when(commandLine.hasOption(option2)).thenReturn(true);
        options.add(option1);
        options.add(option2);
        group = new GroupImpl(options, name, description, minimum, maximum);

        group.validate(commandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateTooFewOptions() throws OptionException {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        Option option = mock(Option.class);
        when(option.isRequired()).thenReturn(true);
        when(commandLine.hasOption(option)).thenReturn(false);
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        group.validate(commandLine);
    }

    @Test
    public void testAppendUsage() {
        StringBuffer buffer = new StringBuffer();
        Set<DisplaySetting> helpSettings = new HashSet<>();
        helpSettings.add(DisplaySetting.DISPLAY_OPTIONAL);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_EXPANDED);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_NAME);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_OUTER);

        Option option = mock(Option.class);
        when(option.getPreferredName()).thenReturn("option");
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        group.appendUsage(buffer, helpSettings, null);

        assertTrue(buffer.toString().contains(name));
        assertTrue(buffer.toString().contains("option"));
    }

    @Test
    public void testHelpLines() {
        Set<DisplaySetting> helpSettings = new HashSet<>();
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_NAME);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_EXPANDED);
        helpSettings.add(DisplaySetting.DISPLAY_GROUP_ARGUMENT);

        Option option = mock(Option.class);
        when(option.helpLines(anyInt(), anySet(), any(Comparator.class))).thenReturn(Collections.singletonList(new HelpLineImpl(option, 1)));
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        List<HelpLine> helpLines = group.helpLines(0, helpSettings, null);

        assertNotNull(helpLines);
        assertFalse(helpLines.isEmpty());
    }

    @Test
    public void testGetOptions() {
        Option option = mock(Option.class);
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        List<Option> groupOptions = group.getOptions();
        assertNotNull(groupOptions);
        assertEquals(1, groupOptions.size());
        assertEquals(option, groupOptions.get(0));
    }

    @Test
    public void testGetAnonymous() {
        Argument argument = mock(Argument.class);
        options.add(argument);
        group = new GroupImpl(options, name, description, minimum, maximum);

        List<Option> anonymous = group.getAnonymous();
        assertNotNull(anonymous);
        assertEquals(1, anonymous.size());
        assertEquals(argument, anonymous.get(0));
    }

    @Test
    public void testFindOption() {
        Option option = mock(Option.class);
        when(option.findOption("test")).thenReturn(option);
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        Option foundOption = group.findOption("test");
        assertNotNull(foundOption);
        assertEquals(option, foundOption);
    }

    @Test
    public void testIsRequired() {
        group = new GroupImpl(options, name, description, minimum, maximum);

        assertTrue(group.isRequired());
    }

    @Test
    public void testDefaults() {
        WriteableCommandLine commandLine = mock(WriteableCommandLine.class);
        Option option = mock(Option.class);
        options.add(option);
        group = new GroupImpl(options, name, description, minimum, maximum);

        group.defaults(commandLine);

        verify(option).defaults(commandLine);
    }
}
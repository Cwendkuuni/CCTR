package org.apache.commons.cli2.option;

import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.WriteableCommandLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupImplTest {

    private GroupImpl group;
    private List<Option> options;
    private Option mockOption1;
    private Option mockOption2;
    private WriteableCommandLine mockCommandLine;

    @Before
    public void setUp() {
        mockOption1 = mock(Option.class);
        mockOption2 = mock(Option.class);
        options = new ArrayList<>();
        options.add(mockOption1);
        options.add(mockOption2);

        when(mockOption1.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--opt1")));
        when(mockOption2.getTriggers()).thenReturn(new HashSet<>(Arrays.asList("--opt2")));

        when(mockOption1.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));
        when(mockOption2.getPrefixes()).thenReturn(new HashSet<>(Arrays.asList("-")));

        group = new GroupImpl(options, "testGroup", "A test group", 1, 2);
        mockCommandLine = mock(WriteableCommandLine.class);
    }

    @Test
    public void testCanProcessWithValidOption() {
        assertTrue(group.canProcess(mockCommandLine, "--opt1"));
    }

    @Test
    public void testCanProcessWithInvalidOption() {
        assertFalse(group.canProcess(mockCommandLine, "--invalid"));
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
    public void testProcessValidOption() throws OptionException {
        List<String> args = new ArrayList<>(Arrays.asList("--opt1"));
        ListIterator<String> iterator = args.listIterator();

        group.process(mockCommandLine, iterator);

        verify(mockOption1).process(eq(mockCommandLine), any(ListIterator.class));
    }

    @Test(expected = OptionException.class)
    public void testValidateWithTooFewOptions() throws OptionException {
        when(mockCommandLine.hasOption(mockOption1)).thenReturn(false);
        when(mockCommandLine.hasOption(mockOption2)).thenReturn(false);

        group.validate(mockCommandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateWithTooManyOptions() throws OptionException {
        when(mockCommandLine.hasOption(mockOption1)).thenReturn(true);
        when(mockCommandLine.hasOption(mockOption2)).thenReturn(true);

        group.validate(mockCommandLine);
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
        when(mockOption1.findOption("--opt1")).thenReturn(mockOption1);
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
        group.defaults(mockCommandLine);
        verify(mockOption1).defaults(mockCommandLine);
        verify(mockOption2).defaults(mockCommandLine);
    }
}
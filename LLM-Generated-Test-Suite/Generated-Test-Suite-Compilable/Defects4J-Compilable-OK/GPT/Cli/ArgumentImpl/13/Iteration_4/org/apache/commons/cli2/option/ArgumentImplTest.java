package org.apache.commons.cli2.option;

import org.apache.commons.cli2.WriteableCommandLine;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.validation.Validator;
import org.apache.commons.cli2.validation.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ArgumentImplTest {

    private ArgumentImpl argument;
    private Validator mockValidator;
    private WriteableCommandLine mockCommandLine;
    private ListIterator<String> mockArguments;

    @Before
    public void setUp() {
        mockValidator = mock(Validator.class);
        mockCommandLine = mock(WriteableCommandLine.class);
        mockArguments = mock(ListIterator.class);

        argument = new ArgumentImpl(
                "testArg",
                "A test argument",
                1,
                2,
                ArgumentImpl.DEFAULT_INITIAL_SEPARATOR,
                ArgumentImpl.DEFAULT_SUBSEQUENT_SEPARATOR,
                mockValidator,
                ArgumentImpl.DEFAULT_CONSUME_REMAINING,
                Collections.singletonList("default"),
                0
        );
    }

    @Test
    public void testGetPreferredName() {
        assertEquals("testArg", argument.getPreferredName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A test argument", argument.getDescription());
    }

    @Test
    public void testGetMinimum() {
        assertEquals(1, argument.getMinimum());
    }

    @Test
    public void testGetMaximum() {
        assertEquals(2, argument.getMaximum());
    }

    @Test
    public void testGetInitialSeparator() {
        assertEquals(ArgumentImpl.DEFAULT_INITIAL_SEPARATOR, argument.getInitialSeparator());
    }

    @Test
    public void testGetSubsequentSeparator() {
        assertEquals(ArgumentImpl.DEFAULT_SUBSEQUENT_SEPARATOR, argument.getSubsequentSeparator());
    }

    @Test
    public void testGetConsumeRemaining() {
        assertEquals(ArgumentImpl.DEFAULT_CONSUME_REMAINING, argument.getConsumeRemaining());
    }

    @Test
    public void testGetDefaultValues() {
        assertEquals(Collections.singletonList("default"), argument.getDefaultValues());
    }

    @Test
    public void testGetValidator() {
        assertEquals(mockValidator, argument.getValidator());
    }

    @Test
    public void testIsRequired() {
        assertTrue(argument.isRequired());
    }

    @Test
    public void testCanProcess() {
        assertTrue(argument.canProcess(mockCommandLine, "anyArg"));
    }

    @Test
    public void testGetPrefixes() {
        Set<String> prefixes = argument.getPrefixes();
        assertTrue(prefixes.isEmpty());
    }

    @Test
    public void testGetTriggers() {
        Set<String> triggers = argument.getTriggers();
        assertTrue(triggers.isEmpty());
    }

    @Test
    public void testStripBoundaryQuotes() {
        assertEquals("value", argument.stripBoundaryQuotes("\"value\""));
        assertEquals("value", argument.stripBoundaryQuotes("value"));
    }

    @Test
    public void testProcessValues() throws OptionException {
        when(mockArguments.hasNext()).thenReturn(true, false);
        when(mockArguments.next()).thenReturn("value");

        argument.processValues(mockCommandLine, mockArguments, argument);

        verify(mockCommandLine).addValue(argument, "value");
    }

    @Test(expected = OptionException.class)
    public void testProcessValuesThrowsExceptionWhenTooManyValues() throws OptionException {
        when(mockArguments.hasNext()).thenReturn(true, true, true);
        when(mockArguments.next()).thenReturn("value1", "value2", "value3");

        argument.processValues(mockCommandLine, mockArguments, argument);
    }

    @Test
    public void testValidate() throws OptionException, InvalidArgumentException {
        List<String> values = new ArrayList<>();
        values.add("value1");
        when(mockCommandLine.getValues(argument)).thenReturn(values);

        argument.validate(mockCommandLine);

        verify(mockValidator).validate(values);
    }

    @Test(expected = OptionException.class)
    public void testValidateThrowsExceptionWhenTooFewValues() throws OptionException {
        when(mockCommandLine.getValues(argument)).thenReturn(Collections.emptyList());

        argument.validate(mockCommandLine);
    }

    @Test(expected = OptionException.class)
    public void testValidateThrowsExceptionWhenTooManyValues() throws OptionException {
        List<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        values.add("value3");
        when(mockCommandLine.getValues(argument)).thenReturn(values);

        argument.validate(mockCommandLine);
    }

    @Test
    public void testDefaults() {
        argument.defaults(mockCommandLine);

        verify(mockCommandLine).setDefaultValues(argument, Collections.singletonList("default"));
    }
}
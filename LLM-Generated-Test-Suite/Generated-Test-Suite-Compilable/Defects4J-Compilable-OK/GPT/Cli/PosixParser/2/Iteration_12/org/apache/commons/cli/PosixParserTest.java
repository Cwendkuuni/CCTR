package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PosixParserTest {

    private PosixParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new PosixParser();
        options = new Options();
    }

    @Test
    public void testFlattenWithStopAtNonOptionTrue() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-a", "value1", "-b", "value2", "nonOption"};
        String[] expected = {"-a", "value1", "-b", "value2", "--", "nonOption"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOptionFalse() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-a", "value1", "-b", "value2", "nonOption"};
        String[] expected = {"-a", "value1", "-b", "value2", "nonOption"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        options.addOption("a", false, "Option a");

        String[] args = {"--", "-a"};
        String[] expected = {"--", "-a"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithEqualsSign() {
        options.addOption("a", true, "Option a");

        String[] args = {"--a=value"};
        String[] expected = {"--a", "value"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithValidOptions() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithInvalidOption() {
        options.addOption("a", false, "Option a");

        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithStopAtNonOption() {
        options.addOption("a", false, "Option a");

        String[] args = {"-abc"};
        String[] expected = {"-a", "--", "bc"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionTokenWithValidOption() {
        Option mockOption = mock(Option.class);
        when(mockOption.hasArg()).thenReturn(false);
        options.addOption(mockOption);

        String[] args = {"-a"};
        String[] expected = {"-a"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionTokenWithInvalidOption() {
        String[] args = {"-z"};
        String[] expected = {"--", "-z"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }
}
package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PosixParserTest {

    private PosixParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new PosixParser();
        options = new Options();
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");
        options.addOption("c", false, "Option c");
    }

    @Test
    public void testFlattenWithValidOptions() {
        String[] arguments = {"-a", "-b", "value", "-c"};
        String[] expected = {"-a", "-b", "value", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] arguments = {"-a", "-d", "-c"};
        String[] expected = {"-a", "-d", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] arguments = {"-a", "value", "-c"};
        String[] expected = {"-a", "value", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSpecialTokens() {
        String[] arguments = {"--", "-a", "--", "-b", "value"};
        String[] expected = {"--", "-a", "--", "-b", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] arguments = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndStopAtNonOption() {
        String[] arguments = {"-abd"};
        String[] expected = {"-a", "-b", "--", "d"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndArgument() {
        String[] arguments = {"-abcvalue"};
        String[] expected = {"-a", "-b", "cvalue"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndInvalidOption() {
        String[] arguments = {"-abdvalue"};
        String[] expected = {"-a", "-b", "--", "dvalue"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithEmptyArguments() {
        String[] arguments = {};
        String[] expected = {};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] arguments = {"-"};
        String[] expected = {"-"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] arguments = {"--"};
        String[] expected = {"--"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionEqualsValue() {
        String[] arguments = {"--option=value"};
        String[] expected = {"--option", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionEqualsValueAndStopAtNonOption() {
        String[] arguments = {"--option=value", "nonOption"};
        String[] expected = {"--option", "value", "nonOption"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }
}
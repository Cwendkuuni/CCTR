package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

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
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "arg1", "--", "arg2", "-b", "value"};
        String[] expected = {"-a", "arg1", "--", "arg2", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "arg1", "arg2", "-b", "value"};
        String[] expected = {"-a", "arg1", "arg2", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--option=value"};
        String[] expected = {"--option", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-"};
        String[] expected = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithKnownOption() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithUnknownOption() {
        String[] args = {"-xyz"};
        String[] expected = {"-xyz"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithStopAtNonOption() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionTokenWithValidOption() {
        String[] args = {"-a"};
        String[] expected = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionTokenWithInvalidOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }
}
package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

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
        options.addOption("d", false, "Option d");
        options.addOption("e", true, "Option e");
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-a", "-b", "value", "-c"};
        String[] expected = {"-a", "-b", "value", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--a", "--b=value", "--c"};
        String[] expected = {"--a", "--b", "value", "--c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b", "value"};
        String[] expected = {"-a", "--", "non-option", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b", "value"};
        String[] expected = {"-a", "non-option", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndArgument() {
        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptionAndStopAtNonOption() {
        String[] args = {"-x"};
        String[] expected = {"--", "-x"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithMixedOptions() {
        String[] args = {"-a", "--b=value", "-c", "non-option", "-d"};
        String[] expected = {"-a", "--b", "value", "-c", "non-option", "-d"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDashAsArgument() {
        String[] args = {"--"};
        String[] expected = {"--"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
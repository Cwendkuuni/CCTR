package org.apache.commons.cli;

import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GnuParserTest {

    private GnuParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new GnuParser();
        options = new Options();
        options.addOption(new Option("a", "alpha"));
        options.addOption(new Option("b", "beta"));
        options.addOption(new Option("D", "delta"));
    }

    @Test
    public void testFlattenWithValidOptions() {
        String[] arguments = {"-a", "-b", "--delta=value"};
        String[] expected = {"-a", "-b", "--delta=value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        String[] arguments = {"-c", "-d", "--epsilon=value"};
        String[] expected = {"-c", "-d", "--epsilon=value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithMixedOptions() {
        String[] arguments = {"-a", "-c", "--delta=value", "--epsilon=value"};
        String[] expected = {"-a", "-c", "--delta=value", "--epsilon=value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] arguments = {"-a", "nonOption", "-b"};
        String[] expected = {"-a", "nonOption", "-b"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] arguments = {"-a", "--", "nonOption", "-b"};
        String[] expected = {"-a", "--", "nonOption", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] arguments = {"-a", "-", "nonOption", "-b"};
        String[] expected = {"-a", "-", "nonOption", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSpecialPropertiesOption() {
        String[] arguments = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        String[] result = parser.flatten(options, arguments, false);
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
    public void testFlattenWithNullArguments() {
        String[] arguments = null;
        try {
            parser.flatten(options, arguments, false);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
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
        String[] arguments = {"-a", "-b", "-Dproperty=value"};
        String[] expected = {"-a", "-b", "-D", "property=value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        String[] arguments = {"-x", "-y", "-z"};
        String[] expected = {"-x", "-y", "-z"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] arguments = {"-a", "value", "-b"};
        String[] expected = {"-a", "value", "-b"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] arguments = {"-a", "--", "value", "-b"};
        String[] expected = {"-a", "--", "value", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] arguments = {"-a", "-", "value", "-b"};
        String[] expected = {"-a", "-", "value", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithMixedOptions() {
        String[] arguments = {"-a", "-Dproperty=value", "value", "-b"};
        String[] expected = {"-a", "-D", "property=value", "value", "-b"};
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
    public void testFlattenWithOnlyNonOptions() {
        String[] arguments = {"value1", "value2", "value3"};
        String[] expected = {"value1", "value2", "value3"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }
}
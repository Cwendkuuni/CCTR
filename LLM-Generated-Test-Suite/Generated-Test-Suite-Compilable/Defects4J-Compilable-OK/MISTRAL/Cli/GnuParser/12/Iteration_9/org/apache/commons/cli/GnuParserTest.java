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
        options.addOption(new Option("c", "charlie"));
    }

    @Test
    public void testFlattenWithValidOptions() {
        String[] arguments = {"-a", "-b", "-c"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        String[] arguments = {"-d", "-e", "-f"};
        String[] expected = {"-d", "-e", "-f"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithMixedOptions() {
        String[] arguments = {"-a", "-d", "-b"};
        String[] expected = {"-a", "-d", "-b"};
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
    public void testFlattenWithDoubleHyphen() {
        String[] arguments = {"-a", "--", "value", "-b"};
        String[] expected = {"-a", "--", "value", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] arguments = {"-a", "-", "value", "-b"};
        String[] expected = {"-a", "-", "value", "-b"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSpecialPropertiesOption() {
        options.addOption(new Option("D", "delta"));
        String[] arguments = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithNonOptionAfterDoubleHyphen() {
        String[] arguments = {"-a", "--", "value", "-b"};
        String[] expected = {"-a", "--", "value", "-b"};
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
    public void testFlattenWithSingleArgument() {
        String[] arguments = {"-a"};
        String[] expected = {"-a"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }
}
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
        options.addOption("b", true, "Option b with argument");
        options.addOption("c", false, "Option c");
        options.addOption("d", false, "Option d");
        options.addOption("e", false, "Option e");
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "-b", "value", "non-option", "-c"};
        String[] expected = {"-a", "-b", "value", "--", "non-option", "-c"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "-b", "value", "non-option", "-c"};
        String[] expected = {"-a", "-b", "value", "non-option", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"-a", "--", "-b", "value"};
        String[] expected = {"-a", "--", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithLongOption() {
        options.addOption("long", false, "Long option");
        String[] args = {"--long", "-a"};
        String[] expected = {"--long", "-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithLongOptionAndValue() {
        options.addOption("long", true, "Long option with value");
        String[] args = {"--long=value", "-a"};
        String[] expected = {"--long", "value", "-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidLongOption() {
        String[] args = {"--invalid", "-a"};
        String[] expected = {"--", "--invalid", "-a"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithValidOptions() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithOptionAndArgument() {
        String[] args = {"-bvalue"};
        String[] expected = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithInvalidOption() {
        String[] args = {"-z"};
        String[] expected = {"-z"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithStopAtNonOption() {
        String[] args = {"-abz"};
        String[] expected = {"-a", "-b", "--", "z"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }
}
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
        options.addOption("d", false, "Option d");
        options.addOption("e", true, "Option e");
    }

    @Test
    public void testFlattenWithNoOptions() {
        String[] args = {"arg1", "arg2"};
        String[] expected = {"arg1", "arg2"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-"};
        String[] expected = {"-"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--"};
        String[] expected = {"--"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithValidOption() {
        String[] args = {"-a"};
        String[] expected = {"-a"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithOptionAndArgument() {
        String[] args = {"-b", "value"};
        String[] expected = {"-b", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBurstableOption() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "arg1", "-b", "value"};
        String[] expected = {"-a", "--", "arg1", "-b", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithLongOption() {
        String[] args = {"--option"};
        String[] expected = {"--", "--option"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithLongOptionWithEquals() {
        options.addOption("option", true, "Long option");
        String[] args = {"--option=value"};
        String[] expected = {"--option", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithNonOptionToken() {
        String[] args = {"arg1", "-a", "arg2"};
        String[] expected = {"arg1", "-a", "arg2"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithMixedOptionsAndArguments() {
        String[] args = {"-a", "arg1", "-b", "value", "-c"};
        String[] expected = {"-a", "arg1", "-b", "value", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }
}
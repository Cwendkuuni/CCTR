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
        options.addOption("d", true, "Option d");
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
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "arg1", "-b", "value"};
        String[] expected = {"-a", "arg1", "-b", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBurstTokenAndArgument() {
        String[] args = {"-bdvalue"};
        String[] expected = {"-b", "-d", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithLongOption() {
        options.addOption("long", false, "Long option");
        String[] args = {"--long"};
        String[] expected = {"--long"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithLongOptionAndValue() {
        options.addOption("long", true, "Long option with value");
        String[] args = {"--long=value"};
        String[] expected = {"--long", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithNonOptionToken() {
        String[] args = {"arg1", "-a", "arg2"};
        String[] expected = {"arg1", "-a", "arg2"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }
}
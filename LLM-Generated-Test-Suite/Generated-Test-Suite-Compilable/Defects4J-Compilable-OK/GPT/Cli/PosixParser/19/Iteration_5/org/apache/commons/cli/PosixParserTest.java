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
        options.addOption("c", "cOption", true, "Option c");
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
        String[] args = {"--cOption=value", "-a", "arg1"};
        String[] expected = {"--cOption", "value", "-a", "arg1"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-", "arg1", "-b", "value"};
        String[] expected = {"-", "arg1", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc", "value"};
        String[] expected = {"-a", "-b", "c", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptionAndStopAtNonOption() {
        String[] args = {"-x", "arg1", "-b", "value"};
        String[] expected = {"--", "x", "arg1", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptionWithoutStopAtNonOption() {
        String[] args = {"-x", "arg1", "-b", "value"};
        String[] expected = {"-x", "arg1", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionHavingArgument() {
        String[] args = {"-b", "value", "-a"};
        String[] expected = {"-b", "value", "-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionHavingNoArgument() {
        String[] args = {"-a", "-b", "value"};
        String[] expected = {"-a", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
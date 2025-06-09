package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
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
        String[] args = {"--cOption=value"};
        String[] expected = {"--cOption", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptionAndStopAtNonOption() {
        String[] args = {"-x", "arg1"};
        String[] expected = {"--", "x", "arg1"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptionWithoutStopAtNonOption() {
        String[] args = {"-x", "arg1"};
        String[] expected = {"-x", "arg1"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};
        String[] result = parser.flatten(options, args, false);
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
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithValidOptions() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstTokenWithInvalidOption() {
        String[] args = {"-ax"};
        String[] expected = {"-a", "-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
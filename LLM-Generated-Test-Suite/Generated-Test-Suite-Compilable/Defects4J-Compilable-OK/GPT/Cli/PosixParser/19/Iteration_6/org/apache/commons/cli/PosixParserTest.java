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
        options.addOption("c", "cOption", false, "Option c");
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
    public void testFlattenWithOptionAndArgument() {
        String[] args = {"-b", "value"};
        String[] expected = {"-b", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "nonOption", "-b", "value"};
        String[] expected = {"-a", "--", "nonOption", "-b", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithEqualsSign() {
        String[] args = {"--cOption=value"};
        String[] expected = {"--cOption", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        String[] args = {"-a", "-b", "value", "-c"};
        String[] expected = {"-a", "-b", "value", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithNonOptionArgument() {
        String[] args = {"nonOption"};
        String[] expected = {"nonOption"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOptionAndInvalidOption() {
        String[] args = {"-a", "nonOption", "-x"};
        String[] expected = {"-a", "--", "nonOption", "-x"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }
}
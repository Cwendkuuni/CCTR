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
    public void testFlattenWithMultipleOptions() {
        String[] args = {"-a", "-b", "value", "-c"};
        String[] expected = {"-a", "-b", "value", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] expected = {"-a", "--", "non-option", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithEqualsInDoubleDash() {
        String[] args = {"--option=value"};
        String[] expected = {"--option", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithNonOptionArgument() {
        String[] args = {"non-option"};
        String[] expected = {"non-option"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOptionAndUnknownOption() {
        String[] args = {"-x", "non-option"};
        String[] expected = {"-x", "--", "non-option"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }
}
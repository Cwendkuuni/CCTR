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
        options.addOption("c", false, "Option c");
        options.addOption("d", true, "Option d");
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-"}, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--"}, result);
    }

    @Test
    public void testFlattenWithOptionAndArgument() {
        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "non-option", "-b", "value"}, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "non-option", "-b", "value"}, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "-c"}, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndArgument() {
        String[] args = {"-abc", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "-c", "value"}, result);
    }

    @Test
    public void testFlattenWithLongOption() {
        String[] args = {"--option=value"};
        options.addOption("option", true, "Long option");
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--option", "value"}, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-z"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-z"}, result);
    }

    @Test
    public void testFlattenWithInvalidOptionAndStopAtNonOption() {
        String[] args = {"-z"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "-z"}, result);
    }
}
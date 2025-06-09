package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PosixParserTest {

    private PosixParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new PosixParser();
        options = new Options();
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
    public void testFlattenWithOption() {
        Option option = new Option("a", "alpha", false, "alpha option");
        options.addOption(option);

        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenWithOptionAndArgument() {
        Option option = new Option("b", "beta", true, "beta option");
        options.addOption(option);

        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        Option optionA = new Option("a", "alpha", false, "alpha option");
        Option optionB = new Option("b", "beta", false, "beta option");
        options.addOption(optionA);
        options.addOption(optionB);

        String[] args = {"-ab"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        Option option = new Option("a", "alpha", false, "alpha option");
        options.addOption(option);

        String[] args = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "non-option", "-b"}, result);
    }

    @Test
    public void testFlattenWithEqualsInOption() {
        String[] args = {"--option=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--option", "value"}, result);
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-z"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-z"}, result);
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        Option optionA = new Option("a", "alpha", false, "alpha option");
        Option optionB = new Option("b", "beta", true, "beta option");
        options.addOption(optionA);
        options.addOption(optionB);

        String[] args = {"-a", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "value"}, result);
    }
}
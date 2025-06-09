package org.apache.commons.cli;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
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
        String[] arguments = {"-a", "-x", "-c"};
        String[] expected = {"-a", "-x", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] arguments = {"-a", "value", "-c"};
        String[] expected = {"-a", "value", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] arguments = {"-a", "--", "-c"};
        String[] expected = {"-a", "--", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] arguments = {"-a", "-", "-c"};
        String[] expected = {"-a", "-", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBursting() {
        String[] arguments = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstingAndStopAtNonOption() {
        String[] arguments = {"-axc"};
        String[] expected = {"-a", "x", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgument() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] arguments = {"-d", "value"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] arguments = {"-d", "value", "-c"};
        String[] expected = {"-d", "value", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBursting() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] arguments = {"-dvalue"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBurstingAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] arguments = {"-dvalue", "-c"};
        String[] expected = {"-d", "value", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArguments() {
        options.addOption(new Option("e", "epsilon", true, "epsilon option"));
        String[] arguments = {"-e", "value1", "value2"};
        String[] expected = {"-e", "value1", "value2"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndStopAtNonOption() {
        options.addOption(new Option("e", "epsilon", true, "epsilon option"));
        String[] arguments = {"-e", "value1", "value2", "-c"};
        String[] expected = {"-e", "value1", "value2", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndBursting() {
        options.addOption(new Option("e", "epsilon", true, "epsilon option"));
        String[] arguments = {"-evalue1value2"};
        String[] expected = {"-e", "value1value2"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndBurstingAndStopAtNonOption() {
        options.addOption(new Option("e", "epsilon", true, "epsilon option"));
        String[] arguments = {"-evalue1value2", "-c"};
        String[] expected = {"-e", "value1value2", "-c"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }
}
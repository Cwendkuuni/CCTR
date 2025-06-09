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
        String[] args = {"-a", "-b", "-c"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-a", "-d", "-c"};
        String[] expected = {"-a", "-d", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "value", "-c"};
        String[] expected = {"-a", "value", "-c"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] args = {"-a", "--", "value", "-c"};
        String[] expected = {"-a", "--", "value", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-a", "-", "value", "-c"};
        String[] expected = {"-a", "-", "value", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBursting() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstingAndInvalidOption() {
        String[] args = {"-abd"};
        String[] expected = {"-a", "-b", "-d"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstingAndStopAtNonOption() {
        String[] args = {"-abd"};
        String[] expected = {"-a", "-b", "--", "d"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgument() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] args = {"-d", "value"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] args = {"-d", "value"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBursting() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] args = {"-dvalue"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBurstingAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "delta option"));
        String[] args = {"-dvalue"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArguments() {
        options.addOption(new Option("e", "echo", true, "echo option"));
        String[] args = {"-e", "value1", "value2"};
        String[] expected = {"-e", "value1", "value2"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndStopAtNonOption() {
        options.addOption(new Option("e", "echo", true, "echo option"));
        String[] args = {"-e", "value1", "value2"};
        String[] expected = {"-e", "value1", "value2"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndBursting() {
        options.addOption(new Option("e", "echo", true, "echo option"));
        String[] args = {"-evalue1value2"};
        String[] expected = {"-e", "value1value2"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithMultipleArgumentsAndBurstingAndStopAtNonOption() {
        options.addOption(new Option("e", "echo", true, "echo option"));
        String[] args = {"-evalue1value2"};
        String[] expected = {"-e", "value1value2"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }
}
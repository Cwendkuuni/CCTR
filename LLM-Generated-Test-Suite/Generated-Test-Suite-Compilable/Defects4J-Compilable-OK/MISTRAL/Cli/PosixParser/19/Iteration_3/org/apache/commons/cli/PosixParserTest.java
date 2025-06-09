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
        options.addOption(new Option("b", "bravo"));
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
        String[] arguments = {"-a", "--", "value", "-c"};
        String[] expected = {"-a", "--", "value", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] arguments = {"-a", "-", "value", "-c"};
        String[] expected = {"-a", "-", "value", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] arguments = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstTokenAndStopAtNonOption() {
        String[] arguments = {"-axc"};
        String[] expected = {"-a", "--", "xc"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgument() {
        options.addOption(new Option("d", "delta", true, "description"));
        String[] arguments = {"-d", "value"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "description"));
        String[] arguments = {"-d", "value", "extra"};
        String[] expected = {"-d", "value", "extra"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBurstToken() {
        options.addOption(new Option("d", "delta", true, "description"));
        String[] arguments = {"-dvalue"};
        String[] expected = {"-d", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithOptionWithArgumentAndBurstTokenAndStopAtNonOption() {
        options.addOption(new Option("d", "delta", true, "description"));
        String[] arguments = {"-dvalue", "extra"};
        String[] expected = {"-d", "value", "extra"};
        String[] result = parser.flatten(options, arguments, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGobble() {
        String[] arguments = {"-a", "value1", "value2"};
        String[] expected = {"-a", "value1", "value2"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcess() {
        String[] arguments = {"-a", "value"};
        String[] expected = {"-a", "value"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionToken() {
        String[] arguments = {"-a"};
        String[] expected = {"-a"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstToken() {
        String[] arguments = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        String[] result = parser.flatten(options, arguments, false);
        assertArrayEquals(expected, result);
    }
}
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
        options.addOption(new Option("a", "alpha"));
        options.addOption(new Option("b", "beta"));
        options.addOption(new Option("c", "gamma"));
    }

    @Test
    public void testFlattenWithValidOptions() {
        String[] args = {"-a", "-b", "-c"};
        String[] expected = {"-a", "-b", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        String[] args = {"-a", "-d", "-e"};
        String[] expected = {"-a", "--", "-d", "-e"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithSpecialTokens() {
        String[] args = {"--", "-", "-a"};
        String[] expected = {"--", "-", "-a"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBursting() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "value", "-d"};
        String[] expected = {"-a", "value", "--", "-d"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithLongOption() {
        String[] args = {"--alpha=value"};
        String[] expected = {"--alpha", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testGobble() {
        String[] args = {"-a", "value1", "value2"};
        String[] expected = {"-a", "value1", "value2"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testProcess() {
        String[] args = {"-a", "value"};
        String[] expected = {"-a", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testProcessSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testProcessOptionToken() {
        String[] args = {"-a"};
        String[] expected = {"-a"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testBurstTokenWithStopAtNonOption() {
        String[] args = {"-abd"};
        String[] expected = {"-a", "-b", "--", "d"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }
}
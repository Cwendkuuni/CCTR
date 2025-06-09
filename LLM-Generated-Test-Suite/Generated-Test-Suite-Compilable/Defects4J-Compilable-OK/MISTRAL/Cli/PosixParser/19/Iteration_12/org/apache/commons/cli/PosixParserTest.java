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
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");
        options.addOption("c", false, "Option c");
    }

    @Test
    public void testFlattenWithValidOptions() {
        String[] args = {"-a", "-b", "value", "-c"};
        String[] expected = {"-a", "-b", "value", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-a", "-d", "-c"};
        String[] expected = {"-a", "-d", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "value", "-c"};
        String[] expected = {"-a", "--", "value", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "-c"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBurstTokenAndStopAtNonOption() {
        String[] args = {"-abd"};
        String[] expected = {"-a", "-b", "--", "d"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithSpecialTokens() {
        String[] args = {"--", "-a", "--option=value"};
        String[] expected = {"--", "-a", "--option", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-", "-a"};
        String[] expected = {"-", "-a"};
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
        String[] args = {"-b", "value"};
        String[] expected = {"-b", "value"};
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
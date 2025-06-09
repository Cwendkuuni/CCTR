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
    }

    @Test
    public void testFlattenWithValidOptions() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-a", "-b", "value"};
        String[] expected = {"-a", "-b", "value"};

        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "-c"};
        String[] expected = {"-a", "--", "-c"};

        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithLongOptions() {
        options.addOption("foo", false, "Option foo");
        options.addOption("bar", true, "Option bar");

        String[] args = {"--foo", "--bar=value"};
        String[] expected = {"--foo", "--bar", "value"};

        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "nonOption"};
        String[] expected = {"-a", "--", "nonOption"};

        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithBursting() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};

        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithBurstingAndStopAtNonOption() {
        options.addOption("a", false, "Option a");

        String[] args = {"-ac"};
        String[] expected = {"-a", "--", "-ac"};

        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};

        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] args = {"--"};
        String[] expected = {"--"};

        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testGobble() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "value1", "value2"};
        String[] expected = {"-a", "value1", "value2"};

        parser.flatten(options, args, false);
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testProcessNonOptionToken() {
        String[] args = {"nonOption"};
        String[] expected = {"--", "nonOption"};

        parser.flatten(options, args, true);
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testProcessOptionToken() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a"};
        String[] expected = {"-a"};

        parser.flatten(options, args, false);
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testBurstToken() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};

        parser.flatten(options, args, false);
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }
}
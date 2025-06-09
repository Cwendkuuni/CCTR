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

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithInvalidOptions() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "-c"};
        String[] expected = {"-a", "--", "-c"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithLongOptions() {
        options.addOption("foo", false, "Option foo");
        options.addOption("bar", true, "Option bar");

        String[] args = {"--foo", "--bar=value"};
        String[] expected = {"--foo", "--bar", "value"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "nonOption"};
        String[] expected = {"-a", "--", "nonOption"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBursting() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] args = {"--"};
        String[] expected = {"--"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGobble() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a", "nonOption1", "nonOption2"};
        String[] expected = {"-a", "--", "nonOption1", "nonOption2"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessNonOptionToken() {
        String[] args = {"nonOption"};
        String[] expected = {"--", "nonOption"};

        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testProcessOptionToken() {
        options.addOption("a", false, "Option a");

        String[] args = {"-a"};
        String[] expected = {"-a"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBurstToken() {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-abvalue"};
        String[] expected = {"-a", "-b", "value"};

        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
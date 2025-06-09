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
        options.addOption("long", false, "Long option");
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-"}, result);
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        String[] args = {"--"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--"}, result);
    }

    @Test
    public void testFlattenWithValidShortOption() {
        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenWithInvalidShortOption() {
        String[] args = {"-z"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "-z"}, result);
    }

    @Test
    public void testFlattenWithValidLongOption() {
        String[] args = {"--long"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--long"}, result);
    }

    @Test
    public void testFlattenWithInvalidLongOption() {
        String[] args = {"--invalid"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "--invalid"}, result);
    }

    @Test
    public void testFlattenWithOptionAndArgument() {
        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithBurstableOption() {
        String[] args = {"-abc"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "c"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "non-option", "-b"}, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "non-option", "-b"}, result);
    }

    @Test
    public void testFlattenWithOptionHavingArgumentInSameToken() {
        String[] args = {"-bvalue"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        String[] args = {"-a", "-b", "value", "--long"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "value", "--long"}, result);
    }

    @Test
    public void testFlattenWithNonOptionToken() {
        String[] args = {"non-option"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"non-option"}, result);
    }
}
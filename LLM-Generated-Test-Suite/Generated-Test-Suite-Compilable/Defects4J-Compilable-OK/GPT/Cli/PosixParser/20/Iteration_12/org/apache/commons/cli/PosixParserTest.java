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
        options.addOption("c", "cOption", false, "Option c");
        options.addOption("d", "dOption", true, "Option d");
    }

    @Test
    public void testFlattenWithNoOptions() {
        String[] args = {"arg1", "arg2"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"arg1", "arg2"}, result);
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
    public void testFlattenWithValidShortOption() {
        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenWithValidLongOption() {
        String[] args = {"--cOption"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--cOption"}, result);
    }

    @Test
    public void testFlattenWithOptionAndArgument() {
        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithLongOptionAndArgument() {
        String[] args = {"--dOption=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--dOption", "value"}, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "c"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "arg1", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "arg1", "-b", "value"}, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-x"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOptionAndInvalidOption() {
        String[] args = {"-x", "arg1"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "-x", "arg1"}, result);
    }
}
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
        options.addOption("c", "cOption", true, "Option c with long name");
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
    public void testFlattenWithOptionWithoutArg() {
        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenWithOptionWithArg() {
        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenWithLongOptionWithArg() {
        String[] args = {"--cOption", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--cOption", "value"}, result);
    }

    @Test
    public void testFlattenWithNonOptionArgument() {
        String[] args = {"-a", "nonOption"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "nonOption"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "nonOption", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "nonOption", "-b", "value"}, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "c"}, result);
    }

    @Test
    public void testFlattenWithInvalidOption() {
        String[] args = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-x"}, result);
    }

    @Test
    public void testFlattenWithInvalidOptionAndStopAtNonOption() {
        String[] args = {"-x"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "x"}, result);
    }

    @Test
    public void testFlattenWithOptionAndEqualsSign() {
        String[] args = {"--cOption=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--cOption", "value"}, result);
    }
}
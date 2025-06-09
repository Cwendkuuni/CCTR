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
        options.addOption("a", false, "simple option");
        options.addOption("b", true, "option with argument");
        options.addOption("c", "complex", false, "long option");
    }

    @Test
    public void testFlattenSimpleOption() {
        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenOptionWithArgument() {
        String[] args = {"-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-b", "value"}, result);
    }

    @Test
    public void testFlattenLongOption() {
        String[] args = {"--complex"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--complex"}, result);
    }

    @Test
    public void testFlattenLongOptionWithEquals() {
        String[] args = {"--complex=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--complex", "value"}, result);
    }

    @Test
    public void testFlattenStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "--", "non-option", "-b", "value"}, result);
    }

    @Test
    public void testFlattenBurstToken() {
        String[] args = {"-abc"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "c"}, result);
    }

    @Test
    public void testFlattenBurstTokenWithArgument() {
        String[] args = {"-abvalue"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b", "value"}, result);
    }

    @Test
    public void testFlattenUnknownOption() {
        String[] args = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-x"}, result);
    }

    @Test
    public void testFlattenUnknownOptionStopAtNonOption() {
        String[] args = {"-x", "value"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"--", "-x", "value"}, result);
    }

    @Test
    public void testFlattenSingleHyphen() {
        String[] args = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-"}, result);
    }

    @Test
    public void testFlattenDoubleHyphen() {
        String[] args = {"--"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"--"}, result);
    }
}
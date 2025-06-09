package org.apache.commons.cli;

import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GnuParserTest {

    private GnuParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new GnuParser();
        options = new Options();
        options.addOption(new Option("a", "alpha", false, "Alpha option"));
        options.addOption(new Option("b", "bravo", false, "Bravo option"));
        options.addOption(new Option("D", "debug", true, "Debug option"));
    }

    @Test
    public void testFlattenWithSingleCharacterOption() {
        String[] args = {"-a"};
        String[] expected = {"-a"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithLongOption() {
        String[] args = {"--alpha"};
        String[] expected = {"--alpha"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithNonOptionArgument() {
        String[] args = {"-a", "file.txt"};
        String[] expected = {"-a", "file.txt"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "file.txt", "-b"};
        String[] expected = {"-a", "file.txt", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"-a", "--", "-b"};
        String[] expected = {"-a", "--", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithSpecialPropertiesOption() {
        String[] args = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        String[] args = {"-a", "-b", "-Dproperty=value"};
        String[] expected = {"-a", "-b", "-D", "property=value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOptionAndUnknownOption() {
        String[] args = {"-a", "file.txt", "-x"};
        String[] expected = {"-a", "file.txt", "-x"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }
}
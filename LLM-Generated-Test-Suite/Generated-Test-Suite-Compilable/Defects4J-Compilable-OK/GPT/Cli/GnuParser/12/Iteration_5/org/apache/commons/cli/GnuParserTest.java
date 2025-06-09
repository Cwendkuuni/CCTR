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
        options.addOption(new Option("b", "beta", false, "Beta option"));
        options.addOption(new Option("D", "property", true, "Property option"));
    }

    @Test
    public void testFlattenWithSingleCharacterOption() {
        String[] args = {"-a"};
        String[] expected = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithLongOption() {
        String[] args = {"--alpha"};
        String[] expected = {"--alpha"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithPropertyOption() {
        String[] args = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] expected = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] expected = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"-a", "--", "-b"};
        String[] expected = {"-a", "--", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleDash() {
        String[] args = {"-a", "-", "-b"};
        String[] expected = {"-a", "-", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        String[] args = {"-a", "-b", "-Dproperty=value"};
        String[] expected = {"-a", "-b", "-D", "property=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
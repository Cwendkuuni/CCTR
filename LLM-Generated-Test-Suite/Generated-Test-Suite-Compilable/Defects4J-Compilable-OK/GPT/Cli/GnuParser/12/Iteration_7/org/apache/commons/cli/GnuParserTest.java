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
        options.addOption("a", false, "Option a");
        options.addOption("b", false, "Option b");
        options.addOption("c", true, "Option c");
        options.addOption("D", true, "Option D");
    }

    @Test
    public void testFlattenWithSingleCharacterOptions() {
        String[] args = {"-a", "-b", "value"};
        String[] expected = {"-a", "-b", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithLongOptions() {
        String[] args = {"--longOption"};
        String[] expected = {"--longOption"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSpecialPropertiesOption() {
        String[] args = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "nonOption", "-b"};
        String[] expected = {"-a", "nonOption", "-b"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "nonOption", "-b"};
        String[] expected = {"-a", "nonOption", "-b"};
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
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] expected = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }
}
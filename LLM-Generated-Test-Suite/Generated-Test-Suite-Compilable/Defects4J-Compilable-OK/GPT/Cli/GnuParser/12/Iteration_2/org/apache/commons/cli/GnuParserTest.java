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
        String[] args = {"-a", "-b", "-c", "value"};
        String[] expected = {"-a", "-b", "-c", "value"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--", "-a", "-b"};
        String[] expected = {"--", "-a", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] expected = {"-a", "non-option", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, true));
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] expected = {"-a", "non-option", "-b"};
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
    public void testFlattenWithMixedOptions() {
        String[] args = {"-a", "-Dproperty=value", "non-option", "--", "-b"};
        String[] expected = {"-a", "-D", "property=value", "non-option", "--", "-b"};
        assertArrayEquals(expected, parser.flatten(options, args, false));
    }
}
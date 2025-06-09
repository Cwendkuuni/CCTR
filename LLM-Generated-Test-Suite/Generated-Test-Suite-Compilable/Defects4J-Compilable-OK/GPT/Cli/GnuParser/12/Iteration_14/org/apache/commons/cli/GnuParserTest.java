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
        options.addOption("D", true, "Option D with value");
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
    public void testFlattenWithKnownOption() {
        String[] args = {"-a"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a"}, result);
    }

    @Test
    public void testFlattenWithUnknownOption() {
        String[] args = {"-x"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-x"}, result);
    }

    @Test
    public void testFlattenWithOptionAndValue() {
        String[] args = {"-Dproperty=value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-D", "property=value"}, result);
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(new String[]{"-a", "non-option", "-b"}, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "non-option", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "non-option", "-b"}, result);
    }

    @Test
    public void testFlattenWithMixedOptions() {
        String[] args = {"-a", "--", "-b", "non-option"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "--", "-b", "non-option"}, result);
    }

    @Test
    public void testFlattenWithMultipleKnownOptions() {
        String[] args = {"-a", "-b"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-a", "-b"}, result);
    }

    @Test
    public void testFlattenWithMultipleUnknownOptions() {
        String[] args = {"-x", "-y"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(new String[]{"-x", "-y"}, result);
    }
}
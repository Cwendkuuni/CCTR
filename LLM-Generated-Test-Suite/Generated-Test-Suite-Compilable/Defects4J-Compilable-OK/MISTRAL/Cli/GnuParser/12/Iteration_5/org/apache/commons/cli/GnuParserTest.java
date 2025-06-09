package org.apache.commons.cli;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GnuParserTest {

    private GnuParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new GnuParser();
        options = new Options();
    }

    @Test
    public void testFlattenWithSingleOption() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a"};
        String[] expected = {"-a"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithMultipleOptions() {
        options.addOption("a", false, "Option a");
        options.addOption("b", false, "Option b");
        String[] arguments = {"-a", "-b"};
        String[] expected = {"-a", "-b"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithOptionAndValue() {
        options.addOption("D", true, "Option D");
        String[] arguments = {"-Dproperty=value"};
        String[] expected = {"-D", "property=value"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithNonOption() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a", "nonOption"};
        String[] expected = {"-a", "nonOption"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a", "nonOption", "-b"};
        String[] expected = {"-a", "nonOption", "-b"};
        assertArrayEquals(expected, parser.flatten(options, arguments, true));
    }

    @Test
    public void testFlattenWithDoubleHyphen() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a", "--", "nonOption"};
        String[] expected = {"-a", "--", "nonOption"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a", "-", "nonOption"};
        String[] expected = {"-a", "-", "nonOption"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithUnknownOption() {
        options.addOption("a", false, "Option a");
        String[] arguments = {"-a", "-unknown"};
        String[] expected = {"-a", "-unknown"};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithEmptyArguments() {
        String[] arguments = {};
        String[] expected = {};
        assertArrayEquals(expected, parser.flatten(options, arguments, false));
    }

    @Test
    public void testFlattenWithNullArguments() {
        String[] arguments = null;
        try {
            parser.flatten(options, arguments, false);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
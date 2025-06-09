package org.apache.commons.cli;

import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new TestParser();
        options = new Options();
        options.addOption("a", "alpha", false, "Alpha option");
        options.addOption("b", "beta", true, "Beta option with argument");
        options.addOption("c", "gamma", false, "Gamma option");
    }

    @Test
    public void testParseWithNoArguments() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{});
        assertFalse(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertFalse(cmd.hasOption("c"));
    }

    @Test
    public void testParseWithSingleOption() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{"-a"});
        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertFalse(cmd.hasOption("c"));
    }

    @Test
    public void testParseWithOptionAndArgument() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{"-b", "value"});
        assertFalse(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testParseWithUnrecognizedOption() throws ParseException {
        parser.parse(options, new String[]{"-x"});
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        Properties properties = new Properties();
        properties.setProperty("c", "true");
        CommandLine cmd = parser.parse(options, new String[]{}, properties);
        assertTrue(cmd.hasOption("c"));
    }

    @Test(expected = MissingOptionException.class)
    public void testParseWithMissingRequiredOption() throws ParseException {
        options.getOption("a").setRequired(true);
        parser.parse(options, new String[]{});
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{"-a", "non-option", "-b"}, true);
        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertEquals("non-option", cmd.getArgs()[0]);
    }

    @Test
    public void testParseWithMultipleOptions() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{"-a", "-b", "value", "-c"});
        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
        assertTrue(cmd.hasOption("c"));
    }

    // Concrete subclass for testing
    private static class TestParser extends Parser {
        @Override
        protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) {
            return arguments;
        }
    }
}
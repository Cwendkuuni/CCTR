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
        parser = new ConcreteParser();
        options = new Options();
        options.addOption("a", "alpha", false, "Alpha option");
        options.addOption("b", "beta", true, "Beta option");
        options.addOption("g", "gamma", false, "Gamma option");
        options.getOption("b").setRequired(true);
    }

    @Test
    public void testSetOptions() {
        parser.setOptions(options);
        assertEquals(options, parser.getOptions());
        assertTrue(parser.getRequiredOptions().contains("b"));
    }

    @Test
    public void testParseWithNoArguments() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{});
        assertFalse(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
    }

    @Test(expected = MissingOptionException.class)
    public void testParseWithMissingRequiredOption() throws ParseException {
        parser.parse(options, new String[]{"-a"});
    }

    @Test
    public void testParseWithAllOptions() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{"-a", "-b", "value"});
        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        Properties properties = new Properties();
        properties.setProperty("g", "true");
        CommandLine cmd = parser.parse(options, new String[]{"-b", "value"}, properties);
        assertTrue(cmd.hasOption("g"));
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testParseWithUnrecognizedOption() throws ParseException {
        parser.parse(options, new String[]{"-x"});
    }

    @Test
    public void testProcessProperties() throws ParseException {
        Properties properties = new Properties();
        properties.setProperty("g", "true");
        parser.parse(options, new String[]{"-b", "value"}, properties);
        assertTrue(parser.cmd.hasOption("g"));
    }

    @Test(expected = MissingArgumentException.class)
    public void testProcessArgsWithMissingArgument() throws ParseException {
        parser.parse(options, new String[]{"-b"});
    }

    @Test
    public void testProcessOption() throws ParseException {
        parser.parse(options, new String[]{"-b", "value"});
        assertTrue(parser.cmd.hasOption("b"));
        assertEquals("value", parser.cmd.getOptionValue("b"));
    }

    // Concrete subclass for testing
    private static class ConcreteParser extends Parser {
        @Override
        protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) throws ParseException {
            return arguments;
        }
    }
}
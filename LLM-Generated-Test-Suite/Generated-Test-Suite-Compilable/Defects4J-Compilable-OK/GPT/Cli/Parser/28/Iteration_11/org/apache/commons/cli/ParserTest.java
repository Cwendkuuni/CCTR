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
        options.addOption("g", "gamma", false, "Gamma option");
    }

    @Test
    public void testParseWithNoArguments() throws ParseException {
        CommandLine cmd = parser.parse(options, new String[]{});
        assertFalse(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertFalse(cmd.hasOption("g"));
    }

    @Test
    public void testParseWithValidOptions() throws ParseException {
        String[] args = {"-a", "-b", "value"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testParseWithUnrecognizedOption() throws ParseException {
        String[] args = {"-x"};
        parser.parse(options, args);
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        String[] args = {"-a", "non-option", "-b", "value"};
        CommandLine cmd = parser.parse(options, args, true);
        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertEquals("non-option", cmd.getArgs()[0]);
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        Properties properties = new Properties();
        properties.setProperty("g", "true");
        CommandLine cmd = parser.parse(options, new String[]{}, properties);
        assertTrue(cmd.hasOption("g"));
    }

    @Test(expected = MissingOptionException.class)
    public void testCheckRequiredOptions() throws ParseException {
        options.getOption("a").setRequired(true);
        parser.parse(options, new String[]{});
    }

    @Test
    public void testProcessArgs() throws ParseException {
        String[] args = {"-b", "value"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    // Concrete subclass for testing
    private static class TestParser extends Parser {
        @Override
        protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) {
            return arguments;
        }
    }
}
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
    public void testSetOptions() {
        parser.setOptions(options);
        assertEquals(options, parser.getOptions());
    }

    @Test
    public void testGetRequiredOptions() {
        options.getOption("b").setRequired(true);
        parser.setOptions(options);
        assertTrue(parser.getRequiredOptions().contains("b"));
    }

    @Test
    public void testParseSimple() throws ParseException {
        String[] args = {"-a", "-b", "value"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testParseUnrecognizedOption() throws ParseException {
        String[] args = {"-x"};
        parser.parse(options, args);
    }

    @Test(expected = MissingOptionException.class)
    public void testParseMissingRequiredOption() throws ParseException {
        options.getOption("b").setRequired(true);
        String[] args = {"-a"};
        parser.parse(options, args);
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        Properties properties = new Properties();
        properties.setProperty("b", "valueFromProperties");
        String[] args = {"-a"};
        CommandLine cmd = parser.parse(options, args, properties);
        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("valueFromProperties", cmd.getOptionValue("b"));
    }

    @Test
    public void testParseStopAtNonOption() throws ParseException {
        String[] args = {"-a", "nonOptionArg", "-b", "value"};
        CommandLine cmd = parser.parse(options, args, true);
        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertEquals("nonOptionArg", cmd.getArgs()[0]);
    }

    @Test
    public void testProcessArgs() throws ParseException {
        String[] args = {"-b", "value"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test(expected = MissingArgumentException.class)
    public void testProcessArgsMissingArgument() throws ParseException {
        String[] args = {"-b"};
        parser.parse(options, args);
    }

    // Concrete subclass for testing
    private static class TestParser extends Parser {
        @Override
        protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) {
            return arguments;
        }
    }
}
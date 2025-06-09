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
        options.addOption("b", "beta", true, "Beta option with argument");
        options.addOption("c", "gamma", false, "Gamma option");
        options.addOption("d", "delta", true, "Delta option with argument");
    }

    @Test
    public void testParseWithoutProperties() throws ParseException {
        String[] args = {"-a", "-b", "value"};
        CommandLine cmd = parser.parse(options, args);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
        assertFalse(cmd.hasOption("c"));
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        String[] args = {"-a"};
        Properties properties = new Properties();
        properties.setProperty("b", "value");

        CommandLine cmd = parser.parse(options, args, properties);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        String[] args = {"-a", "non-option", "-b", "value"};
        CommandLine cmd = parser.parse(options, args, true);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertEquals("non-option", cmd.getArgs()[0]);
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testParseWithUnrecognizedOption() throws ParseException {
        String[] args = {"-x"};
        parser.parse(options, args);
    }

    @Test(expected = MissingArgumentException.class)
    public void testParseWithMissingArgument() throws ParseException {
        String[] args = {"-b"};
        parser.parse(options, args);
    }

    @Test(expected = MissingOptionException.class)
    public void testParseWithMissingRequiredOption() throws ParseException {
        options.getOption("a").setRequired(true);
        String[] args = {"-b", "value"};
        parser.parse(options, args);
    }

    @Test
    public void testProcessProperties() throws ParseException {
        String[] args = {"-a"};
        Properties properties = new Properties();
        properties.setProperty("b", "value");

        CommandLine cmd = parser.parse(options, args, properties);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test
    public void testCheckRequiredOptions() throws ParseException {
        options.getOption("a").setRequired(true);
        String[] args = {"-a", "-b", "value"};
        CommandLine cmd = parser.parse(options, args);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
    }

    // Concrete implementation of the abstract Parser class for testing
    private static class ConcreteParser extends Parser {
        @Override
        protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) {
            return arguments; // Simple pass-through for testing
        }
    }
}
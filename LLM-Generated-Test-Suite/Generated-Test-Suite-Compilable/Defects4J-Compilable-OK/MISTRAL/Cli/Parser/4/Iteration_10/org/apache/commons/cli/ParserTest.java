package org.apache.commons.cli;

import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Properties;

public class ParserTest {

    private Parser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new Parser() {
            @Override
            protected String[] flatten(Options opts, String[] arguments, boolean stopAtNonOption) {
                return arguments;
            }
        };
        options = new Options();
    }

    @Test
    public void testParse() throws ParseException {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-a", "-b", "value"};
        CommandLine cmd = parser.parse(options, args);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        Properties properties = new Properties();
        properties.setProperty("b", "value");

        String[] args = {"-a"};
        CommandLine cmd = parser.parse(options, args, properties);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        String[] args = {"-a", "nonOption", "-b", "value"};
        CommandLine cmd = parser.parse(options, args, true);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertEquals(2, cmd.getArgList().size());
        assertEquals("nonOption", cmd.getArgList().get(0));
        assertEquals("-b", cmd.getArgList().get(1));
    }

    @Test
    public void testParseWithPropertiesAndStopAtNonOption() throws ParseException {
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");

        Properties properties = new Properties();
        properties.setProperty("b", "value");

        String[] args = {"-a", "nonOption"};
        CommandLine cmd = parser.parse(options, args, properties, true);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
        assertEquals(1, cmd.getArgList().size());
        assertEquals("nonOption", cmd.getArgList().get(0));
    }

    @Test(expected = MissingOptionException.class)
    public void testCheckRequiredOptions() throws ParseException {
        Option option = new Option("a", "Option a");
        option.setRequired(true);
        options.addOption(option);

        String[] args = {};
        parser.parse(options, args);
    }

    @Test(expected = MissingArgumentException.class)
    public void testProcessArgs() throws ParseException {
        Option option = new Option("a", "Option a");
        option.setArgs(1);
        options.addOption(option);

        String[] args = {"-a"};
        CommandLine cmd = parser.parse(options, args);
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testProcessOption() throws ParseException {
        String[] args = {"-unknown"};
        parser.parse(options, args);
    }
}
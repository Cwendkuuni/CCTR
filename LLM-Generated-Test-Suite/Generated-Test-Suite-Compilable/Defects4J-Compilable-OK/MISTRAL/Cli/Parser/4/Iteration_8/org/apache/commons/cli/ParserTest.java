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
        String[] args = {"-a"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("a"));
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        options.addOption("a", false, "Option a");
        Properties properties = new Properties();
        properties.setProperty("a", "true");
        String[] args = {};
        CommandLine cmd = parser.parse(options, args, properties);
        assertTrue(cmd.hasOption("a"));
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        options.addOption("a", false, "Option a");
        String[] args = {"-a", "arg1"};
        CommandLine cmd = parser.parse(options, args, true);
        assertTrue(cmd.hasOption("a"));
        assertEquals(1, cmd.getArgList().size());
        assertEquals("arg1", cmd.getArgList().get(0));
    }

    @Test
    public void testParseWithPropertiesAndStopAtNonOption() throws ParseException {
        options.addOption("a", false, "Option a");
        Properties properties = new Properties();
        properties.setProperty("a", "true");
        String[] args = {"arg1"};
        CommandLine cmd = parser.parse(options, args, properties, true);
        assertTrue(cmd.hasOption("a"));
        assertEquals(1, cmd.getArgList().size());
        assertEquals("arg1", cmd.getArgList().get(0));
    }

    @Test(expected = MissingOptionException.class)
    public void testCheckRequiredOptions() throws ParseException {
        Option requiredOption = new Option("r", "required", true, "Required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);
        String[] args = {};
        parser.parse(options, args);
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testProcessOptionUnrecognized() throws ParseException {
        String[] args = {"-u"};
        parser.parse(options, args);
    }

    @Test(expected = MissingArgumentException.class)
    public void testProcessArgsMissingArgument() throws ParseException {
        Option optionWithArg = new Option("o", "option", true, "Option with argument");
        options.addOption(optionWithArg);
        String[] args = {"-o"};
        parser.parse(options, args);
    }

    @Test
    public void testProcessArgs() throws ParseException {
        Option optionWithArg = new Option("o", "option", true, "Option with argument");
        options.addOption(optionWithArg);
        String[] args = {"-o", "value"};
        CommandLine cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("o"));
        assertEquals("value", cmd.getOptionValue("o"));
    }
}
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
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");
        options.addOption("c", false, "Option c");
    }

    @Test
    public void testParse() throws ParseException {
        String[] args = {"-a", "-b", "value", "-c"};
        CommandLine cmd = parser.parse(options, args);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("value", cmd.getOptionValue("b"));
        assertTrue(cmd.hasOption("c"));
    }

    @Test
    public void testParseWithProperties() throws ParseException {
        String[] args = {"-a", "-b", "value", "-c"};
        Properties properties = new Properties();
        properties.setProperty("b", "newValue");

        CommandLine cmd = parser.parse(options, args, properties);

        assertTrue(cmd.hasOption("a"));
        assertTrue(cmd.hasOption("b"));
        assertEquals("newValue", cmd.getOptionValue("b"));
        assertTrue(cmd.hasOption("c"));
    }

    @Test
    public void testParseWithStopAtNonOption() throws ParseException {
        String[] args = {"-a", "nonOption", "-b", "value", "-c"};
        CommandLine cmd = parser.parse(options, args, true);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertFalse(cmd.hasOption("c"));
        assertEquals(4, cmd.getArgList().size());
        assertEquals("nonOption", cmd.getArgList().get(0));
        assertEquals("-b", cmd.getArgList().get(1));
        assertEquals("value", cmd.getArgList().get(2));
        assertEquals("-c", cmd.getArgList().get(3));
    }

    @Test
    public void testParseWithPropertiesAndStopAtNonOption() throws ParseException {
        String[] args = {"-a", "nonOption", "-b", "value", "-c"};
        Properties properties = new Properties();
        properties.setProperty("b", "newValue");

        CommandLine cmd = parser.parse(options, args, properties, true);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertFalse(cmd.hasOption("c"));
        assertEquals(4, cmd.getArgList().size());
        assertEquals("nonOption", cmd.getArgList().get(0));
        assertEquals("-b", cmd.getArgList().get(1));
        assertEquals("value", cmd.getArgList().get(2));
        assertEquals("-c", cmd.getArgList().get(3));
    }

    @Test(expected = MissingOptionException.class)
    public void testCheckRequiredOptions() throws ParseException {
        Option requiredOption = new Option("r", "required", true, "Required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);

        String[] args = {"-a", "-b", "value", "-c"};
        parser.parse(options, args);
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void testProcessOptionWithUnrecognizedOption() throws ParseException {
        String[] args = {"-a", "-unknown", "-b", "value", "-c"};
        parser.parse(options, args);
    }

    @Test(expected = MissingArgumentException.class)
    public void testProcessArgsWithMissingArgument() throws ParseException {
        String[] args = {"-a", "-b", "-c"};
        parser.parse(options, args);
    }
}
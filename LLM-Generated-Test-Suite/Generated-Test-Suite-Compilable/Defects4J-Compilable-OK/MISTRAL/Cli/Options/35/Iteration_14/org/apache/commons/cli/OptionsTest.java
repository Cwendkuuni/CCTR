package org.apache.commons.cli;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OptionsTest {

    private Options options;
    private OptionGroup optionGroup;
    private Option option;

    @Before
    public void setUp() {
        options = new Options();
        optionGroup = new OptionGroup();
        option = new Option("a", "alpha", false, "alpha option");
    }

    @Test
    public void testAddOptionGroup() {
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        assertTrue(options.getOptionGroups().contains(optionGroup));
        assertEquals(optionGroup, options.getOptionGroup(option));
    }

    @Test
    public void testGetOptionGroups() {
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        Collection<OptionGroup> optionGroups = options.getOptionGroups();
        assertTrue(optionGroups.contains(optionGroup));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("a", "alpha option");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha option", retrievedOption.getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("a", true, "alpha option with arg");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha option with arg", retrievedOption.getDescription());
        assertTrue(retrievedOption.hasArg());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", false, "alpha option");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha", retrievedOption.getLongOpt());
        assertEquals("alpha option", retrievedOption.getDescription());
    }

    @Test
    public void testAddOptionInstance() {
        Option opt = new Option("a", "alpha", false, "alpha option");
        options.addOption(opt);

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha", retrievedOption.getLongOpt());
        assertEquals("alpha option", retrievedOption.getDescription());
    }

    @Test
    public void testGetOptions() {
        options.addOption("a", "alpha option");
        options.addOption("b", "beta option");

        Collection<Option> opts = options.getOptions();
        assertEquals(2, opts.size());
        assertTrue(opts.contains(options.getOption("a")));
        assertTrue(opts.contains(options.getOption("b")));
    }

    @Test
    public void testGetRequiredOptions() {
        Option requiredOption = new Option("r", "required", true, "required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);

        List requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("r"));
    }

    @Test
    public void testGetOption() {
        options.addOption("a", "alpha option");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha option", retrievedOption.getDescription());
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", false, "alpha option");
        options.addOption("b", "beta", false, "beta option");

        List<String> matchingOpts = options.getMatchingOptions("a");
        assertEquals(1, matchingOpts.size());
        assertTrue(matchingOpts.contains("alpha"));
    }

    @Test
    public void testHasOption() {
        options.addOption("a", "alpha option");

        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("b"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption("a", "alpha", false, "alpha option");

        assertTrue(options.hasLongOption("alpha"));
        assertFalse(options.hasLongOption("beta"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption("a", "alpha option");

        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("b"));
    }

    @Test
    public void testGetOptionGroup() {
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        assertEquals(optionGroup, options.getOptionGroup(option));
    }

    @Test
    public void testToString() {
        options.addOption("a", "alpha option");

        String toString = options.toString();
        assertTrue(toString.contains("a"));
        assertTrue(toString.contains("alpha option"));
    }
}
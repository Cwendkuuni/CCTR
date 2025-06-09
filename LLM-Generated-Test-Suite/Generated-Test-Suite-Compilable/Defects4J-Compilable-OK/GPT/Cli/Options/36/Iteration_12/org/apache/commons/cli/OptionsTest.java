package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class OptionsTest {

    private Options options;

    @Before
    public void setUp() {
        options = new Options();
    }

    @Test
    public void testAddOptionGroup() {
        OptionGroup group = new OptionGroup();
        Option option1 = new Option("a", "alpha", false, "Alpha option");
        Option option2 = new Option("b", "beta", false, "Beta option");
        group.addOption(option1);
        group.addOption(option2);
        group.setRequired(true);

        options.addOptionGroup(group);

        Collection<OptionGroup> optionGroups = options.getOptionGroups();
        assertEquals(1, optionGroups.size());
        assertTrue(optionGroups.contains(group));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("a", "Alpha option");

        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("b"));
    }

    @Test
    public void testAddOptionWithShortNameAndArgument() {
        options.addOption("a", true, "Alpha option");

        assertTrue(options.hasOption("a"));
        Option option = options.getOption("a");
        assertTrue(option.hasArg());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", false, "Alpha option");

        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
    }

    @Test
    public void testGetOptions() {
        options.addOption("a", "alpha", false, "Alpha option");
        options.addOption("b", "beta", false, "Beta option");

        Collection<Option> opts = options.getOptions();
        assertEquals(2, opts.size());
    }

    @Test
    public void testGetRequiredOptions() {
        Option option1 = new Option("a", "alpha", false, "Alpha option");
        option1.setRequired(true);
        options.addOption(option1);

        List requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption("a", "alpha", false, "Alpha option");

        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals("alpha", option.getLongOpt());
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", false, "Alpha option");
        options.addOption("b", "beta", false, "Beta option");

        List<String> matchingOptions = options.getMatchingOptions("al");
        assertEquals(1, matchingOptions.size());
        assertTrue(matchingOptions.contains("alpha"));
    }

    @Test
    public void testHasOption() {
        options.addOption("a", "alpha", false, "Alpha option");

        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
        assertFalse(options.hasOption("b"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption("a", "alpha", false, "Alpha option");

        assertTrue(options.hasLongOption("alpha"));
        assertFalse(options.hasLongOption("beta"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption("a", "alpha", false, "Alpha option");

        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("b"));
    }

    @Test
    public void testGetOptionGroup() {
        OptionGroup group = new OptionGroup();
        Option option1 = new Option("a", "alpha", false, "Alpha option");
        group.addOption(option1);
        options.addOptionGroup(group);

        OptionGroup retrievedGroup = options.getOptionGroup(option1);
        assertNotNull(retrievedGroup);
        assertEquals(group, retrievedGroup);
    }

    @Test
    public void testToString() {
        options.addOption("a", "alpha", false, "Alpha option");
        String optionsString = options.toString();
        assertTrue(optionsString.contains("short"));
        assertTrue(optionsString.contains("long"));
    }
}
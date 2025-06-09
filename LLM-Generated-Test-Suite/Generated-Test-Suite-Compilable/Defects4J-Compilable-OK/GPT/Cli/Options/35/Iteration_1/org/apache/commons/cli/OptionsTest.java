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
        Option option1 = new Option("a", "alpha", false, "alpha option");
        Option option2 = new Option("b", "beta", false, "beta option");
        group.addOption(option1);
        group.addOption(option2);
        group.setRequired(true);

        options.addOptionGroup(group);

        assertTrue(options.getOptionGroups().contains(group));
        assertTrue(options.getRequiredOptions().contains(group));
        assertEquals(group, options.getOptionGroup(option1));
        assertEquals(group, options.getOptionGroup(option2));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("a", "alpha option");

        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("b"));
        assertEquals("alpha option", options.getOption("a").getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArgument() {
        options.addOption("a", true, "alpha option");

        assertTrue(options.hasOption("a"));
        assertTrue(options.getOption("a").hasArg());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", false, "alpha option");

        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
        assertEquals("alpha option", options.getOption("a").getDescription());
        assertEquals("alpha option", options.getOption("alpha").getDescription());
    }

    @Test
    public void testGetOptions() {
        options.addOption("a", "alpha", false, "alpha option");
        options.addOption("b", "beta", false, "beta option");

        Collection<Option> opts = options.getOptions();
        assertEquals(2, opts.size());
    }

    @Test
    public void testGetRequiredOptions() {
        Option option1 = new Option("a", "alpha", false, "alpha option");
        option1.setRequired(true);
        options.addOption(option1);

        List requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption("a", "alpha", false, "alpha option");

        Option opt = options.getOption("a");
        assertNotNull(opt);
        assertEquals("alpha", opt.getLongOpt());
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", false, "alpha option");
        options.addOption("b", "beta", false, "beta option");

        List<String> matchingOpts = options.getMatchingOptions("al");
        assertEquals(1, matchingOpts.size());
        assertTrue(matchingOpts.contains("alpha"));
    }

    @Test
    public void testHasOption() {
        options.addOption("a", "alpha", false, "alpha option");

        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
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
        options.addOption("a", "alpha", false, "alpha option");

        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("b"));
    }

    @Test
    public void testGetOptionGroup() {
        OptionGroup group = new OptionGroup();
        Option option1 = new Option("a", "alpha", false, "alpha option");
        group.addOption(option1);
        options.addOptionGroup(group);

        assertEquals(group, options.getOptionGroup(option1));
    }

    @Test
    public void testToString() {
        options.addOption("a", "alpha", false, "alpha option");
        options.addOption("b", "beta", false, "beta option");

        String optionsString = options.toString();
        assertTrue(optionsString.contains("short"));
        assertTrue(optionsString.contains("long"));
        assertTrue(optionsString.contains("alpha"));
        assertTrue(optionsString.contains("beta"));
    }
}
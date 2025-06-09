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
        option = new Option("a", "alpha", false, "alpha description");
    }

    @Test
    public void testAddOptionGroup() {
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        assertTrue(options.getOptionGroups().contains(optionGroup));
        assertFalse(option.isRequired());
        assertTrue(options.getOptions().contains(option));
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
        options.addOption("a", "alpha description");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha description", retrievedOption.getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("a", true, "alpha description");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertTrue(retrievedOption.hasArg());
        assertEquals("alpha description", retrievedOption.getDescription());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", false, "alpha description");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha", retrievedOption.getLongOpt());
        assertEquals("alpha description", retrievedOption.getDescription());
    }

    @Test
    public void testAddOptionInstance() {
        Option opt = new Option("a", "alpha", false, "alpha description");
        options.addOption(opt);

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha", retrievedOption.getLongOpt());
        assertEquals("alpha description", retrievedOption.getDescription());
    }

    @Test
    public void testGetOptions() {
        options.addOption("a", "alpha description");
        options.addOption("b", "beta description");

        Collection<Option> opts = options.getOptions();
        assertEquals(2, opts.size());
        assertTrue(opts.contains(options.getOption("a")));
        assertTrue(opts.contains(options.getOption("b")));
    }

    @Test
    public void testGetRequiredOptions() {
        Option opt = new Option("a", "alpha", false, "alpha description");
        opt.setRequired(true);
        options.addOption(opt);

        List requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption("a", "alpha description");

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals("a", retrievedOption.getOpt());
        assertEquals("alpha description", retrievedOption.getDescription());
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", false, "alpha description");
        options.addOption("b", "beta", false, "beta description");

        List<String> matchingOpts = options.getMatchingOptions("a");
        assertEquals(1, matchingOpts.size());
        assertTrue(matchingOpts.contains("alpha"));
    }

    @Test
    public void testHasOption() {
        options.addOption("a", "alpha description");

        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("b"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption("a", "alpha", false, "alpha description");

        assertTrue(options.hasLongOption("alpha"));
        assertFalse(options.hasLongOption("beta"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption("a", "alpha description");

        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("b"));
    }

    @Test
    public void testGetOptionGroup() {
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        OptionGroup retrievedGroup = options.getOptionGroup(option);
        assertNotNull(retrievedGroup);
        assertEquals(optionGroup, retrievedGroup);
    }

    @Test
    public void testToString() {
        options.addOption("a", "alpha description");

        String toString = options.toString();
        assertTrue(toString.contains("a"));
        assertTrue(toString.contains("alpha description"));
    }
}
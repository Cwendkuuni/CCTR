package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class OptionsTest {

    private Options options;
    private OptionGroup optionGroup;
    private Option option1;
    private Option option2;

    @Before
    public void setUp() {
        options = new Options();
        optionGroup = new OptionGroup();
        option1 = new Option("a", "alpha", false, "alpha option");
        option2 = new Option("b", "beta", true, "beta option");
    }

    @Test
    public void testAddOptionGroup() {
        optionGroup.addOption(option1);
        optionGroup.addOption(option2);
        options.addOptionGroup(optionGroup);

        assertTrue(options.getOptionGroups().contains(optionGroup));
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("b"));
    }

    @Test
    public void testGetOptionGroups() {
        optionGroup.addOption(option1);
        optionGroup.addOption(option2);
        options.addOptionGroup(optionGroup);

        Collection<OptionGroup> optionGroups = options.getOptionGroups();
        assertEquals(1, optionGroups.size());
        assertTrue(optionGroups.contains(optionGroup));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("a", "alpha option");
        assertTrue(options.hasOption("a"));
        assertFalse(options.hasLongOption("alpha"));
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("a", true, "alpha option");
        assertTrue(options.hasOption("a"));
        assertFalse(options.hasLongOption("alpha"));
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", true, "alpha option");
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasLongOption("alpha"));
    }

    @Test
    public void testAddOptionInstance() {
        Option option = new Option("a", "alpha", true, "alpha option");
        options.addOption(option);
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasLongOption("alpha"));
    }

    @Test
    public void testGetOptions() {
        options.addOption(option1);
        options.addOption(option2);

        Collection<Option> opts = options.getOptions();
        assertEquals(2, opts.size());
        assertTrue(opts.contains(option1));
        assertTrue(opts.contains(option2));
    }

    @Test
    public void testGetRequiredOptions() {
        option1.setRequired(true);
        options.addOption(option1);

        List<Object> requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption(option1);
        Option opt = options.getOption("a");
        assertEquals(option1, opt);
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", true, "alpha option");
        options.addOption("b", "beta", true, "beta option");

        List<String> matchingOpts = options.getMatchingOptions("a");
        assertEquals(1, matchingOpts.size());
        assertTrue(matchingOpts.contains("alpha"));
    }

    @Test
    public void testHasOption() {
        options.addOption(option1);
        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("b"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption(option1);
        assertTrue(options.hasLongOption("alpha"));
        assertFalse(options.hasLongOption("beta"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption(option1);
        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("b"));
    }

    @Test
    public void testGetOptionGroup() {
        optionGroup.addOption(option1);
        options.addOptionGroup(optionGroup);

        OptionGroup group = options.getOptionGroup(option1);
        assertEquals(optionGroup, group);
    }

    @Test
    public void testToString() {
        options.addOption(option1);
        String str = options.toString();
        assertTrue(str.contains("a"));
        assertTrue(str.contains("alpha"));
    }
}
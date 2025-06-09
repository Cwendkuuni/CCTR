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
        assertEquals(2, options.getOptions().size());
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

        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals("a", option.getOpt());
        assertEquals("alpha option", option.getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("a", true, "alpha option");

        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals("a", option.getOpt());
        assertEquals("alpha option", option.getDescription());
        assertTrue(option.hasArg());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("a", "alpha", true, "alpha option");

        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals("a", option.getOpt());
        assertEquals("alpha", option.getLongOpt());
        assertEquals("alpha option", option.getDescription());
        assertTrue(option.hasArg());
    }

    @Test
    public void testAddOptionInstance() {
        Option option = new Option("a", "alpha", true, "alpha option");
        options.addOption(option);

        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals(option, retrievedOption);
    }

    @Test
    public void testGetOptions() {
        options.addOption(option1);
        options.addOption(option2);

        Collection<Option> optionsList = options.getOptions();
        assertEquals(2, optionsList.size());
        assertTrue(optionsList.contains(option1));
        assertTrue(optionsList.contains(option2));
    }

    @Test
    public void testGetRequiredOptions() {
        option1.setRequired(true);
        options.addOption(option1);

        List requiredOptions = options.getRequiredOptions();
        assertEquals(1, requiredOptions.size());
        assertTrue(requiredOptions.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption(option1);

        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals(option1, option);
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha", false, "alpha option");
        options.addOption("b", "beta", false, "beta option");

        List<String> matchingOptions = options.getMatchingOptions("a");
        assertEquals(1, matchingOptions.size());
        assertTrue(matchingOptions.contains("alpha"));
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

        OptionGroup retrievedGroup = options.getOptionGroup(option1);
        assertNotNull(retrievedGroup);
        assertEquals(optionGroup, retrievedGroup);
    }

    @Test
    public void testToString() {
        options.addOption(option1);
        options.addOption(option2);

        String toString = options.toString();
        assertTrue(toString.contains("a"));
        assertTrue(toString.contains("alpha"));
        assertTrue(toString.contains("b"));
        assertTrue(toString.contains("beta"));
    }
}
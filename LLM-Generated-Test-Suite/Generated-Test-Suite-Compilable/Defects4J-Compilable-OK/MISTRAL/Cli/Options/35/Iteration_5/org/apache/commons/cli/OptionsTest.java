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

        Collection<OptionGroup> optionGroups = options.getOptionGroups();
        assertEquals(1, optionGroups.size());
        assertTrue(optionGroups.contains(optionGroup));
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
        options.addOption("b", true, "beta option");
        Option option = options.getOption("b");
        assertNotNull(option);
        assertEquals("b", option.getOpt());
        assertTrue(option.hasArg());
        assertEquals("beta option", option.getDescription());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("c", "charlie", false, "charlie option");
        Option option = options.getOption("c");
        assertNotNull(option);
        assertEquals("c", option.getOpt());
        assertEquals("charlie", option.getLongOpt());
        assertEquals("charlie option", option.getDescription());
    }

    @Test
    public void testAddOptionInstance() {
        Option option = new Option("d", "delta", true, "delta option");
        options.addOption(option);
        Option retrievedOption = options.getOption("d");
        assertNotNull(retrievedOption);
        assertEquals(option, retrievedOption);
    }

    @Test
    public void testGetOptions() {
        options.addOption("a", "alpha option");
        options.addOption("b", true, "beta option");
        Collection<Option> optionsList = options.getOptions();
        assertEquals(2, optionsList.size());
    }

    @Test
    public void testGetRequiredOptions() {
        Option requiredOption = new Option("r", "required", true, "required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);
        List<Object> requiredOptions = options.getRequiredOptions();
        assertEquals(1, requiredOptions.size());
        assertTrue(requiredOptions.contains("r"));
    }

    @Test
    public void testGetOption() {
        options.addOption("a", "alpha option");
        Option option = options.getOption("a");
        assertNotNull(option);
        assertEquals("a", option.getOpt());
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("a", "alpha option");
        options.addOption("b", "beta option");
        List<String> matchingOptions = options.getMatchingOptions("a");
        assertEquals(1, matchingOptions.size());
        assertTrue(matchingOptions.contains("alpha"));
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
        optionGroup.addOption(option1);
        optionGroup.addOption(option2);
        options.addOptionGroup(optionGroup);
        OptionGroup retrievedGroup = options.getOptionGroup(option1);
        assertNotNull(retrievedGroup);
        assertEquals(optionGroup, retrievedGroup);
    }

    @Test
    public void testToString() {
        options.addOption("a", "alpha option");
        String toString = options.toString();
        assertTrue(toString.contains("a"));
        assertTrue(toString.contains("alpha option"));
    }
}
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
    private Option option1;
    private Option option2;
    private OptionGroup group;

    @Before
    public void setUp() {
        options = new Options();
        option1 = new Option("a", "alpha", false, "alpha option");
        option2 = new Option("b", "beta", true, "beta option");
        group = new OptionGroup();
        group.addOption(option1);
        group.addOption(option2);
    }

    @Test
    public void testAddOptionGroup() {
        options.addOptionGroup(group);
        assertTrue(options.getOptionGroups().contains(group));
        assertFalse(option1.isRequired());
        assertFalse(option2.isRequired());
    }

    @Test
    public void testGetOptionGroups() {
        options.addOptionGroup(group);
        Collection<OptionGroup> optionGroups = options.getOptionGroups();
        assertEquals(1, optionGroups.size());
        assertTrue(optionGroups.contains(group));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("c", "charlie option");
        Option option = options.getOption("c");
        assertNotNull(option);
        assertEquals("c", option.getOpt());
        assertEquals("charlie option", option.getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("d", true, "delta option");
        Option option = options.getOption("d");
        assertNotNull(option);
        assertEquals("d", option.getOpt());
        assertTrue(option.hasArg());
        assertEquals("delta option", option.getDescription());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("e", "echo", false, "echo option");
        Option option = options.getOption("e");
        assertNotNull(option);
        assertEquals("e", option.getOpt());
        assertEquals("echo", option.getLongOpt());
        assertEquals("echo option", option.getDescription());
    }

    @Test
    public void testAddOptionInstance() {
        Option option = new Option("f", "foxtrot", true, "foxtrot option");
        options.addOption(option);
        Option retrievedOption = options.getOption("f");
        assertNotNull(retrievedOption);
        assertEquals("f", retrievedOption.getOpt());
        assertEquals("foxtrot", retrievedOption.getLongOpt());
        assertEquals("foxtrot option", retrievedOption.getDescription());
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
        Option requiredOption = new Option("r", "required", true, "required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);
        List requiredOptions = options.getRequiredOptions();
        assertEquals(1, requiredOptions.size());
        assertTrue(requiredOptions.contains("r"));
    }

    @Test
    public void testGetOption() {
        options.addOption(option1);
        Option retrievedOption = options.getOption("a");
        assertNotNull(retrievedOption);
        assertEquals(option1, retrievedOption);
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("g", "gamma", false, "gamma option");
        options.addOption("h", "hotel", false, "hotel option");
        List<String> matchingOptions = options.getMatchingOptions("g");
        assertEquals(1, matchingOptions.size());
        assertTrue(matchingOptions.contains("gamma"));
    }

    @Test
    public void testHasOption() {
        options.addOption(option1);
        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("z"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption(option1);
        assertTrue(options.hasLongOption("alpha"));
        assertFalse(options.hasLongOption("zeta"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption(option1);
        assertTrue(options.hasShortOption("a"));
        assertFalse(options.hasShortOption("z"));
    }

    @Test
    public void testGetOptionGroup() {
        options.addOptionGroup(group);
        OptionGroup retrievedGroup = options.getOptionGroup(option1);
        assertNotNull(retrievedGroup);
        assertEquals(group, retrievedGroup);
    }

    @Test
    public void testToString() {
        options.addOption(option1);
        String toString = options.toString();
        assertTrue(toString.contains("a"));
        assertTrue(toString.contains("alpha"));
    }
}
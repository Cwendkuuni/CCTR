package org.apache.commons.cli;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class OptionsTest {

    private Options options;
    private Option option;
    private OptionGroup optionGroup;

    @Before
    public void setUp() {
        options = new Options();
        option = new Option("a", "alpha", false, "Alpha option");
        optionGroup = new OptionGroup();
        optionGroup.addOption(option);
    }

    @Test
    public void testAddOptionGroup() {
        options.addOptionGroup(optionGroup);
        assertTrue(options.getOptionGroups().contains(optionGroup));
    }

    @Test
    public void testGetOptionGroups() {
        options.addOptionGroup(optionGroup);
        assertEquals(1, options.getOptionGroups().size());
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("b", "Beta option");
        assertNotNull(options.getOption("b"));
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("c", true, "Charlie option");
        assertNotNull(options.getOption("c"));
        assertTrue(options.getOption("c").hasArg());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("d", "delta", false, "Delta option");
        assertNotNull(options.getOption("d"));
        assertNotNull(options.getOption("delta"));
    }

    @Test
    public void testAddOptionInstance() {
        options.addOption(option);
        assertNotNull(options.getOption("a"));
        assertNotNull(options.getOption("alpha"));
    }

    @Test
    public void testGetOptions() {
        options.addOption(option);
        assertEquals(1, options.getOptions().size());
    }

    @Test
    public void testGetRequiredOptions() {
        Option requiredOption = new Option("r", "required", true, "Required option");
        requiredOption.setRequired(true);
        options.addOption(requiredOption);
        assertEquals(1, options.getRequiredOptions().size());
    }

    @Test
    public void testGetOption() {
        options.addOption(option);
        assertEquals(option, options.getOption("a"));
        assertEquals(option, options.getOption("alpha"));
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("e", "echo", false, "Echo option");
        options.addOption("f", "foxtrot", false, "Foxtrot option");
        assertEquals(1, options.getMatchingOptions("e").size());
        assertEquals(1, options.getMatchingOptions("f").size());
    }

    @Test
    public void testHasOption() {
        options.addOption(option);
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("alpha"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption(option);
        assertTrue(options.hasLongOption("alpha"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption(option);
        assertTrue(options.hasShortOption("a"));
    }

    @Test
    public void testGetOptionGroup() {
        options.addOptionGroup(optionGroup);
        assertEquals(optionGroup, options.getOptionGroup(option));
    }

    @Test
    public void testToString() {
        options.addOption(option);
        String result = options.toString();
        assertTrue(result.contains("a"));
        assertTrue(result.contains("alpha"));
    }
}
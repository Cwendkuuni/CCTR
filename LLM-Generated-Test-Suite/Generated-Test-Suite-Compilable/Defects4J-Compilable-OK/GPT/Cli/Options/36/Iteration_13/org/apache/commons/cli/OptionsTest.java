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

        assertTrue(options.getOptionGroups().contains(group));
        assertTrue(options.getRequiredOptions().contains(group));
        assertEquals(group, options.getOptionGroup(option1));
        assertEquals(group, options.getOptionGroup(option2));
    }

    @Test
    public void testAddOptionWithShortName() {
        options.addOption("a", "Alpha option");

        assertTrue(options.hasOption("a"));
        assertFalse(options.hasOption("alpha"));
    }

    @Test
    public void testAddOptionWithShortNameAndArgument() {
        options.addOption("b", true, "Beta option");

        assertTrue(options.hasOption("b"));
        assertFalse(options.hasOption("beta"));
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("c", "charlie", false, "Charlie option");

        assertTrue(options.hasOption("c"));
        assertTrue(options.hasOption("charlie"));
    }

    @Test
    public void testAddOptionInstance() {
        Option option = new Option("d", "delta", false, "Delta option");
        options.addOption(option);

        assertTrue(options.hasOption("d"));
        assertTrue(options.hasOption("delta"));
    }

    @Test
    public void testGetOptions() {
        Option option1 = new Option("e", "echo", false, "Echo option");
        Option option2 = new Option("f", "foxtrot", false, "Foxtrot option");
        options.addOption(option1);
        options.addOption(option2);

        Collection<Option> opts = options.getOptions();
        assertTrue(opts.contains(option1));
        assertTrue(opts.contains(option2));
    }

    @Test
    public void testGetRequiredOptions() {
        Option option1 = new Option("g", "golf", false, "Golf option");
        option1.setRequired(true);
        options.addOption(option1);

        List requiredOpts = options.getRequiredOptions();
        assertTrue(requiredOpts.contains("g"));
    }

    @Test
    public void testGetOption() {
        Option option = new Option("h", "hotel", false, "Hotel option");
        options.addOption(option);

        assertEquals(option, options.getOption("h"));
        assertEquals(option, options.getOption("hotel"));
    }

    @Test
    public void testGetMatchingOptions() {
        Option option1 = new Option("i", "india", false, "India option");
        Option option2 = new Option("j", "indigo", false, "Indigo option");
        options.addOption(option1);
        options.addOption(option2);

        List<String> matchingOpts = options.getMatchingOptions("ind");
        assertTrue(matchingOpts.contains("india"));
        assertTrue(matchingOpts.contains("indigo"));
    }

    @Test
    public void testHasOption() {
        options.addOption("k", "kilo", false, "Kilo option");

        assertTrue(options.hasOption("k"));
        assertTrue(options.hasOption("kilo"));
        assertFalse(options.hasOption("l"));
    }

    @Test
    public void testHasLongOption() {
        options.addOption("m", "mike", false, "Mike option");

        assertTrue(options.hasLongOption("mike"));
        assertFalse(options.hasLongOption("m"));
    }

    @Test
    public void testHasShortOption() {
        options.addOption("n", "november", false, "November option");

        assertTrue(options.hasShortOption("n"));
        assertFalse(options.hasShortOption("november"));
    }

    @Test
    public void testGetOptionGroup() {
        OptionGroup group = new OptionGroup();
        Option option1 = new Option("o", "oscar", false, "Oscar option");
        group.addOption(option1);
        options.addOptionGroup(group);

        assertEquals(group, options.getOptionGroup(option1));
    }

    @Test
    public void testToString() {
        options.addOption("p", "papa", false, "Papa option");
        String optionsString = options.toString();

        assertTrue(optionsString.contains("short {p="));
        assertTrue(optionsString.contains("long {papa="));
    }
}
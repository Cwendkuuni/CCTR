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
        Option opt = options.getOption("c");
        assertNotNull(opt);
        assertEquals("c", opt.getOpt());
        assertEquals("charlie option", opt.getDescription());
    }

    @Test
    public void testAddOptionWithShortNameAndArg() {
        options.addOption("d", true, "delta option");
        Option opt = options.getOption("d");
        assertNotNull(opt);
        assertEquals("d", opt.getOpt());
        assertTrue(opt.hasArg());
        assertEquals("delta option", opt.getDescription());
    }

    @Test
    public void testAddOptionWithShortAndLongName() {
        options.addOption("e", "echo", false, "echo option");
        Option opt = options.getOption("e");
        assertNotNull(opt);
        assertEquals("e", opt.getOpt());
        assertEquals("echo", opt.getLongOpt());
        assertEquals("echo option", opt.getDescription());
    }

    @Test
    public void testAddOptionInstance() {
        Option opt = new Option("f", "foxtrot", true, "foxtrot option");
        options.addOption(opt);
        Option retrievedOpt = options.getOption("f");
        assertNotNull(retrievedOpt);
        assertEquals(opt, retrievedOpt);
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
        List requiredOpts = options.getRequiredOptions();
        assertEquals(1, requiredOpts.size());
        assertTrue(requiredOpts.contains("a"));
    }

    @Test
    public void testGetOption() {
        options.addOption(option1);
        Option opt = options.getOption("a");
        assertNotNull(opt);
        assertEquals(option1, opt);
    }

    @Test
    public void testGetMatchingOptions() {
        options.addOption("g", "gamma", false, "gamma option");
        options.addOption("h", "hotel", false, "hotel option");
        List<String> matchingOpts = options.getMatchingOptions("g");
        assertEquals(1, matchingOpts.size());
        assertTrue(matchingOpts.contains("gamma"));
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
        String str = options.toString();
        assertTrue(str.contains("a"));
        assertTrue(str.contains("alpha"));
    }
}
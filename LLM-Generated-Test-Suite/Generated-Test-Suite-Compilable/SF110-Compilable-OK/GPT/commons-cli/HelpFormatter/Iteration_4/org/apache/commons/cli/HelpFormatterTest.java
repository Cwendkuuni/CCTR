package org.apache.commons.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;

import static org.junit.Assert.*;

public class HelpFormatterTest {

    private HelpFormatter helpFormatter;

    @Before
    public void setUp() {
        helpFormatter = new HelpFormatter();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(HelpFormatter.DEFAULT_WIDTH, helpFormatter.getWidth());
        assertEquals(HelpFormatter.DEFAULT_LEFT_PAD, helpFormatter.getLeftPadding());
        assertEquals(HelpFormatter.DEFAULT_DESC_PAD, helpFormatter.getDescPadding());
        assertEquals(HelpFormatter.DEFAULT_SYNTAX_PREFIX, helpFormatter.getSyntaxPrefix());
        assertEquals(System.getProperty("line.separator"), helpFormatter.getNewLine());
        assertEquals(HelpFormatter.DEFAULT_OPT_PREFIX, helpFormatter.getOptPrefix());
        assertEquals(HelpFormatter.DEFAULT_LONG_OPT_PREFIX, helpFormatter.getLongOptPrefix());
        assertEquals(HelpFormatter.DEFAULT_ARG_NAME, helpFormatter.getArgName());
        assertNotNull(helpFormatter.getOptionComparator());
    }

    @Test
    public void testSetWidth() {
        helpFormatter.setWidth(100);
        assertEquals(100, helpFormatter.getWidth());
    }

    @Test
    public void testSetLeftPadding() {
        helpFormatter.setLeftPadding(5);
        assertEquals(5, helpFormatter.getLeftPadding());
    }

    @Test
    public void testSetDescPadding() {
        helpFormatter.setDescPadding(10);
        assertEquals(10, helpFormatter.getDescPadding());
    }

    @Test
    public void testSetSyntaxPrefix() {
        helpFormatter.setSyntaxPrefix("command: ");
        assertEquals("command: ", helpFormatter.getSyntaxPrefix());
    }

    @Test
    public void testSetNewLine() {
        helpFormatter.setNewLine("\n");
        assertEquals("\n", helpFormatter.getNewLine());
    }

    @Test
    public void testSetOptPrefix() {
        helpFormatter.setOptPrefix("/");
        assertEquals("/", helpFormatter.getOptPrefix());
    }

    @Test
    public void testSetLongOptPrefix() {
        helpFormatter.setLongOptPrefix("++");
        assertEquals("++", helpFormatter.getLongOptPrefix());
    }

    @Test
    public void testSetArgName() {
        helpFormatter.setArgName("parameter");
        assertEquals("parameter", helpFormatter.getArgName());
    }

    @Test
    public void testSetOptionComparator() {
        Comparator customComparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        };
        helpFormatter.setOptionComparator(customComparator);
        assertEquals(customComparator, helpFormatter.getOptionComparator());

        helpFormatter.setOptionComparator(null);
        assertNotNull(helpFormatter.getOptionComparator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrintHelpWithNullCmdLineSyntax() {
        helpFormatter.printHelp(null, new Options());
    }

    @Test
    public void testPrintHelp() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        helpFormatter.printHelp(pw, 80, "app", "Header", options, 1, 3, "Footer", false);
        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("usage: app"));
        assertTrue(output.contains("Header"));
        assertTrue(output.contains("-a,--all"));
        assertTrue(output.contains("Show all"));
        assertTrue(output.contains("Footer"));
    }

    @Test
    public void testPrintUsage() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        helpFormatter.printUsage(pw, 80, "app", options);
        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("usage: app"));
        assertTrue(output.contains("-a,--all"));
    }

    @Test
    public void testPrintOptions() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        helpFormatter.printOptions(pw, 80, options, 1, 3);
        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("-a,--all"));
        assertTrue(output.contains("Show all"));
    }

    @Test
    public void testPrintWrapped() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        helpFormatter.printWrapped(pw, 80, "This is a long text that should be wrapped according to the specified width.");
        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("This is a long text that should be wrapped"));
    }
}
package org.apache.commons.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
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
    public void testDefaultConstructor() {
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
        Comparator customComparator = (o1, o2) -> 0;
        helpFormatter.setOptionComparator(customComparator);
        assertEquals(customComparator, helpFormatter.getOptionComparator());

        helpFormatter.setOptionComparator(null);
        assertNotNull(helpFormatter.getOptionComparator());
    }

    @Test
    public void testPrintHelp() {
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        options.addOption("b", "block", true, "Block size");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        helpFormatter.printHelp(pw, 80, "testapp", "Header", options, 1, 3, "Footer", false);
        pw.flush();

        String output = sw.toString();
        assertTrue(output.contains("usage: testapp"));
        assertTrue(output.contains("Header"));
        assertTrue(output.contains("-a,--all"));
        assertTrue(output.contains("-b,--block <arg>"));
        assertTrue(output.contains("Footer"));
    }

    @Test
    public void testPrintUsage() {
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        options.addOption("b", "block", true, "Block size");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        helpFormatter.printUsage(pw, 80, "testapp", options);
        pw.flush();

        String output = sw.toString();
        assertTrue(output.contains("usage: testapp"));
        assertTrue(output.contains("-a,--all"));
        assertTrue(output.contains("-b,--block"));
    }
}
package org.apache.commons.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
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
        helpFormatter.setDescPadding(7);
        assertEquals(7, helpFormatter.getDescPadding());
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
    public void testPrintHelpWithCmdLineSyntaxAndOptions() {
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);

        helpFormatter.printHelp(pw, 80, "testapp", null, options, 1, 3, null, false);
        pw.flush();

        String result = out.toString();
        assertTrue(result.contains("usage: testapp"));
        assertTrue(result.contains("-a,--all"));
        assertTrue(result.contains("Show all"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrintHelpWithNullCmdLineSyntax() {
        Options options = new Options();
        helpFormatter.printHelp(null, options);
    }

    @Test
    public void testPrintUsage() {
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);

        helpFormatter.printUsage(pw, 80, "testapp", options);
        pw.flush();

        String result = out.toString();
        assertTrue(result.contains("usage: testapp"));
        assertTrue(result.contains("-a,--all"));
    }

    @Test
    public void testPrintOptions() {
        Options options = new Options();
        options.addOption("a", "all", false, "Show all");
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);

        helpFormatter.printOptions(pw, 80, options, 1, 3);
        pw.flush();

        String result = out.toString();
        assertTrue(result.contains("-a,--all"));
        assertTrue(result.contains("Show all"));
    }

    @Test
    public void testRenderWrappedText() {
        StringBuffer sb = new StringBuffer();
        helpFormatter.renderWrappedText(sb, 10, 0, "This is a long text that needs wrapping.");
        String result = sb.toString();
        assertTrue(result.contains("This is a"));
        assertTrue(result.contains("long text"));
        assertTrue(result.contains("that needs"));
        assertTrue(result.contains("wrapping."));
    }

    @Test
    public void testCreatePadding() {
        String padding = helpFormatter.createPadding(5);
        assertEquals("     ", padding);
    }

    @Test
    public void testRtrim() {
        String trimmed = helpFormatter.rtrim("  text  ");
        assertEquals("  text", trimmed);
    }
}
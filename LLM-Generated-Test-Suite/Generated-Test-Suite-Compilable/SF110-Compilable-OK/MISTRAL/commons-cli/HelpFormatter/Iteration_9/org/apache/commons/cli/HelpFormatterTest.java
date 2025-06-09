package org.apache.commons.cli;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Comparator;

public class HelpFormatterTest {

    private HelpFormatter helpFormatter;

    @Before
    public void setUp() {
        helpFormatter = new HelpFormatter();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(74, helpFormatter.getWidth());
        assertEquals(1, helpFormatter.getLeftPadding());
        assertEquals(3, helpFormatter.getDescPadding());
        assertEquals("usage: ", helpFormatter.getSyntaxPrefix());
        assertEquals(System.getProperty("line.separator"), helpFormatter.getNewLine());
        assertEquals("-", helpFormatter.getOptPrefix());
        assertEquals("--", helpFormatter.getLongOptPrefix());
        assertEquals("arg", helpFormatter.getArgName());
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
        helpFormatter.setSyntaxPrefix("test: ");
        assertEquals("test: ", helpFormatter.getSyntaxPrefix());
    }

    @Test
    public void testSetNewLine() {
        helpFormatter.setNewLine("\r\n");
        assertEquals("\r\n", helpFormatter.getNewLine());
    }

    @Test
    public void testSetOptPrefix() {
        helpFormatter.setOptPrefix("/");
        assertEquals("/", helpFormatter.getOptPrefix());
    }

    @Test
    public void testSetLongOptPrefix() {
        helpFormatter.setLongOptPrefix("//");
        assertEquals("//", helpFormatter.getLongOptPrefix());
    }

    @Test
    public void testSetArgName() {
        helpFormatter.setArgName("parameter");
        assertEquals("parameter", helpFormatter.getArgName());
    }

    @Test
    public void testSetOptionComparator() {
        Comparator<Option> comparator = new Comparator<Option>() {
            @Override
            public int compare(Option o1, Option o2) {
                return o1.getOpt().compareTo(o2.getOpt());
            }
        };
        helpFormatter.setOptionComparator(comparator);
        assertEquals(comparator, helpFormatter.getOptionComparator());
    }

    @Test
    public void testPrintHelp() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        helpFormatter.printHelp(pw, 80, "cmdLineSyntax", "header", new Options(), 1, 3, "footer", false);
        pw.flush();
        String output = outputStream.toString();
        assertTrue(output.contains("cmdLineSyntax"));
        assertTrue(output.contains("header"));
        assertTrue(output.contains("footer"));
    }

    @Test
    public void testPrintUsage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        helpFormatter.printUsage(pw, 80, "cmdLineSyntax", new Options());
        pw.flush();
        String output = outputStream.toString();
        assertTrue(output.contains("cmdLineSyntax"));
    }

    @Test
    public void testPrintOptions() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        Options options = new Options();
        options.addOption("a", "alpha", false, "alpha option");
        helpFormatter.printOptions(pw, 80, options, 1, 3);
        pw.flush();
        String output = outputStream.toString();
        assertTrue(output.contains("-a"));
        assertTrue(output.contains("alpha option"));
    }

    @Test
    public void testPrintWrapped() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        helpFormatter.printWrapped(pw, 80, "This is a long text that should be wrapped.");
        pw.flush();
        String output = outputStream.toString();
        assertTrue(output.contains("This is a long text that should be wrapped."));
    }

    @Test
    public void testRenderOptions() {
        StringBuffer sb = new StringBuffer();
        Options options = new Options();
        options.addOption("a", "alpha", false, "alpha option");
        helpFormatter.renderOptions(sb, 80, options, 1, 3);
        String output = sb.toString();
        assertTrue(output.contains("-a"));
        assertTrue(output.contains("alpha option"));
    }

    @Test
    public void testRenderWrappedText() {
        StringBuffer sb = new StringBuffer();
        helpFormatter.renderWrappedText(sb, 80, 0, "This is a long text that should be wrapped.");
        String output = sb.toString();
        assertTrue(output.contains("This is a long text that should be wrapped."));
    }

    @Test
    public void testFindWrapPos() {
        String text = "This is a long text that should be wrapped.";
        int pos = helpFormatter.findWrapPos(text, 20, 0);
        assertTrue(pos > 0 && pos <= 20);
    }

    @Test
    public void testCreatePadding() {
        String padding = helpFormatter.createPadding(5);
        assertEquals("     ", padding);
    }

    @Test
    public void testRtrim() {
        String trimmed = helpFormatter.rtrim("  test  ");
        assertEquals("  test", trimmed);
    }
}
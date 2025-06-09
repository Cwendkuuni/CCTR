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
    public void testDefaultConstructor() {
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
        Options options = new Options();
        options.addOption("h", "help", false, "show help");

        helpFormatter.printHelp(pw, 80, "test", "header", options, 1, 3, "footer");
        pw.flush();

        String output = outputStream.toString();
        assertTrue(output.contains("test"));
        assertTrue(output.contains("header"));
        assertTrue(output.contains("footer"));
        assertTrue(output.contains("-h,--help"));
    }

    @Test
    public void testPrintUsage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        Options options = new Options();
        options.addOption("h", "help", false, "show help");

        helpFormatter.printUsage(pw, 80, "test", options);
        pw.flush();

        String output = outputStream.toString();
        assertTrue(output.contains("test"));
        assertTrue(output.contains("-h,--help"));
    }

    @Test
    public void testPrintOptions() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        Options options = new Options();
        options.addOption("h", "help", false, "show help");

        helpFormatter.printOptions(pw, 80, options, 1, 3);
        pw.flush();

        String output = outputStream.toString();
        assertTrue(output.contains("-h,--help"));
        assertTrue(output.contains("show help"));
    }

    @Test
    public void testPrintWrapped() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);

        helpFormatter.printWrapped(pw, 10, "This is a test string that should be wrapped.");
        pw.flush();

        String output = outputStream.toString();
        assertTrue(output.contains("This is a"));
        assertTrue(output.contains("test string"));
        assertTrue(output.contains("that should"));
        assertTrue(output.contains("be wrapped."));
    }

    @Test
    public void testRenderOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "show help");

        StringBuffer sb = new StringBuffer();
        helpFormatter.renderOptions(sb, 80, options, 1, 3);

        String output = sb.toString();
        assertTrue(output.contains("-h,--help"));
        assertTrue(output.contains("show help"));
    }

    @Test
    public void testRenderWrappedText() {
        StringBuffer sb = new StringBuffer();
        helpFormatter.renderWrappedText(sb, 10, 0, "This is a test string that should be wrapped.");

        String output = sb.toString();
        assertTrue(output.contains("This is a"));
        assertTrue(output.contains("test string"));
        assertTrue(output.contains("that should"));
        assertTrue(output.contains("be wrapped."));
    }

    @Test
    public void testFindWrapPos() {
        String text = "This is a test string that should be wrapped.";
        int pos = helpFormatter.findWrapPos(text, 10, 0);
        assertEquals(10, pos);
    }

    @Test
    public void testCreatePadding() {
        String padding = helpFormatter.createPadding(5);
        assertEquals("     ", padding);
    }

    @Test
    public void testRtrim() {
        String trimmed = helpFormatter.rtrim("test   ");
        assertEquals("test", trimmed);
    }
}
package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testStripLeadingHyphens_noHyphens() {
        String input = "example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_singleHyphen() {
        String input = "-example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_doubleHyphen() {
        String input = "--example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_multipleHyphens() {
        String input = "---example";
        String expected = "-example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_emptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_onlyHyphens() {
        String input = "--";
        String expected = "";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_noQuotes() {
        String input = "example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_leadingQuote() {
        String input = "\"example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_trailingQuote() {
        String input = "example\"";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_bothQuotes() {
        String input = "\"example\"";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_multipleQuotes() {
        String input = "\"\"example\"\"";
        String expected = "\"example\"";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_emptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_onlyQuotes() {
        String input = "\"\"";
        String expected = "";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }
}
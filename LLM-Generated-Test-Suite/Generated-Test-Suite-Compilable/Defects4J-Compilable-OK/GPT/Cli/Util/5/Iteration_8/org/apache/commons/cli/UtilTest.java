package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testStripLeadingHyphens_NoHyphens() {
        String input = "example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_SingleHyphen() {
        String input = "-example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_DoubleHyphen() {
        String input = "--example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_TripleHyphen() {
        String input = "---example";
        String expected = "-example";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_EmptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingHyphens_OnlyHyphens() {
        String input = "--";
        String expected = "";
        assertEquals(expected, Util.stripLeadingHyphens(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_NoQuotes() {
        String input = "example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_LeadingQuote() {
        String input = "\"example";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_TrailingQuote() {
        String input = "example\"";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_BothQuotes() {
        String input = "\"example\"";
        String expected = "example";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_EmptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_OnlyQuotes() {
        String input = "\"\"";
        String expected = "";
        assertEquals(expected, Util.stripLeadingAndTrailingQuotes(input));
    }
}
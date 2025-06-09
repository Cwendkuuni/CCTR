package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testStripLeadingHyphens_nullInput() {
        assertNull(Util.stripLeadingHyphens(null));
    }

    @Test
    public void testStripLeadingHyphens_noHyphens() {
        assertEquals("example", Util.stripLeadingHyphens("example"));
    }

    @Test
    public void testStripLeadingHyphens_singleHyphen() {
        assertEquals("example", Util.stripLeadingHyphens("-example"));
    }

    @Test
    public void testStripLeadingHyphens_doubleHyphen() {
        assertEquals("example", Util.stripLeadingHyphens("--example"));
    }

    @Test
    public void testStripLeadingHyphens_multipleHyphens() {
        assertEquals("-example", Util.stripLeadingHyphens("---example"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_noQuotes() {
        assertEquals("example", Util.stripLeadingAndTrailingQuotes("example"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_leadingQuote() {
        assertEquals("example", Util.stripLeadingAndTrailingQuotes("\"example"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_trailingQuote() {
        assertEquals("example", Util.stripLeadingAndTrailingQuotes("example\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_bothQuotes() {
        assertEquals("example", Util.stripLeadingAndTrailingQuotes("\"example\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_onlyLeadingQuote() {
        assertEquals("example\"", Util.stripLeadingAndTrailingQuotes("\"example\"\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_onlyTrailingQuote() {
        assertEquals("\"example", Util.stripLeadingAndTrailingQuotes("\"\"example\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_emptyString() {
        assertEquals("", Util.stripLeadingAndTrailingQuotes(""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_onlyQuotes() {
        assertEquals("", Util.stripLeadingAndTrailingQuotes("\"\""));
    }
}
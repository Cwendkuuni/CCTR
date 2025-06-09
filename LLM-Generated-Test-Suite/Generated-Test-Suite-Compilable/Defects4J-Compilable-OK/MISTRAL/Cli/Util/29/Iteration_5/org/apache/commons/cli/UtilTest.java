package org.apache.commons.cli;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilTest {

    @Test
    public void testStripLeadingHyphens_NullInput() {
        assertNull(Util.stripLeadingHyphens(null));
    }

    @Test
    public void testStripLeadingHyphens_NoHyphens() {
        assertEquals("test", Util.stripLeadingHyphens("test"));
    }

    @Test
    public void testStripLeadingHyphens_SingleHyphen() {
        assertEquals("test", Util.stripLeadingHyphens("-test"));
    }

    @Test
    public void testStripLeadingHyphens_DoubleHyphen() {
        assertEquals("test", Util.stripLeadingHyphens("--test"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_NoQuotes() {
        assertEquals("test", Util.stripLeadingAndTrailingQuotes("test"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_LeadingQuote() {
        assertEquals("test", Util.stripLeadingAndTrailingQuotes("\"test"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_TrailingQuote() {
        assertEquals("test", Util.stripLeadingAndTrailingQuotes("test\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_BothQuotes() {
        assertEquals("test", Util.stripLeadingAndTrailingQuotes("\"test\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_MultipleQuotes() {
        assertEquals("\"test\"", Util.stripLeadingAndTrailingQuotes("\"\"test\"\""));
    }
}
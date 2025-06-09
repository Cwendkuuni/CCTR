package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testStripLeadingHyphens() {
        assertEquals("option", Util.stripLeadingHyphens("--option"));
        assertEquals("option", Util.stripLeadingHyphens("-option"));
        assertEquals("option", Util.stripLeadingHyphens("option"));
        assertEquals("", Util.stripLeadingHyphens("--"));
        assertEquals("", Util.stripLeadingHyphens("-"));
        assertEquals("", Util.stripLeadingHyphens(""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes() {
        assertEquals("one two", Util.stripLeadingAndTrailingQuotes("\"one two\""));
        assertEquals("one two", Util.stripLeadingAndTrailingQuotes("one two"));
        assertEquals("one two\"", Util.stripLeadingAndTrailingQuotes("\"one two\""));
        assertEquals("\"one two", Util.stripLeadingAndTrailingQuotes("\"one two"));
        assertEquals("one two", Util.stripLeadingAndTrailingQuotes("one two"));
        assertEquals("", Util.stripLeadingAndTrailingQuotes("\"\""));
        assertEquals("", Util.stripLeadingAndTrailingQuotes(""));
    }
}
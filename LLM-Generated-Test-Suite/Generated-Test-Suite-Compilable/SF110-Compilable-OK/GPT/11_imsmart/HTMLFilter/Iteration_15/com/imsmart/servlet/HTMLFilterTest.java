package com.imsmart.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HTMLFilterTest {

    @Test
    public void testFilterWithNullInput() {
        // Test with null input
        assertNull("Filtering null should return null", HTMLFilter.filter(null));
    }

    @Test
    public void testFilterWithEmptyString() {
        // Test with empty string
        assertEquals("Filtering empty string should return empty string", "", HTMLFilter.filter(""));
    }

    @Test
    public void testFilterWithNoSpecialCharacters() {
        // Test with string that has no special characters
        String input = "Hello World";
        assertEquals("String with no special characters should remain unchanged", input, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithLessThanCharacter() {
        // Test with string containing '<'
        String input = "a < b";
        String expected = "a &lt; b";
        assertEquals("String with '<' should be converted to '&lt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithGreaterThanCharacter() {
        // Test with string containing '>'
        String input = "a > b";
        String expected = "a &gt; b";
        assertEquals("String with '>' should be converted to '&gt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAmpersandCharacter() {
        // Test with string containing '&'
        String input = "a & b";
        String expected = "a &amp; b";
        assertEquals("String with '&' should be converted to '&amp;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithDoubleQuoteCharacter() {
        // Test with string containing '\"'
        String input = "a \"quote\" b";
        String expected = "a &quot;quote&quot; b";
        assertEquals("String with '\"' should be converted to '&quot;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAllSpecialCharacters() {
        // Test with string containing all special characters
        String input = "<html> & \"quotes\"";
        String expected = "&lt;html&gt; &amp; &quot;quotes&quot;";
        assertEquals("String with all special characters should be converted correctly", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithMixedContent() {
        // Test with string containing mixed content
        String input = "Hello <world> & \"everyone\"";
        String expected = "Hello &lt;world&gt; &amp; &quot;everyone&quot;";
        assertEquals("String with mixed content should be converted correctly", expected, HTMLFilter.filter(input));
    }
}
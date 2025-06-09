package com.imsmart.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HTMLFilterTest {

    @Test
    public void testFilterWithNull() {
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
        assertEquals("Filtering string with no special characters should return the same string", input, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithLessThan() {
        // Test with string containing '<'
        String input = "a < b";
        String expected = "a &lt; b";
        assertEquals("Filtering string with '<' should replace it with '&lt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithGreaterThan() {
        // Test with string containing '>'
        String input = "a > b";
        String expected = "a &gt; b";
        assertEquals("Filtering string with '>' should replace it with '&gt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAmpersand() {
        // Test with string containing '&'
        String input = "a & b";
        String expected = "a &amp; b";
        assertEquals("Filtering string with '&' should replace it with '&amp;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithDoubleQuote() {
        // Test with string containing '\"'
        String input = "a \"b\" c";
        String expected = "a &quot;b&quot; c";
        assertEquals("Filtering string with '\"' should replace it with '&quot;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAllSpecialCharacters() {
        // Test with string containing all special characters
        String input = "<>&\"";
        String expected = "&lt;&gt;&amp;&quot;";
        assertEquals("Filtering string with all special characters should replace them accordingly", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithMixedContent() {
        // Test with string containing mixed content
        String input = "Hello <world> & \"everyone\"";
        String expected = "Hello &lt;world&gt; &amp; &quot;everyone&quot;";
        assertEquals("Filtering string with mixed content should replace special characters accordingly", expected, HTMLFilter.filter(input));
    }
}
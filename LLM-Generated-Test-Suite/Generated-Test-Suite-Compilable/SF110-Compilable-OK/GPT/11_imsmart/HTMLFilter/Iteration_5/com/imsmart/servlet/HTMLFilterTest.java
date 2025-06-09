package com.imsmart.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HTMLFilterTest {

    @Test
    public void testFilterWithNullInput() {
        // Test case for null input
        String input = null;
        String expectedOutput = null;
        assertNull("Filtering null should return null", HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithEmptyString() {
        // Test case for empty string
        String input = "";
        String expectedOutput = "";
        assertEquals("Filtering empty string should return empty string", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithNoSpecialCharacters() {
        // Test case for string with no special characters
        String input = "Hello World";
        String expectedOutput = "Hello World";
        assertEquals("Filtering string with no special characters should return the same string", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithLessThanCharacter() {
        // Test case for string with '<' character
        String input = "a < b";
        String expectedOutput = "a &lt; b";
        assertEquals("Filtering string with '<' should replace it with '&lt;'", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithGreaterThanCharacter() {
        // Test case for string with '>' character
        String input = "a > b";
        String expectedOutput = "a &gt; b";
        assertEquals("Filtering string with '>' should replace it with '&gt;'", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAmpersandCharacter() {
        // Test case for string with '&' character
        String input = "a & b";
        String expectedOutput = "a &amp; b";
        assertEquals("Filtering string with '&' should replace it with '&amp;'", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithDoubleQuoteCharacter() {
        // Test case for string with '\"' character
        String input = "a \"quote\" b";
        String expectedOutput = "a &quot;quote&quot; b";
        assertEquals("Filtering string with '\"' should replace it with '&quot;'", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithAllSpecialCharacters() {
        // Test case for string with all special characters
        String input = "<html> & \"quotes\"";
        String expectedOutput = "&lt;html&gt; &amp; &quot;quotes&quot;";
        assertEquals("Filtering string with all special characters should replace them accordingly", expectedOutput, HTMLFilter.filter(input));
    }

    @Test
    public void testFilterWithMixedContent() {
        // Test case for string with mixed content
        String input = "Hello <world> & \"everyone\"!";
        String expectedOutput = "Hello &lt;world&gt; &amp; &quot;everyone&quot;!";
        assertEquals("Filtering string with mixed content should replace special characters accordingly", expectedOutput, HTMLFilter.filter(input));
    }
}
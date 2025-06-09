package com.imsmart.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HTMLFilterTest {

    @Test
    public void testFilter_NullInput() {
        // Test with null input
        assertNull("Filtering null should return null", HTMLFilter.filter(null));
    }

    @Test
    public void testFilter_EmptyString() {
        // Test with an empty string
        assertEquals("Filtering an empty string should return an empty string", "", HTMLFilter.filter(""));
    }

    @Test
    public void testFilter_NoSpecialCharacters() {
        // Test with a string that contains no special characters
        String input = "Hello World";
        assertEquals("Filtering a string with no special characters should return the same string", input, HTMLFilter.filter(input));
    }

    @Test
    public void testFilter_LessThanCharacter() {
        // Test with a string containing the '<' character
        String input = "a < b";
        String expected = "a &lt; b";
        assertEquals("Filtering a string with '<' should replace it with '&lt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilter_GreaterThanCharacter() {
        // Test with a string containing the '>' character
        String input = "a > b";
        String expected = "a &gt; b";
        assertEquals("Filtering a string with '>' should replace it with '&gt;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilter_AmpersandCharacter() {
        // Test with a string containing the '&' character
        String input = "a & b";
        String expected = "a &amp; b";
        assertEquals("Filtering a string with '&' should replace it with '&amp;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilter_QuoteCharacter() {
        // Test with a string containing the '\"' character
        String input = "a \"quote\" b";
        String expected = "a &quot;quote&quot; b";
        assertEquals("Filtering a string with '\"' should replace it with '&quot;'", expected, HTMLFilter.filter(input));
    }

    @Test
    public void testFilter_MixedSpecialCharacters() {
        // Test with a string containing multiple special characters
        String input = "<div> \"Hello & Welcome\" </div>";
        String expected = "&lt;div&gt; &quot;Hello &amp; Welcome&quot; &lt;/div&gt;";
        assertEquals("Filtering a string with mixed special characters should replace them correctly", expected, HTMLFilter.filter(input));
    }
}
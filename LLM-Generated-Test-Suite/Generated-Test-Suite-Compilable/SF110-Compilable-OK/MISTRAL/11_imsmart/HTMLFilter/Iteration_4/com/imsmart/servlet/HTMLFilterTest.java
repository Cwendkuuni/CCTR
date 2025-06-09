package com.imsmart.servlet;

import org.junit.Test;
import static org.junit.Assert.*;

public class HTMLFilterTest {

    @Test
    public void testFilterNullInput() {
        assertNull(HTMLFilter.filter(null));
    }

    @Test
    public void testFilterEmptyString() {
        assertEquals("", HTMLFilter.filter(""));
    }

    @Test
    public void testFilterNoSpecialCharacters() {
        assertEquals("Hello World", HTMLFilter.filter("Hello World"));
    }

    @Test
    public void testFilterLessThan() {
        assertEquals("a&lt;b", HTMLFilter.filter("a<b"));
    }

    @Test
    public void testFilterGreaterThan() {
        assertEquals("a&gt;b", HTMLFilter.filter("a>b"));
    }

    @Test
    public void testFilterAmpersand() {
        assertEquals("a&amp;b", HTMLFilter.filter("a&b"));
    }

    @Test
    public void testFilterDoubleQuote() {
        assertEquals("a&quot;b", HTMLFilter.filter("a\"b"));
    }

    @Test
    public void testFilterMixedSpecialCharacters() {
        assertEquals("a&lt;b&gt;c&amp;d&quot;e", HTMLFilter.filter("a<b>c&d\"e"));
    }

    @Test
    public void testFilterMultipleSpecialCharacters() {
        assertEquals("&lt;&lt;&gt;&gt;&amp;&amp;&quot;&quot;", HTMLFilter.filter("<<>>&&\"\""));
    }

    @Test
    public void testFilterWithNormalAndSpecialCharacters() {
        assertEquals("Hello &lt;World&gt; &amp; \"Quotes\"", HTMLFilter.filter("Hello <World> & \"Quotes\""));
    }

    @Test
    public void testFilterLongString() {
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longString.append("a<b>c&d\"e");
        }
        String expected = longString.toString().replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;").replace("\"", "&quot;");
        assertEquals(expected, HTMLFilter.filter(longString.toString()));
    }
}
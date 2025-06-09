package com.imsmart.servlet;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        assertEquals("a &lt; b", HTMLFilter.filter("a < b"));
    }

    @Test
    public void testFilterGreaterThan() {
        assertEquals("a &gt; b", HTMLFilter.filter("a > b"));
    }

    @Test
    public void testFilterAmpersand() {
        assertEquals("a &amp; b", HTMLFilter.filter("a & b"));
    }

    @Test
    public void testFilterQuote() {
        assertEquals("a &quot;b&quot;", HTMLFilter.filter("a \"b\""));
    }

    @Test
    public void testFilterMixedSpecialCharacters() {
        assertEquals("a &lt; b &gt; c &amp; d &quot;e&quot;", HTMLFilter.filter("a < b > c & d \"e\""));
    }

    @Test
    public void testFilterMultipleSpecialCharacters() {
        assertEquals("&lt;&lt;&lt;&gt;&gt;&gt;&amp;&amp;&amp;&quot;&quot;&quot;", HTMLFilter.filter("<<<>>>&&&\"\"\""));
    }

    @Test
    public void testFilterSpecialCharactersWithNormalText() {
        assertEquals("Hello &lt;World&gt; &amp; \"Quote\"", HTMLFilter.filter("Hello <World> & \"Quote\""));
    }
}
package org.jsoup.helper;

import org.jsoup.helper.StringUtil;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testJoinCollection() {
        Collection<String> strings = Arrays.asList("one", "two", "three");
        String result = StringUtil.join(strings, ", ");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinIterator() {
        Collection<String> strings = Arrays.asList("one", "two", "three");
        String result = StringUtil.join(strings.iterator(), ", ");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinArray() {
        String[] strings = {"one", "two", "three"};
        String result = StringUtil.join(strings, ", ");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testPadding() {
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("     ", StringUtil.padding(5));
        assertEquals("                    ", StringUtil.padding(20));
        assertEquals("                    ", StringUtil.padding(21));
        assertEquals("                         ", StringUtil.padding(25));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPaddingNegativeWidth() {
        StringUtil.padding(-1);
    }

    @Test
    public void testIsBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank(" "));
        assertTrue(StringUtil.isBlank("\n"));
        assertFalse(StringUtil.isBlank("a"));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(""));
        assertTrue(StringUtil.isNumeric("123"));
        assertFalse(StringUtil.isNumeric("123a"));
    }

    @Test
    public void testIsWhitespace() {
        assertTrue(StringUtil.isWhitespace(' '));
        assertTrue(StringUtil.isWhitespace('\t'));
        assertTrue(StringUtil.isWhitespace('\n'));
        assertFalse(StringUtil.isWhitespace('a'));
    }

    @Test
    public void testIsActuallyWhitespace() {
        assertTrue(StringUtil.isActuallyWhitespace(' '));
        assertTrue(StringUtil.isActuallyWhitespace('\t'));
        assertTrue(StringUtil.isActuallyWhitespace('\n'));
        assertTrue(StringUtil.isActuallyWhitespace(160)); // non-breaking space
        assertFalse(StringUtil.isActuallyWhitespace('a'));
    }

    @Test
    public void testNormaliseWhitespace() {
        assertEquals("a b c", StringUtil.normaliseWhitespace("a  b \n c"));
        assertEquals("a b c", StringUtil.normaliseWhitespace("a\tb\nc"));
    }

    @Test
    public void testAppendNormalisedWhitespace() {
        StringBuilder sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "a  b \n c", false);
        assertEquals("a b c", sb.toString());
    }

    @Test
    public void testIn() {
        assertTrue(StringUtil.in("a", "a", "b", "c"));
        assertFalse(StringUtil.in("d", "a", "b", "c"));
    }

    @Test
    public void testInSorted() {
        String[] haystack = {"a", "b", "c"};
        assertTrue(StringUtil.inSorted("a", haystack));
        assertFalse(StringUtil.inSorted("d", haystack));
    }

    @Test
    public void testResolveURL() throws MalformedURLException {
        URL base = new URL("http://example.com");
        URL resolved = StringUtil.resolve(base, "/path");
        assertEquals("http://example.com/path", resolved.toString());
    }

    @Test
    public void testResolveString() {
        String baseUrl = "http://example.com";
        String resolved = StringUtil.resolve(baseUrl, "/path");
        assertEquals("http://example.com/path", resolved);
    }

    @Test
    public void testResolveStringMalformedBase() {
        String baseUrl = "htp://example.com"; // malformed protocol
        String resolved = StringUtil.resolve(baseUrl, "http://example.com/path");
        assertEquals("http://example.com/path", resolved);
    }

    @Test
    public void testResolveStringMalformedRel() {
        String baseUrl = "http://example.com";
        String resolved = StringUtil.resolve(baseUrl, "htp://example.com/path"); // malformed protocol
        assertEquals("", resolved);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = StringUtil.stringBuilder();
        assertNotNull(sb);
        assertEquals(0, sb.length());
    }
}
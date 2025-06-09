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
    public void testJoinCollectionEmpty() {
        Collection<String> strings = Collections.emptyList();
        String result = StringUtil.join(strings, ", ");
        assertEquals("", result);
    }

    @Test
    public void testJoinIterator() {
        Collection<String> strings = Arrays.asList("one", "two", "three");
        String result = StringUtil.join(strings.iterator(), ", ");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinIteratorEmpty() {
        Collection<String> strings = Collections.emptyList();
        String result = StringUtil.join(strings.iterator(), ", ");
        assertEquals("", result);
    }

    @Test
    public void testJoinArray() {
        String[] strings = {"one", "two", "three"};
        String result = StringUtil.join(strings, ", ");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinArrayEmpty() {
        String[] strings = {};
        String result = StringUtil.join(strings, ", ");
        assertEquals("", result);
    }

    @Test
    public void testPadding() {
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("     ", StringUtil.padding(5));
        assertEquals("                    ", StringUtil.padding(20));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPaddingNegative() {
        StringUtil.padding(-1);
    }

    @Test
    public void testIsBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank("   "));
        assertFalse(StringUtil.isBlank("not blank"));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(""));
        assertTrue(StringUtil.isNumeric("12345"));
        assertFalse(StringUtil.isNumeric("123a45"));
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
        assertEquals("one two three", StringUtil.normaliseWhitespace("one  two   three"));
        assertEquals("one two three", StringUtil.normaliseWhitespace("one\ttwo\nthree"));
    }

    @Test
    public void testAppendNormalisedWhitespace() {
        StringBuilder sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "one  two   three", false);
        assertEquals("one two three", sb.toString());
    }

    @Test
    public void testIn() {
        assertTrue(StringUtil.in("needle", "haystack", "needle", "stack"));
        assertFalse(StringUtil.in("needle", "haystack", "stack"));
    }

    @Test
    public void testInSorted() {
        String[] haystack = {"apple", "banana", "needle", "orange"};
        Arrays.sort(haystack);
        assertTrue(StringUtil.inSorted("needle", haystack));
        assertFalse(StringUtil.inSorted("pear", haystack));
    }

    @Test
    public void testResolveURL() throws MalformedURLException {
        URL base = new URL("http://example.com/path/");
        URL resolved = StringUtil.resolve(base, "file.html");
        assertEquals("http://example.com/path/file.html", resolved.toString());
    }

    @Test
    public void testResolveString() {
        String baseUrl = "http://example.com/path/";
        String resolved = StringUtil.resolve(baseUrl, "file.html");
        assertEquals("http://example.com/path/file.html", resolved);
    }

    @Test
    public void testResolveStringMalformedBase() {
        String baseUrl = "malformed://example.com";
        String resolved = StringUtil.resolve(baseUrl, "http://example.com/file.html");
        assertEquals("http://example.com/file.html", resolved);
    }

    @Test
    public void testResolveStringMalformedRel() {
        String baseUrl = "http://example.com/path/";
        String resolved = StringUtil.resolve(baseUrl, "malformed://example.com");
        assertEquals("", resolved);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = StringUtil.stringBuilder();
        assertNotNull(sb);
        assertEquals(0, sb.length());
    }
}
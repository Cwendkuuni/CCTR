package org.jsoup.helper;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StringUtilTest {

    private Collection<String> collection;
    private Iterator<String> iterator;
    private String[] array;

    @Before
    public void setUp() {
        collection = Arrays.asList("one", "two", "three");
        iterator = collection.iterator();
        array = new String[]{"one", "two", "three"};
    }

    @After
    public void tearDown() {
        collection = null;
        iterator = null;
        array = null;
    }

    @Test
    public void testJoinCollection() {
        String result = StringUtil.join(collection, ",");
        assertEquals("one,two,three", result);
    }

    @Test
    public void testJoinIterator() {
        String result = StringUtil.join(iterator, ",");
        assertEquals("one,two,three", result);
    }

    @Test
    public void testJoinArray() {
        String result = StringUtil.join(array, ",");
        assertEquals("one,two,three", result);
    }

    @Test
    public void testPadding() {
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("  ", StringUtil.padding(2));
        assertEquals("                    ", StringUtil.padding(20));
        assertEquals("                     ", StringUtil.padding(21));
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
        assertTrue(StringUtil.isBlank("\t\n\r\f"));
        assertFalse(StringUtil.isBlank("a"));
        assertFalse(StringUtil.isBlank(" a "));
    }

    @Test
    public void testIsNumeric() {
        assertTrue(StringUtil.isNumeric("12345"));
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(""));
        assertFalse(StringUtil.isNumeric("123a"));
    }

    @Test
    public void testIsWhitespace() {
        assertTrue(StringUtil.isWhitespace(' '));
        assertTrue(StringUtil.isWhitespace('\t'));
        assertTrue(StringUtil.isWhitespace('\n'));
        assertTrue(StringUtil.isWhitespace('\f'));
        assertTrue(StringUtil.isWhitespace('\r'));
        assertFalse(StringUtil.isWhitespace('a'));
    }

    @Test
    public void testIsActuallyWhitespace() {
        assertTrue(StringUtil.isActuallyWhitespace(' '));
        assertTrue(StringUtil.isActuallyWhitespace('\t'));
        assertTrue(StringUtil.isActuallyWhitespace('\n'));
        assertTrue(StringUtil.isActuallyWhitespace('\f'));
        assertTrue(StringUtil.isActuallyWhitespace('\r'));
        assertTrue(StringUtil.isActuallyWhitespace(160)); // non-breaking space
        assertFalse(StringUtil.isActuallyWhitespace('a'));
    }

    @Test
    public void testNormaliseWhitespace() {
        assertEquals("a b c", StringUtil.normaliseWhitespace("a   b\tc\n"));
        assertEquals("a b c", StringUtil.normaliseWhitespace("a\tb\nc"));
        assertEquals("a b c", StringUtil.normaliseWhitespace("a\nb\tc"));
    }

    @Test
    public void testAppendNormalisedWhitespace() {
        StringBuilder sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "a   b\tc\n", false);
        assertEquals("a b c", sb.toString());

        sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "a\tb\nc", true);
        assertEquals("a b c", sb.toString());
    }

    @Test
    public void testIn() {
        assertTrue(StringUtil.in("two", "one", "two", "three"));
        assertFalse(StringUtil.in("four", "one", "two", "three"));
    }

    @Test
    public void testInSorted() {
        String[] sortedArray = {"one", "two", "three"};
        assertTrue(StringUtil.inSorted("two", sortedArray));
        assertFalse(StringUtil.inSorted("four", sortedArray));
    }

    @Test
    public void testResolveURL() throws MalformedURLException {
        URL base = new URL("http://example.com/path/file");
        URL result = StringUtil.resolve(base, "?foo");
        assertEquals("http://example.com/path/file?foo", result.toString());

        result = StringUtil.resolve(base, "./foo");
        assertEquals("http://example.com/path/foo", result.toString());
    }

    @Test
    public void testResolveString() {
        String baseUrl = "http://example.com/path/file";
        String result = StringUtil.resolve(baseUrl, "?foo");
        assertEquals("http://example.com/path/file?foo", result);

        result = StringUtil.resolve(baseUrl, "./foo");
        assertEquals("http://example.com/path/foo", result);

        result = StringUtil.resolve(baseUrl, "http://example.com/other");
        assertEquals("http://example.com/other", result);

        result = StringUtil.resolve("invalid", "http://example.com/other");
        assertEquals("http://example.com/other", result);

        result = StringUtil.resolve("invalid", "invalid");
        assertEquals("", result);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = StringUtil.stringBuilder();
        assertNotNull(sb);
        assertEquals(0, sb.length());
    }
}
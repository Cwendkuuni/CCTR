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

    @Before
    public void setUp() {
        // Set up any necessary preconditions for the tests
    }

    @After
    public void tearDown() {
        // Clean up any resources used in the tests
    }

    @Test
    public void testJoinCollection() {
        Collection<String> strings = Arrays.asList("a", "b", "c");
        String result = StringUtil.join(strings, ",");
        assertEquals("a,b,c", result);
    }

    @Test
    public void testJoinIterator() {
        Collection<String> strings = Arrays.asList("a", "b", "c");
        Iterator<String> iterator = strings.iterator();
        String result = StringUtil.join(iterator, ",");
        assertEquals("a,b,c", result);
    }

    @Test
    public void testJoinArray() {
        String[] strings = {"a", "b", "c"};
        String result = StringUtil.join(strings, ",");
        assertEquals("a,b,c", result);
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
        assertTrue(StringUtil.isBlank(" "));
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
        assertTrue(StringUtil.isActuallyWhitespace(160));
        assertFalse(StringUtil.isActuallyWhitespace('a'));
    }

    @Test
    public void testNormaliseWhitespace() {
        assertEquals("a b c", StringUtil.normaliseWhitespace("a  \t\nb\nc "));
        assertEquals("", StringUtil.normaliseWhitespace(" \t\n"));
        assertEquals("abc", StringUtil.normaliseWhitespace("abc"));
    }

    @Test
    public void testAppendNormalisedWhitespace() {
        StringBuilder sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "a  \t\nb\nc ", false);
        assertEquals("a b c ", sb.toString());

        sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, "a  \t\nb\nc ", true);
        assertEquals("a b c", sb.toString());
    }

    @Test
    public void testIn() {
        assertTrue(StringUtil.in("a", "a", "b", "c"));
        assertFalse(StringUtil.in("d", "a", "b", "c"));
    }

    @Test
    public void testInSorted() {
        String[] sortedArray = {"a", "b", "c"};
        assertTrue(StringUtil.inSorted("b", sortedArray));
        assertFalse(StringUtil.inSorted("d", sortedArray));
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
        assertEquals("http://example.com/path/file?foo", StringUtil.resolve("http://example.com/path/file", "?foo"));
        assertEquals("http://example.com/path/foo", StringUtil.resolve("http://example.com/path/file", "./foo"));
        assertEquals("http://example.com/", StringUtil.resolve("http://example.com/path/file", "http://example.com/"));
        assertEquals("", StringUtil.resolve("invalid", "invalid"));
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = StringUtil.stringBuilder();
        assertNotNull(sb);
        assertEquals(0, sb.length());
    }
}
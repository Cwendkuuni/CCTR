package org.jsoup.helper;

import org.junit.Test;
import org.jsoup.helper.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testJoinCollection() {
        assertEquals("a,b,c", StringUtil.join(Arrays.asList("a", "b", "c"), ","));
        assertEquals("a", StringUtil.join(Collections.singletonList("a"), ","));
        assertEquals("", StringUtil.join(Collections.emptyList(), ","));
    }

    @Test
    public void testJoinIterator() {
        assertEquals("a,b,c", StringUtil.join(Arrays.asList("a", "b", "c").iterator(), ","));
        assertEquals("a", StringUtil.join(Collections.singletonList("a").iterator(), ","));
        assertEquals("", StringUtil.join(Collections.emptyList().iterator(), ","));
    }

    @Test
    public void testJoinArray() {
        assertEquals("a,b,c", StringUtil.join(new String[]{"a", "b", "c"}, ","));
        assertEquals("a", StringUtil.join(new String[]{"a"}, ","));
        assertEquals("", StringUtil.join(new String[]{}, ","));
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
        assertTrue(StringUtil.isBlank("\n\t\r"));
        assertFalse(StringUtil.isBlank("a"));
        assertFalse(StringUtil.isBlank(" a "));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric(""));
        assertTrue(StringUtil.isNumeric("123"));
        assertFalse(StringUtil.isNumeric("123a"));
        assertFalse(StringUtil.isNumeric("a123"));
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
        assertTrue(StringUtil.isActuallyWhitespace(160)); // &nbsp;
        assertFalse(StringUtil.isActuallyWhitespace('a'));
    }

    @Test
    public void testNormaliseWhitespace() {
        assertEquals("a b c", StringUtil.normaliseWhitespace("a  b \n c"));
        assertEquals("a b c", StringUtil.normaliseWhitespace(" a\tb\nc "));
        assertEquals("", StringUtil.normaliseWhitespace(""));
        assertEquals("", StringUtil.normaliseWhitespace(" \t\n\r"));
    }

    @Test
    public void testAppendNormalisedWhitespace() {
        StringBuilder sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, " a\tb\nc ", true);
        assertEquals("a b c", sb.toString());

        sb = new StringBuilder();
        StringUtil.appendNormalisedWhitespace(sb, " a\tb\nc ", false);
        assertEquals(" a b c", sb.toString());
    }

    @Test
    public void testIn() {
        assertTrue(StringUtil.in("a", "a", "b", "c"));
        assertFalse(StringUtil.in("d", "a", "b", "c"));
        assertFalse(StringUtil.in("a"));
    }

    @Test
    public void testInSorted() {
        assertTrue(StringUtil.inSorted("b", new String[]{"a", "b", "c"}));
        assertFalse(StringUtil.inSorted("d", new String[]{"a", "b", "c"}));
    }

    @Test
    public void testResolveURL() throws MalformedURLException {
        URL base = new URL("http://example.com/path/");
        URL resolved = StringUtil.resolve(base, "file.html");
        assertEquals("http://example.com/path/file.html", resolved.toString());

        resolved = StringUtil.resolve(base, "?query=1");
        assertEquals("http://example.com/path/?query=1", resolved.toString());

        resolved = StringUtil.resolve(base, "./file.html");
        assertEquals("http://example.com/path/file.html", resolved.toString());
    }

    @Test
    public void testResolveString() {
        assertEquals("http://example.com/path/file.html", StringUtil.resolve("http://example.com/path/", "file.html"));
        assertEquals("", StringUtil.resolve("http://example.com/path/", "http://"));
        assertEquals("http://example.com/path/?query=1", StringUtil.resolve("http://example.com/path/", "?query=1"));
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = StringUtil.stringBuilder();
        assertNotNull(sb);
        assertEquals(0, sb.length());

        sb.append("test");
        assertEquals(4, sb.length());

        StringBuilder sb2 = StringUtil.stringBuilder();
        assertEquals(0, sb2.length());
    }
}
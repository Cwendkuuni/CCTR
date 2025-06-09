package com.google.common.base;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class SplitterTest {

    @Test
    public void testOnChar() {
        Splitter splitter = Splitter.on(',');
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void testOnCharMatcher() {
        Splitter splitter = Splitter.on(CharMatcher.is(';'));
        List<String> result = splitter.splitToList("x;y;z");
        assertEquals(3, result.size());
        assertEquals("x", result.get(0));
        assertEquals("y", result.get(1));
        assertEquals("z", result.get(2));
    }

    @Test
    public void testOnString() {
        Splitter splitter = Splitter.on("::");
        List<String> result = splitter.splitToList("key::value::pair");
        assertEquals(3, result.size());
        assertEquals("key", result.get(0));
        assertEquals("value", result.get(1));
        assertEquals("pair", result.get(2));
    }

    @Test
    public void testOnPattern() {
        Splitter splitter = Splitter.on(Pattern.compile("\\s+"));
        List<String> result = splitter.splitToList("split by spaces");
        assertEquals(3, result.size());
        assertEquals("split", result.get(0));
        assertEquals("by", result.get(1));
        assertEquals("spaces", result.get(2));
    }

    @Test
    public void testOnPatternString() {
        Splitter splitter = Splitter.onPattern("\\d+");
        List<String> result = splitter.splitToList("a1b2c3");
        assertEquals(4, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
        assertEquals("", result.get(3));
    }

    @Test
    public void testFixedLength() {
        Splitter splitter = Splitter.fixedLength(2);
        List<String> result = splitter.splitToList("abcdef");
        assertEquals(3, result.size());
        assertEquals("ab", result.get(0));
        assertEquals("cd", result.get(1));
        assertEquals("ef", result.get(2));
    }

    @Test
    public void testOmitEmptyStrings() {
        Splitter splitter = Splitter.on(',').omitEmptyStrings();
        List<String> result = splitter.splitToList("a,,b,");
        assertEquals(2, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
    }

    @Test
    public void testLimit() {
        Splitter splitter = Splitter.on(',').limit(2);
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(2, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b,c", result.get(1));
    }

    @Test
    public void testTrimResults() {
        Splitter splitter = Splitter.on(',').trimResults();
        List<String> result = splitter.splitToList(" a , b , c ");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void testTrimResultsWithCharMatcher() {
        Splitter splitter = Splitter.on(',').trimResults(CharMatcher.is(' '));
        List<String> result = splitter.splitToList(" a , b , c ");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void testSplitToList() {
        Splitter splitter = Splitter.on(',');
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void testWithKeyValueSeparator() {
        Splitter.MapSplitter mapSplitter = Splitter.on(';').withKeyValueSeparator('=');
        Map<String, String> result = mapSplitter.split("key1=value1;key2=value2");
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnEmptyStringSeparator() {
        Splitter.on("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnPatternMatchingEmptyString() {
        Splitter.on(Pattern.compile(".*"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFixedLengthZero() {
        Splitter.fixedLength(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLimitZero() {
        Splitter.on(',').limit(0);
    }
}
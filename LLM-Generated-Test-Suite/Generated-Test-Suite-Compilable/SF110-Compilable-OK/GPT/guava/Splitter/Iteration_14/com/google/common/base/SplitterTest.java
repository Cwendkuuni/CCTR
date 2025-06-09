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
        Splitter splitter = Splitter.on(CharMatcher.is(','));
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void testOnString() {
        Splitter splitter = Splitter.on(",");
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnEmptyString() {
        Splitter.on("");
    }

    @Test
    public void testOnPattern() {
        Splitter splitter = Splitter.on(Pattern.compile(","));
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnPatternEmptyString() {
        Splitter.on(Pattern.compile("")).splitToList("a,b,c");
    }

    @Test
    public void testOnPatternString() {
        Splitter splitter = Splitter.onPattern(",");
        List<String> result = splitter.splitToList("a,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
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

    @Test(expected = IllegalArgumentException.class)
    public void testFixedLengthInvalid() {
        Splitter.fixedLength(0);
    }

    @Test
    public void testOmitEmptyStrings() {
        Splitter splitter = Splitter.on(',').omitEmptyStrings();
        List<String> result = splitter.splitToList("a,,b,c");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
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
        Map<String, String> result = mapSplitter.split("a=1;b=2;c=3");
        assertEquals(3, result.size());
        assertEquals("1", result.get("a"));
        assertEquals("2", result.get("b"));
        assertEquals("3", result.get("c"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithKeyValueSeparatorInvalidEntry() {
        Splitter.MapSplitter mapSplitter = Splitter.on(';').withKeyValueSeparator('=');
        mapSplitter.split("a=1;b=2;c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithKeyValueSeparatorDuplicateKey() {
        Splitter.MapSplitter mapSplitter = Splitter.on(';').withKeyValueSeparator('=');
        mapSplitter.split("a=1;a=2");
    }
}
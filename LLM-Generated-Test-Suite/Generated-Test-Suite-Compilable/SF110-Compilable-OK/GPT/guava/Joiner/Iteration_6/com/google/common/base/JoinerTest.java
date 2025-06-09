package com.google.common.base;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JoinerTest {

    private Joiner joiner;
    private List<String> parts;
    private Map<String, String> map;

    @Before
    public void setUp() {
        joiner = Joiner.on(", ");
        parts = Arrays.asList("one", "two", "three");
        map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
    }

    @Test
    public void testJoinWithIterable() {
        String result = joiner.join(parts);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithIterator() {
        Iterator<String> iterator = parts.iterator();
        String result = joiner.join(iterator);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithArray() {
        String[] array = {"one", "two", "three"};
        String result = joiner.join(array);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithVarargs() {
        String result = joiner.join("one", "two", "three");
        assertEquals("one, two, three", result);
    }

    @Test
    public void testAppendToAppendable() throws IOException {
        StringWriter writer = new StringWriter();
        joiner.appendTo(writer, parts);
        assertEquals("one, two, three", writer.toString());
    }

    @Test
    public void testAppendToStringBuilder() {
        StringBuilder builder = new StringBuilder();
        joiner.appendTo(builder, parts);
        assertEquals("one, two, three", builder.toString());
    }

    @Test
    public void testUseForNull() {
        Joiner nullJoiner = joiner.useForNull("null");
        String result = nullJoiner.join("one", null, "three");
        assertEquals("one, null, three", result);
    }

    @Test(expected = NullPointerException.class)
    public void testJoinWithNullThrowsException() {
        joiner.join("one", null, "three");
    }

    @Test
    public void testSkipNulls() {
        Joiner skipNullsJoiner = joiner.skipNulls();
        String result = skipNullsJoiner.join("one", null, "three");
        assertEquals("one, three", result);
    }

    @Test
    public void testMapJoiner() {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        String result = mapJoiner.join(map);
        assertTrue(result.contains("key1=value1"));
        assertTrue(result.contains("key2=value2"));
    }

    @Test
    public void testMapJoinerAppendTo() throws IOException {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        StringWriter writer = new StringWriter();
        mapJoiner.appendTo(writer, map);
        String result = writer.toString();
        assertTrue(result.contains("key1=value1"));
        assertTrue(result.contains("key2=value2"));
    }

    @Test
    public void testMapJoinerUseForNull() {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=").useForNull("null");
        map.put("key3", null);
        String result = mapJoiner.join(map);
        assertTrue(result.contains("key3=null"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSkipNullsWithMapJoinerThrowsException() {
        MapJoiner mapJoiner = joiner.skipNulls().withKeyValueSeparator("=");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUseForNullWithMapJoinerThrowsException() {
        MapJoiner mapJoiner = joiner.useForNull("null").withKeyValueSeparator("=");
    }
}
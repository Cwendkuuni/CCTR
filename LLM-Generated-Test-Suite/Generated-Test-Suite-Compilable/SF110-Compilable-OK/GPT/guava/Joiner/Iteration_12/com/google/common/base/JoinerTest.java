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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    public void testJoinWithStringSeparator() {
        String result = joiner.join(parts);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithCharSeparator() {
        Joiner charJoiner = Joiner.on(';');
        String result = charJoiner.join(parts);
        assertEquals("one;two;three", result);
    }

    @Test
    public void testAppendToStringBuilder() {
        StringBuilder builder = new StringBuilder();
        joiner.appendTo(builder, parts);
        assertEquals("one, two, three", builder.toString());
    }

    @Test
    public void testAppendToAppendable() throws IOException {
        StringWriter writer = new StringWriter();
        joiner.appendTo(writer, parts);
        assertEquals("one, two, three", writer.toString());
    }

    @Test
    public void testJoinWithNulls() {
        List<String> partsWithNulls = Arrays.asList("one", null, "three");
        try {
            joiner.join(partsWithNulls);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void testUseForNull() {
        Joiner nullJoiner = joiner.useForNull("null");
        List<String> partsWithNulls = Arrays.asList("one", null, "three");
        String result = nullJoiner.join(partsWithNulls);
        assertEquals("one, null, three", result);
    }

    @Test
    public void testSkipNulls() {
        Joiner skipNullsJoiner = joiner.skipNulls();
        List<String> partsWithNulls = Arrays.asList("one", null, "three");
        String result = skipNullsJoiner.join(partsWithNulls);
        assertEquals("one, three", result);
    }

    @Test
    public void testMapJoiner() {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        String result = mapJoiner.join(map);
        assertEquals("key1=value1, key2=value2", result);
    }

    @Test
    public void testMapJoinerAppendTo() throws IOException {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        StringWriter writer = new StringWriter();
        mapJoiner.appendTo(writer, map);
        assertEquals("key1=value1, key2=value2", writer.toString());
    }

    @Test
    public void testMapJoinerUseForNull() {
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=").useForNull("null");
        Map<String, String> mapWithNulls = new HashMap<>();
        mapWithNulls.put("key1", null);
        mapWithNulls.put("key2", "value2");
        String result = mapJoiner.join(mapWithNulls);
        assertEquals("key1=null, key2=value2", result);
    }

    @Test
    public void testJoinWithArray() {
        String[] array = {"one", "two", "three"};
        String result = joiner.join(array);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithIterator() {
        Iterator<String> iterator = parts.iterator();
        String result = joiner.join(iterator);
        assertEquals("one, two, three", result);
    }

    @Test
    public void testJoinWithVarargs() {
        String result = joiner.join("one", "two", "three");
        assertEquals("one, two, three", result);
    }
}
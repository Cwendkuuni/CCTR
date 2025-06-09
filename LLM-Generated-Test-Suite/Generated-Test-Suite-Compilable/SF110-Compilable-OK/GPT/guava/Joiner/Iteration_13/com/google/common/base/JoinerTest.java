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
    private Joiner joinerWithNulls;
    private Joiner joinerSkippingNulls;
    private MapJoiner mapJoiner;

    @Before
    public void setUp() {
        joiner = Joiner.on(", ");
        joinerWithNulls = joiner.useForNull("null");
        joinerSkippingNulls = joiner.skipNulls();
        mapJoiner = joiner.withKeyValueSeparator("=");
    }

    @Test
    public void testOnString() {
        Joiner joiner = Joiner.on(", ");
        assertNotNull(joiner);
    }

    @Test
    public void testOnChar() {
        Joiner joiner = Joiner.on(',');
        assertNotNull(joiner);
    }

    @Test
    public void testAppendTo_Iterable() throws IOException {
        StringWriter writer = new StringWriter();
        List<String> parts = Arrays.asList("a", "b", "c");
        joiner.appendTo(writer, parts);
        assertEquals("a, b, c", writer.toString());
    }

    @Test
    public void testAppendTo_Iterator() throws IOException {
        StringWriter writer = new StringWriter();
        List<String> parts = Arrays.asList("a", "b", "c");
        joiner.appendTo(writer, parts.iterator());
        assertEquals("a, b, c", writer.toString());
    }

    @Test
    public void testAppendTo_Array() throws IOException {
        StringWriter writer = new StringWriter();
        String[] parts = {"a", "b", "c"};
        joiner.appendTo(writer, parts);
        assertEquals("a, b, c", writer.toString());
    }

    @Test
    public void testAppendTo_VarArgs() throws IOException {
        StringWriter writer = new StringWriter();
        joiner.appendTo(writer, "a", "b", "c");
        assertEquals("a, b, c", writer.toString());
    }

    @Test
    public void testJoin_Iterable() {
        List<String> parts = Arrays.asList("a", "b", "c");
        String result = joiner.join(parts);
        assertEquals("a, b, c", result);
    }

    @Test
    public void testJoin_Iterator() {
        List<String> parts = Arrays.asList("a", "b", "c");
        String result = joiner.join(parts.iterator());
        assertEquals("a, b, c", result);
    }

    @Test
    public void testJoin_Array() {
        String[] parts = {"a", "b", "c"};
        String result = joiner.join(parts);
        assertEquals("a, b, c", result);
    }

    @Test
    public void testJoin_VarArgs() {
        String result = joiner.join("a", "b", "c");
        assertEquals("a, b, c", result);
    }

    @Test
    public void testUseForNull() {
        List<String> parts = Arrays.asList("a", null, "c");
        String result = joinerWithNulls.join(parts);
        assertEquals("a, null, c", result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUseForNull_ThrowsException() {
        joinerWithNulls.useForNull("anotherNull");
    }

    @Test
    public void testSkipNulls() {
        List<String> parts = Arrays.asList("a", null, "c");
        String result = joinerSkippingNulls.join(parts);
        assertEquals("a, c", result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSkipNulls_ThrowsException() {
        joinerSkippingNulls.skipNulls();
    }

    @Test
    public void testMapJoiner() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        String result = mapJoiner.join(map);
        assertEquals("key1=value1, key2=value2", result);
    }

    @Test
    public void testMapJoiner_UseForNull() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", null);
        map.put("key2", "value2");
        MapJoiner mapJoinerWithNulls = mapJoiner.useForNull("null");
        String result = mapJoinerWithNulls.join(map);
        assertEquals("key1=null, key2=value2", result);
    }
}
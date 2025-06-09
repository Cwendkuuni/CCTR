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
    private Joiner joinerWithNulls;
    private Joiner joinerSkippingNulls;

    @Before
    public void setUp() {
        joiner = Joiner.on(", ");
        joinerWithNulls = joiner.useForNull("null");
        joinerSkippingNulls = joiner.skipNulls();
    }

    @Test
    public void testJoinIterable() {
        List<String> parts = Arrays.asList("one", "two", "three");
        assertEquals("one, two, three", joiner.join(parts));
    }

    @Test
    public void testJoinIterator() {
        Iterator<String> parts = Arrays.asList("one", "two", "three").iterator();
        assertEquals("one, two, three", joiner.join(parts));
    }

    @Test
    public void testJoinArray() {
        String[] parts = {"one", "two", "three"};
        assertEquals("one, two, three", joiner.join(parts));
    }

    @Test
    public void testJoinVarargs() {
        assertEquals("one, two, three", joiner.join("one", "two", "three"));
    }

    @Test
    public void testJoinWithNulls() {
        List<String> parts = Arrays.asList("one", null, "three");
        assertEquals("one, null, three", joinerWithNulls.join(parts));
    }

    @Test
    public void testJoinSkippingNulls() {
        List<String> parts = Arrays.asList("one", null, "three");
        assertEquals("one, three", joinerSkippingNulls.join(parts));
    }

    @Test
    public void testAppendToAppendable() throws IOException {
        StringWriter writer = new StringWriter();
        joiner.appendTo(writer, Arrays.asList("one", "two", "three"));
        assertEquals("one, two, three", writer.toString());
    }

    @Test
    public void testAppendToStringBuilder() {
        StringBuilder builder = new StringBuilder();
        joiner.appendTo(builder, Arrays.asList("one", "two", "three"));
        assertEquals("one, two, three", builder.toString());
    }

    @Test
    public void testUseForNull() {
        Joiner customJoiner = joiner.useForNull("missing");
        List<String> parts = Arrays.asList("one", null, "three");
        assertEquals("one, missing, three", customJoiner.join(parts));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUseForNullAfterSkipNulls() {
        joinerSkippingNulls.useForNull("missing");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSkipNullsAfterUseForNull() {
        joinerWithNulls.skipNulls();
    }

    @Test
    public void testMapJoiner() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        assertEquals("key1=value1, key2=value2", mapJoiner.join(map));
    }

    @Test
    public void testMapJoinerWithNulls() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", null);
        map.put("key2", "value2");

        MapJoiner mapJoiner = joinerWithNulls.withKeyValueSeparator("=");
        assertEquals("key1=null, key2=value2", mapJoiner.join(map));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMapJoinerSkipNulls() {
        MapJoiner mapJoiner = joinerSkippingNulls.withKeyValueSeparator("=");
    }

    @Test
    public void testMapJoinerAppendTo() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        StringWriter writer = new StringWriter();
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");
        mapJoiner.appendTo(writer, map);
        assertEquals("key1=value1, key2=value2", writer.toString());
    }
}
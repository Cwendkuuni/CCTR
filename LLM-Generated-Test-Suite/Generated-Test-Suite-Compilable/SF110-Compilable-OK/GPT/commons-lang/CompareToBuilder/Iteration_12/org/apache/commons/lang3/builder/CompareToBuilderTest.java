package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

public class CompareToBuilderTest {

    @Test
    public void testReflectionCompareEqualObjects() {
        DummyObject obj1 = new DummyObject(1, "test");
        DummyObject obj2 = new DummyObject(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2));
    }

    @Test
    public void testReflectionCompareDifferentObjects() {
        DummyObject obj1 = new DummyObject(1, "test");
        DummyObject obj2 = new DummyObject(2, "test");
        assertTrue(CompareToBuilder.reflectionCompare(obj1, obj2) < 0);
    }

    @Test(expected = NullPointerException.class)
    public void testReflectionCompareNullLhs() {
        DummyObject obj2 = new DummyObject(1, "test");
        CompareToBuilder.reflectionCompare(null, obj2);
    }

    @Test(expected = NullPointerException.class)
    public void testReflectionCompareNullRhs() {
        DummyObject obj1 = new DummyObject(1, "test");
        CompareToBuilder.reflectionCompare(obj1, null);
    }

    @Test(expected = ClassCastException.class)
    public void testReflectionCompareDifferentClasses() {
        DummyObject obj1 = new DummyObject(1, "test");
        String obj2 = "test";
        CompareToBuilder.reflectionCompare(obj1, obj2);
    }

    @Test
    public void testReflectionCompareWithExcludeFields() {
        DummyObject obj1 = new DummyObject(1, "test");
        DummyObject obj2 = new DummyObject(2, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, "id"));
    }

    @Test
    public void testAppendSuper() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.appendSuper(0);
        assertEquals(0, builder.toComparison());
        builder.appendSuper(-1);
        assertEquals(-1, builder.toComparison());
    }

    @Test
    public void testAppendObject() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append("a", "b");
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendLong() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(1L, 2L);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendInt() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(1, 2);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendShort() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append((short) 1, (short) 2);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendChar() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append('a', 'b');
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendByte() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append((byte) 1, (byte) 2);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendDouble() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(1.0, 2.0);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendFloat() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(1.0f, 2.0f);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendBoolean() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(false, true);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendObjectArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new String[]{"a"}, new String[]{"b"});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendLongArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new long[]{1}, new long[]{2});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendIntArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new int[]{1}, new int[]{2});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendShortArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new short[]{1}, new short[]{2});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendCharArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new char[]{'a'}, new char[]{'b'});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendByteArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new byte[]{1}, new byte[]{2});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendDoubleArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new double[]{1.0}, new double[]{2.0});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendFloatArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new float[]{1.0f}, new float[]{2.0f});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testAppendBooleanArray() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(new boolean[]{false}, new boolean[]{true});
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testBuild() {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(1, 2);
        assertEquals(-1, builder.build().intValue());
    }

    private static class DummyObject {
        private int id;
        private String name;

        public DummyObject(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
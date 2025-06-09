package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashCodeBuilderTest {

    @Test
    public void testDefaultConstructor() {
        HashCodeBuilder builder = new HashCodeBuilder();
        assertEquals(17, builder.toHashCode());
    }

    @Test
    public void testConstructorWithOddNumbers() {
        HashCodeBuilder builder = new HashCodeBuilder(19, 39);
        assertEquals(19, builder.toHashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEvenInitialNumber() {
        new HashCodeBuilder(18, 39);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEvenMultiplier() {
        new HashCodeBuilder(19, 38);
    }

    @Test
    public void testAppendBoolean() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(true);
        assertEquals(17 * 37, builder.toHashCode());
    }

    @Test
    public void testAppendBooleanArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new boolean[]{true, false});
        assertEquals(17 * 37 * 37 + 1, builder.toHashCode());
    }

    @Test
    public void testAppendByte() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append((byte) 1);
        assertEquals(17 * 37 + 1, builder.toHashCode());
    }

    @Test
    public void testAppendByteArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new byte[]{1, 2});
        assertEquals((17 * 37 + 1) * 37 + 2, builder.toHashCode());
    }

    @Test
    public void testAppendChar() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append('a');
        assertEquals(17 * 37 + 'a', builder.toHashCode());
    }

    @Test
    public void testAppendCharArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new char[]{'a', 'b'});
        assertEquals((17 * 37 + 'a') * 37 + 'b', builder.toHashCode());
    }

    @Test
    public void testAppendDouble() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(1.0);
        assertEquals(17 * 37 + Double.valueOf(1.0).hashCode(), builder.toHashCode());
    }

    @Test
    public void testAppendDoubleArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new double[]{1.0, 2.0});
        assertEquals((17 * 37 + Double.valueOf(1.0).hashCode()) * 37 + Double.valueOf(2.0).hashCode(), builder.toHashCode());
    }

    @Test
    public void testAppendFloat() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(1.0f);
        assertEquals(17 * 37 + Float.floatToIntBits(1.0f), builder.toHashCode());
    }

    @Test
    public void testAppendFloatArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new float[]{1.0f, 2.0f});
        assertEquals((17 * 37 + Float.floatToIntBits(1.0f)) * 37 + Float.floatToIntBits(2.0f), builder.toHashCode());
    }

    @Test
    public void testAppendInt() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(1);
        assertEquals(17 * 37 + 1, builder.toHashCode());
    }

    @Test
    public void testAppendIntArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new int[]{1, 2});
        assertEquals((17 * 37 + 1) * 37 + 2, builder.toHashCode());
    }

    @Test
    public void testAppendLong() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(1L);
        assertEquals(17 * 37 + (int) (1L ^ (1L >> 32)), builder.toHashCode());
    }

    @Test
    public void testAppendLongArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new long[]{1L, 2L});
        assertEquals((17 * 37 + (int) (1L ^ (1L >> 32))) * 37 + (int) (2L ^ (2L >> 32)), builder.toHashCode());
    }

    @Test
    public void testAppendObject() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        Object obj = new Object();
        builder.append(obj);
        assertEquals(17 * 37 + obj.hashCode(), builder.toHashCode());
    }

    @Test
    public void testAppendObjectArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        Object obj1 = new Object();
        Object obj2 = new Object();
        builder.append(new Object[]{obj1, obj2});
        assertEquals((17 * 37 + obj1.hashCode()) * 37 + obj2.hashCode(), builder.toHashCode());
    }

    @Test
    public void testAppendShort() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append((short) 1);
        assertEquals(17 * 37 + 1, builder.toHashCode());
    }

    @Test
    public void testAppendShortArray() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.append(new short[]{1, 2});
        assertEquals((17 * 37 + 1) * 37 + 2, builder.toHashCode());
    }

    @Test
    public void testAppendSuper() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        builder.appendSuper(1);
        assertEquals(17 * 37 + 1, builder.toHashCode());
    }

    @Test
    public void testReflectionHashCode() {
        Object obj = new Object();
        int hashCode = HashCodeBuilder.reflectionHashCode(obj);
        assertEquals(obj.hashCode(), hashCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflectionHashCodeWithNullObject() {
        HashCodeBuilder.reflectionHashCode(null);
    }

    @Test
    public void testReflectionHashCodeWithExcludeFields() {
        class TestClass {
            int a = 1;
            int b = 2;
        }
        TestClass obj = new TestClass();
        int hashCode = HashCodeBuilder.reflectionHashCode(obj, "a");
        assertEquals(17 * 37 + 2, hashCode);
    }

    @Test
    public void testBuild() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        assertEquals(builder.toHashCode(), builder.build().intValue());
    }

    @Test
    public void testHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37);
        assertEquals(builder.toHashCode(), builder.hashCode());
    }
}
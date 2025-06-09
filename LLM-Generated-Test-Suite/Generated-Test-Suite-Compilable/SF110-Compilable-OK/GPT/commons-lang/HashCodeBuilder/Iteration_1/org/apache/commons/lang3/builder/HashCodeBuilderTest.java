package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class HashCodeBuilderTest {

    private HashCodeBuilder builder;

    @Before
    public void setUp() {
        builder = new HashCodeBuilder();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(17, builder.toHashCode());
    }

    @Test
    public void testConstructorWithOddNumbers() {
        HashCodeBuilder customBuilder = new HashCodeBuilder(19, 39);
        assertEquals(19, customBuilder.toHashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEvenInitialNumber() {
        new HashCodeBuilder(18, 39);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEvenMultiplierNumber() {
        new HashCodeBuilder(19, 38);
    }

    @Test
    public void testAppendBoolean() {
        builder.append(true);
        assertEquals(629, builder.toHashCode());
    }

    @Test
    public void testAppendBooleanArray() {
        builder.append(new boolean[]{true, false});
        assertEquals(1164, builder.toHashCode());
    }

    @Test
    public void testAppendByte() {
        builder.append((byte) 1);
        assertEquals(654, builder.toHashCode());
    }

    @Test
    public void testAppendByteArray() {
        builder.append(new byte[]{1, 2});
        assertEquals(1308, builder.toHashCode());
    }

    @Test
    public void testAppendChar() {
        builder.append('a');
        assertEquals(654, builder.toHashCode());
    }

    @Test
    public void testAppendCharArray() {
        builder.append(new char[]{'a', 'b'});
        assertEquals(1308, builder.toHashCode());
    }

    @Test
    public void testAppendDouble() {
        builder.append(1.0);
        assertEquals(1072694240, builder.toHashCode());
    }

    @Test
    public void testAppendDoubleArray() {
        builder.append(new double[]{1.0, 2.0});
        assertEquals(2145388480, builder.toHashCode());
    }

    @Test
    public void testAppendFloat() {
        builder.append(1.0f);
        assertEquals(1065353216, builder.toHashCode());
    }

    @Test
    public void testAppendFloatArray() {
        builder.append(new float[]{1.0f, 2.0f});
        assertEquals(2130706432, builder.toHashCode());
    }

    @Test
    public void testAppendInt() {
        builder.append(1);
        assertEquals(654, builder.toHashCode());
    }

    @Test
    public void testAppendIntArray() {
        builder.append(new int[]{1, 2});
        assertEquals(1308, builder.toHashCode());
    }

    @Test
    public void testAppendLong() {
        builder.append(1L);
        assertEquals(654, builder.toHashCode());
    }

    @Test
    public void testAppendLongArray() {
        builder.append(new long[]{1L, 2L});
        assertEquals(1308, builder.toHashCode());
    }

    @Test
    public void testAppendObject() {
        builder.append("test");
        assertEquals(3556498, builder.toHashCode());
    }

    @Test
    public void testAppendObjectArray() {
        builder.append(new Object[]{"test", "hash"});
        assertEquals(110529410, builder.toHashCode());
    }

    @Test
    public void testAppendShort() {
        builder.append((short) 1);
        assertEquals(654, builder.toHashCode());
    }

    @Test
    public void testAppendShortArray() {
        builder.append(new short[]{1, 2});
        assertEquals(1308, builder.toHashCode());
    }

    @Test
    public void testAppendSuper() {
        builder.appendSuper(100);
        assertEquals(3817, builder.toHashCode());
    }

    @Test
    public void testReflectionHashCode() {
        TestObject obj = new TestObject(1, "test");
        int hash = HashCodeBuilder.reflectionHashCode(obj);
        assertNotNull(hash);
    }

    @Test
    public void testReflectionHashCodeWithExcludeFields() {
        TestObject obj = new TestObject(1, "test");
        int hash = HashCodeBuilder.reflectionHashCode(obj, "value");
        assertNotNull(hash);
    }

    @Test
    public void testReflectionHashCodeWithCollectionExcludeFields() {
        TestObject obj = new TestObject(1, "test");
        int hash = HashCodeBuilder.reflectionHashCode(obj, Arrays.asList("value"));
        assertNotNull(hash);
    }

    @Test
    public void testReflectionHashCodeWithTransients() {
        TestObject obj = new TestObject(1, "test");
        int hash = HashCodeBuilder.reflectionHashCode(obj, true);
        assertNotNull(hash);
    }

    @Test
    public void testRegistry() {
        Object obj = new Object();
        HashCodeBuilder.register(obj);
        assertTrue(HashCodeBuilder.isRegistered(obj));
        HashCodeBuilder.unregister(obj);
        assertFalse(HashCodeBuilder.isRegistered(obj));
    }

    private static class TestObject {
        private final int id;
        private final String value;

        public TestObject(int id, String value) {
            this.id = id;
            this.value = value;
        }
    }
}
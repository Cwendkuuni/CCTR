package org.apache.commons.lang3.builder;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CompareToBuilderTest {

    private CompareToBuilder compareToBuilder;

    @Before
    public void setUp() {
        compareToBuilder = new CompareToBuilder();
    }

    @Test
    public void testReflectionCompare() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2));

        obj2.setId(2);
        assertNotEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2));
    }

    @Test
    public void testReflectionCompareWithTransients() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, true));

        obj2.setId(2);
        assertNotEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, true));
    }

    @Test
    public void testReflectionCompareWithExcludeFields() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, Arrays.asList("name")));

        obj2.setId(2);
        assertNotEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, Arrays.asList("name")));
    }

    @Test
    public void testReflectionCompareWithExcludeFieldsArray() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, "name"));

        obj2.setId(2);
        assertNotEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, "name"));
    }

    @Test
    public void testReflectionCompareWithAllParams() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, false, null, "name"));

        obj2.setId(2);
        assertNotEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, false, null, "name"));
    }

    @Test
    public void testAppendSuper() {
        compareToBuilder.appendSuper(1);
        assertEquals(1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.appendSuper(-1);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendObjects() {
        compareToBuilder.append(1, 1);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendObjectsWithComparator() {
        Comparator<Integer> comparator = Comparator.reverseOrder();
        compareToBuilder.append(1, 1, comparator);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2, comparator);
        assertEquals(1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1, comparator);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendLong() {
        compareToBuilder.append(1L, 1L);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1L, 2L);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2L, 1L);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendInt() {
        compareToBuilder.append(1, 1);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendShort() {
        compareToBuilder.append((short) 1, (short) 1);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((short) 1, (short) 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((short) 2, (short) 1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendChar() {
        compareToBuilder.append('a', 'a');
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append('a', 'b');
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append('b', 'a');
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendByte() {
        compareToBuilder.append((byte) 1, (byte) 1);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((byte) 1, (byte) 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((byte) 2, (byte) 1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendDouble() {
        compareToBuilder.append(1.0, 1.0);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1.0, 2.0);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2.0, 1.0);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendFloat() {
        compareToBuilder.append(1.0f, 1.0f);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1.0f, 2.0f);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2.0f, 1.0f);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendBoolean() {
        compareToBuilder.append(true, true);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(true, false);
        assertEquals(1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(false, true);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendObjectArray() {
        compareToBuilder.append(new Object[]{1, 2}, new Object[]{1, 2});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object[]{1, 2}, new Object[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object[]{1, 3}, new Object[]{1, 2});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendObjectArrayWithComparator() {
        Comparator<Integer> comparator = Comparator.reverseOrder();
        compareToBuilder.append(new Object[]{1, 2}, new Object[]{1, 2}, comparator);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object[]{1, 2}, new Object[]{1, 3}, comparator);
        assertEquals(1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object[]{1, 3}, new Object[]{1, 2}, comparator);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendLongArray() {
        compareToBuilder.append(new long[]{1, 2}, new long[]{1, 2});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new long[]{1, 2}, new long[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new long[]{1, 3}, new long[]{1, 2});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendIntArray() {
        compareToBuilder.append(new int[]{1, 2}, new int[]{1, 2});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new int[]{1, 2}, new int[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new int[]{1, 3}, new int[]{1, 2});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendShortArray() {
        compareToBuilder.append(new short[]{1, 2}, new short[]{1, 2});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new short[]{1, 2}, new short[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new short[]{1, 3}, new short[]{1, 2});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendCharArray() {
        compareToBuilder.append(new char[]{'a', 'b'}, new char[]{'a', 'b'});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new char[]{'a', 'b'}, new char[]{'a', 'c'});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new char[]{'a', 'c'}, new char[]{'a', 'b'});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendByteArray() {
        compareToBuilder.append(new byte[]{1, 2}, new byte[]{1, 2});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new byte[]{1, 2}, new byte[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new byte[]{1, 3}, new byte[]{1, 2});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendDoubleArray() {
        compareToBuilder.append(new double[]{1.0, 2.0}, new double[]{1.0, 2.0});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new double[]{1.0, 2.0}, new double[]{1.0, 3.0});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new double[]{1.0, 3.0}, new double[]{1.0, 2.0});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendFloatArray() {
        compareToBuilder.append(new float[]{1.0f, 2.0f}, new float[]{1.0f, 2.0f});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new float[]{1.0f, 2.0f}, new float[]{1.0f, 3.0f});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new float[]{1.0f, 3.0f}, new float[]{1.0f, 2.0f});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppendBooleanArray() {
        compareToBuilder.append(new boolean[]{true, false}, new boolean[]{true, false});
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new boolean[]{true, false}, new boolean[]{true, true});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new boolean[]{true, true}, new boolean[]{true, false});
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testToComparison() {
        compareToBuilder.append(1, 1);
        assertEquals(0, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testBuild() {
        compareToBuilder.append(1, 1);
        assertEquals(Integer.valueOf(0), compareToBuilder.build());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2);
        assertEquals(Integer.valueOf(-1), compareToBuilder.build());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1);
        assertEquals(Integer.valueOf(1), compareToBuilder.build());
    }

    private static class TestClass {
        private int id;
        private String name;

        public TestClass(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
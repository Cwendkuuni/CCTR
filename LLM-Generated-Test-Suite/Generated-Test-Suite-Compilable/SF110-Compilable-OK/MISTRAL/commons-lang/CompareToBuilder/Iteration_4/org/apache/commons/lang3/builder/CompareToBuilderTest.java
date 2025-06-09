package org.apache.commons.lang3.builder;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CompareToBuilderTest {

    @Test
    public void testReflectionCompare() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        TestClass obj3 = new TestClass(2, "test");

        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj1));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2));
        assertTrue(CompareToBuilder.reflectionCompare(obj1, obj3) < 0);
        assertTrue(CompareToBuilder.reflectionCompare(obj3, obj1) > 0);
    }

    @Test
    public void testReflectionCompareWithTransients() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        TestClass obj3 = new TestClass(2, "test");

        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj1, true));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, true));
        assertTrue(CompareToBuilder.reflectionCompare(obj1, obj3, true) < 0);
        assertTrue(CompareToBuilder.reflectionCompare(obj3, obj1, true) > 0);
    }

    @Test
    public void testReflectionCompareWithExcludeFields() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        TestClass obj3 = new TestClass(2, "test");

        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj1, Arrays.asList("field1")));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, Arrays.asList("field1")));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj3, Arrays.asList("field1")));
    }

    @Test
    public void testReflectionCompareWithExcludeFieldsArray() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        TestClass obj3 = new TestClass(2, "test");

        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj1, "field1"));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, "field1"));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj3, "field1"));
    }

    @Test
    public void testReflectionCompareWithAllParams() {
        TestClass obj1 = new TestClass(1, "test");
        TestClass obj2 = new TestClass(1, "test");
        TestClass obj3 = new TestClass(2, "test");

        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj1, true, null, "field1"));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, true, null, "field1"));
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj3, true, null, "field1"));
    }

    @Test
    public void testAppendSuper() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(0, builder.toComparison());
        builder.appendSuper(1);
        assertEquals(1, builder.toComparison());
    }

    @Test
    public void testAppendObjects() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(0, builder.append(1, 1).toComparison());
        assertTrue(builder.append(1, 2).toComparison() < 0);
        assertTrue(builder.append(2, 1).toComparison() > 0);
    }

    @Test
    public void testAppendObjectsWithComparator() {
        CompareToBuilder builder = new CompareToBuilder();
        Comparator<Integer> comparator = Comparator.naturalOrder();
        assertEquals(0, builder.append(1, 1, comparator).toComparison());
        assertTrue(builder.append(1, 2, comparator).toComparison() < 0);
        assertTrue(builder.append(2, 1, comparator).toComparison() > 0);
    }

    @Test
    public void testAppendPrimitives() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(0, builder.append(1L, 1L).toComparison());
        assertTrue(builder.append(1L, 2L).toComparison() < 0);
        assertTrue(builder.append(2L, 1L).toComparison() > 0);

        assertEquals(0, builder.append(1, 1).toComparison());
        assertTrue(builder.append(1, 2).toComparison() < 0);
        assertTrue(builder.append(2, 1).toComparison() > 0);

        assertEquals(0, builder.append((short) 1, (short) 1).toComparison());
        assertTrue(builder.append((short) 1, (short) 2).toComparison() < 0);
        assertTrue(builder.append((short) 2, (short) 1).toComparison() > 0);

        assertEquals(0, builder.append('a', 'a').toComparison());
        assertTrue(builder.append('a', 'b').toComparison() < 0);
        assertTrue(builder.append('b', 'a').toComparison() > 0);

        assertEquals(0, builder.append((byte) 1, (byte) 1).toComparison());
        assertTrue(builder.append((byte) 1, (byte) 2).toComparison() < 0);
        assertTrue(builder.append((byte) 2, (byte) 1).toComparison() > 0);

        assertEquals(0, builder.append(1.0, 1.0).toComparison());
        assertTrue(builder.append(1.0, 2.0).toComparison() < 0);
        assertTrue(builder.append(2.0, 1.0).toComparison() > 0);

        assertEquals(0, builder.append(1.0f, 1.0f).toComparison());
        assertTrue(builder.append(1.0f, 2.0f).toComparison() < 0);
        assertTrue(builder.append(2.0f, 1.0f).toComparison() > 0);

        assertEquals(0, builder.append(true, true).toComparison());
        assertTrue(builder.append(false, true).toComparison() < 0);
        assertTrue(builder.append(true, false).toComparison() > 0);
    }

    @Test
    public void testAppendArrays() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(0, builder.append(new int[]{1, 2}, new int[]{1, 2}).toComparison());
        assertTrue(builder.append(new int[]{1, 2}, new int[]{1, 3}).toComparison() < 0);
        assertTrue(builder.append(new int[]{1, 3}, new int[]{1, 2}).toComparison() > 0);

        assertEquals(0, builder.append(new long[]{1L, 2L}, new long[]{1L, 2L}).toComparison());
        assertTrue(builder.append(new long[]{1L, 2L}, new long[]{1L, 3L}).toComparison() < 0);
        assertTrue(builder.append(new long[]{1L, 3L}, new long[]{1L, 2L}).toComparison() > 0);

        assertEquals(0, builder.append(new short[]{1, 2}, new short[]{1, 2}).toComparison());
        assertTrue(builder.append(new short[]{1, 2}, new short[]{1, 3}).toComparison() < 0);
        assertTrue(builder.append(new short[]{1, 3}, new short[]{1, 2}).toComparison() > 0);

        assertEquals(0, builder.append(new char[]{'a', 'b'}, new char[]{'a', 'b'}).toComparison());
        assertTrue(builder.append(new char[]{'a', 'b'}, new char[]{'a', 'c'}).toComparison() < 0);
        assertTrue(builder.append(new char[]{'a', 'c'}, new char[]{'a', 'b'}).toComparison() > 0);

        assertEquals(0, builder.append(new byte[]{1, 2}, new byte[]{1, 2}).toComparison());
        assertTrue(builder.append(new byte[]{1, 2}, new byte[]{1, 3}).toComparison() < 0);
        assertTrue(builder.append(new byte[]{1, 3}, new byte[]{1, 2}).toComparison() > 0);

        assertEquals(0, builder.append(new double[]{1.0, 2.0}, new double[]{1.0, 2.0}).toComparison());
        assertTrue(builder.append(new double[]{1.0, 2.0}, new double[]{1.0, 3.0}).toComparison() < 0);
        assertTrue(builder.append(new double[]{1.0, 3.0}, new double[]{1.0, 2.0}).toComparison() > 0);

        assertEquals(0, builder.append(new float[]{1.0f, 2.0f}, new float[]{1.0f, 2.0f}).toComparison());
        assertTrue(builder.append(new float[]{1.0f, 2.0f}, new float[]{1.0f, 3.0f}).toComparison() < 0);
        assertTrue(builder.append(new float[]{1.0f, 3.0f}, new float[]{1.0f, 2.0f}).toComparison() > 0);

        assertEquals(0, builder.append(new boolean[]{true, false}, new boolean[]{true, false}).toComparison());
        assertTrue(builder.append(new boolean[]{true, false}, new boolean[]{true, true}).toComparison() < 0);
        assertTrue(builder.append(new boolean[]{true, true}, new boolean[]{true, false}).toComparison() > 0);
    }

    @Test
    public void testToComparison() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(0, builder.toComparison());
        builder.append(1, 2);
        assertTrue(builder.toComparison() < 0);
    }

    @Test
    public void testBuild() {
        CompareToBuilder builder = new CompareToBuilder();
        assertEquals(Integer.valueOf(0), builder.build());
        builder.append(1, 2);
        assertTrue(builder.build() < 0);
    }

    private static class TestClass {
        private int field1;
        private String field2;

        public TestClass(int field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public int getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }
}
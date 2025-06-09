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
    public void testReflectionCompare_SameObjects() {
        Object obj = new Object();
        assertEquals(0, CompareToBuilder.reflectionCompare(obj, obj));
    }

    @Test(expected = NullPointerException.class)
    public void testReflectionCompare_NullObjects() {
        CompareToBuilder.reflectionCompare(null, new Object());
    }

    @Test(expected = ClassCastException.class)
    public void testReflectionCompare_DifferentClasses() {
        CompareToBuilder.reflectionCompare(new Object(), new String());
    }

    @Test
    public void testReflectionCompare_WithExcludeFields() {
        class TestClass {
            int field1;
            int field2;
        }
        TestClass obj1 = new TestClass();
        TestClass obj2 = new TestClass();
        obj1.field1 = 1;
        obj2.field1 = 2;
        obj1.field2 = 3;
        obj2.field2 = 3;
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, Arrays.asList("field1")));
    }

    @Test
    public void testReflectionCompare_WithTransients() {
        class TestClass {
            transient int field1;
            int field2;
        }
        TestClass obj1 = new TestClass();
        TestClass obj2 = new TestClass();
        obj1.field1 = 1;
        obj2.field1 = 2;
        obj1.field2 = 3;
        obj2.field2 = 3;
        assertEquals(0, CompareToBuilder.reflectionCompare(obj1, obj2, true));
    }

    @Test
    public void testAppendSuper() {
        compareToBuilder.appendSuper(1);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_SameObjects() {
        Object obj = new Object();
        compareToBuilder.append(obj, obj);
        assertEquals(0, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_NullObjects() {
        compareToBuilder.append(null, new Object());
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object(), null);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_WithComparator() {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        compareToBuilder.append(1, 2, comparator);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_PrimitiveTypes() {
        compareToBuilder.append(1L, 2L);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1, 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((short) 1, (short) 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append('a', 'b');
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append((byte) 1, (byte) 2);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1.0, 2.0);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(1.0f, 2.0f);
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(true, false);
        assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_Arrays() {
        compareToBuilder.append(new int[]{1, 2}, new int[]{1, 3});
        assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new String[]{"a", "b"}, new String[]{"a", "c"});
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testToComparison() {
        compareToBuilder.append(1, 2);
        assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testBuild() {
        compareToBuilder.append(1, 2);
        assertEquals(Integer.valueOf(-1), compareToBuilder.build());
    }
}
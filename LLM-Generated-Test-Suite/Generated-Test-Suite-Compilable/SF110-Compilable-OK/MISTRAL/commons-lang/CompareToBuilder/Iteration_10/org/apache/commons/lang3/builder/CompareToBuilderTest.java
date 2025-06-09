package org.apache.commons.lang3.builder;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import java.util.Arrays;
import java.util.Collections;

public class CompareToBuilderTest {

    private CompareToBuilder compareToBuilder;

    @Before
    public void setUp() {
        compareToBuilder = new CompareToBuilder();
    }

    @Test
    public void testReflectionCompare_SameObjects() {
        Object obj = new Object();
        int result = CompareToBuilder.reflectionCompare(obj, obj);
        Assert.assertEquals(0, result);
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
        obj1.field1 = 1;
        obj1.field2 = 2;

        TestClass obj2 = new TestClass();
        obj2.field1 = 1;
        obj2.field2 = 3;

        int result = CompareToBuilder.reflectionCompare(obj1, obj2, Collections.singletonList("field2"));
        Assert.assertEquals(0, result);
    }

    @Test
    public void testAppendSuper() {
        compareToBuilder.appendSuper(1);
        Assert.assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_SameObjects() {
        Object obj = new Object();
        compareToBuilder.append(obj, obj);
        Assert.assertEquals(0, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_NullObjects() {
        compareToBuilder.append(null, new Object());
        Assert.assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(new Object(), null);
        Assert.assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_ComparableObjects() {
        compareToBuilder.append(1, 2);
        Assert.assertEquals(-1, compareToBuilder.toComparison());

        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(2, 1);
        Assert.assertEquals(1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_Arrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        compareToBuilder.append(array1, array2);
        Assert.assertEquals(0, compareToBuilder.toComparison());

        int[] array3 = {1, 2, 4};
        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(array1, array3);
        Assert.assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testAppend_PrimitiveArrays() {
        long[] array1 = {1L, 2L, 3L};
        long[] array2 = {1L, 2L, 3L};
        compareToBuilder.append(array1, array2);
        Assert.assertEquals(0, compareToBuilder.toComparison());

        long[] array3 = {1L, 2L, 4L};
        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(array1, array3);
        Assert.assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testToComparison() {
        compareToBuilder.append(1, 2);
        Assert.assertEquals(-1, compareToBuilder.toComparison());
    }

    @Test
    public void testBuild() {
        compareToBuilder.append(1, 2);
        Assert.assertEquals(-1, (int) compareToBuilder.build());
    }
}
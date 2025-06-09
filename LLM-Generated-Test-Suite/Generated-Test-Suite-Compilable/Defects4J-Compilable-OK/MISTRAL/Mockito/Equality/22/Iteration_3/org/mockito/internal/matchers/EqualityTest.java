package org.mockito.internal.matchers;

import org.junit.Test;
import static org.junit.Assert.*;

public class EqualityTest {

    @Test
    public void testAreEqual_NullValues() {
        assertTrue(Equality.areEqual(null, null));
        assertFalse(Equality.areEqual(null, "test"));
        assertFalse(Equality.areEqual("test", null));
    }

    @Test
    public void testAreEqual_SameObjects() {
        String obj = "test";
        assertTrue(Equality.areEqual(obj, obj));
    }

    @Test
    public void testAreEqual_EqualObjects() {
        assertTrue(Equality.areEqual("test", "test"));
        assertTrue(Equality.areEqual(123, 123));
    }

    @Test
    public void testAreEqual_DifferentObjects() {
        assertFalse(Equality.areEqual("test", "different"));
        assertFalse(Equality.areEqual(123, 456));
    }

    @Test
    public void testAreEqual_Arrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {1, 2, 4};

        assertTrue(Equality.areEqual(array1, array2));
        assertFalse(Equality.areEqual(array1, array3));
    }

    @Test
    public void testAreArraysEqual_SameArrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};

        assertTrue(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArraysEqual_DifferentArrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};

        assertFalse(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_SameLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};

        assertTrue(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_DifferentLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2};

        assertFalse(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_SameElements() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};

        assertTrue(Equality.areArrayElementsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_DifferentElements() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};

        assertFalse(Equality.areArrayElementsEqual(array1, array2));
    }

    @Test
    public void testIsArray_Array() {
        int[] array = {1, 2, 3};

        assertTrue(Equality.isArray(array));
    }

    @Test
    public void testIsArray_NotArray() {
        String obj = "test";

        assertFalse(Equality.isArray(obj));
    }
}
package org.mockito.internal.matchers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EqualityTest {

    @Test
    public void testAreEqual_BothNull() {
        assertTrue(Equality.areEqual(null, null));
    }

    @Test
    public void testAreEqual_FirstNull() {
        assertFalse(Equality.areEqual(null, "test"));
    }

    @Test
    public void testAreEqual_SecondNull() {
        assertFalse(Equality.areEqual("test", null));
    }

    @Test
    public void testAreEqual_BothObjectsEqual() {
        assertTrue(Equality.areEqual("test", "test"));
    }

    @Test
    public void testAreEqual_BothObjectsNotEqual() {
        assertFalse(Equality.areEqual("test", "different"));
    }

    @Test
    public void testAreEqual_BothArraysEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areEqual(array1, array2));
    }

    @Test
    public void testAreEqual_BothArraysNotEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areEqual(array1, array2));
    }

    @Test
    public void testAreEqual_OneArrayOneObject() {
        int[] array = {1, 2, 3};
        assertFalse(Equality.areEqual(array, "test"));
    }

    @Test
    public void testAreArraysEqual_BothArraysEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArraysEqual_BothArraysNotEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_BothArraysSameLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};
        assertTrue(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_BothArraysDifferentLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2};
        assertFalse(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_BothArraysElementsEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArrayElementsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_BothArraysElementsNotEqual() {
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
        String str = "test";
        assertFalse(Equality.isArray(str));
    }
}
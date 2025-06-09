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
        assertFalse(Equality.areEqual(null, new Object()));
    }

    @Test
    public void testAreEqual_SecondNull() {
        assertFalse(Equality.areEqual(new Object(), null));
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
    public void testAreEqual_DifferentTypes() {
        assertFalse(Equality.areEqual("test", new int[]{1, 2, 3}));
    }

    @Test
    public void testAreArraysEqual_EqualArrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArraysEqual_DifferentLengths() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2};
        assertFalse(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArraysEqual_DifferentElements() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_EqualLengths() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_DifferentLengths() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2};
        assertFalse(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_EqualElements() {
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
        assertTrue(Equality.isArray(new int[]{1, 2, 3}));
    }

    @Test
    public void testIsArray_NotArray() {
        assertFalse(Equality.isArray(new Object()));
    }
}
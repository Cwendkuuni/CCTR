package org.mockito.internal.matchers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EqualityTest {

    @Test
    public void testAreEqual_bothNull() {
        assertTrue(Equality.areEqual(null, null));
    }

    @Test
    public void testAreEqual_firstNull() {
        assertFalse(Equality.areEqual(null, "test"));
    }

    @Test
    public void testAreEqual_secondNull() {
        assertFalse(Equality.areEqual("test", null));
    }

    @Test
    public void testAreEqual_bothObjectsEqual() {
        assertTrue(Equality.areEqual("test", "test"));
    }

    @Test
    public void testAreEqual_bothObjectsNotEqual() {
        assertFalse(Equality.areEqual("test", "different"));
    }

    @Test
    public void testAreEqual_bothArraysEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areEqual(array1, array2));
    }

    @Test
    public void testAreEqual_bothArraysNotEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areEqual(array1, array2));
    }

    @Test
    public void testAreEqual_differentTypes() {
        assertFalse(Equality.areEqual("test", new int[]{1, 2, 3}));
    }

    @Test
    public void testAreArraysEqual_bothArraysEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArraysEqual_bothArraysNotEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areArraysEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_bothArraysEqualLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayLengthsEqual_bothArraysDifferentLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2};
        assertFalse(Equality.areArrayLengthsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_bothArraysEqualElements() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertTrue(Equality.areArrayElementsEqual(array1, array2));
    }

    @Test
    public void testAreArrayElementsEqual_bothArraysNotEqualElements() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        assertFalse(Equality.areArrayElementsEqual(array1, array2));
    }

    @Test
    public void testIsArray_isArray() {
        int[] array = {1, 2, 3};
        assertTrue(Equality.isArray(array));
    }

    @Test
    public void testIsArray_isNotArray() {
        String str = "test";
        assertFalse(Equality.isArray(str));
    }
}
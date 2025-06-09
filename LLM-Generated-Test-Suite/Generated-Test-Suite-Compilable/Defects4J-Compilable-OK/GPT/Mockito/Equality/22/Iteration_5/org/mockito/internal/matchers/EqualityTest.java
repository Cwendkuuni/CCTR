package org.mockito.internal.matchers;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.internal.matchers.Equality;

public class EqualityTest {

    @Test
    public void testAreEqualWithBothNull() {
        assertTrue(Equality.areEqual(null, null));
    }

    @Test
    public void testAreEqualWithOneNull() {
        assertFalse(Equality.areEqual(null, new Object()));
        assertFalse(Equality.areEqual(new Object(), null));
    }

    @Test
    public void testAreEqualWithNonArrayObjects() {
        assertTrue(Equality.areEqual("test", "test"));
        assertFalse(Equality.areEqual("test", "different"));
    }

    @Test
    public void testAreEqualWithArrays() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {1, 2, 4};
        int[] array4 = {1, 2, 3, 4};

        assertTrue(Equality.areEqual(array1, array2));
        assertFalse(Equality.areEqual(array1, array3));
        assertFalse(Equality.areEqual(array1, array4));
    }

    @Test
    public void testAreEqualWithDifferentTypes() {
        int[] intArray = {1, 2, 3};
        String[] stringArray = {"1", "2", "3"};

        assertFalse(Equality.areEqual(intArray, stringArray));
    }

    @Test
    public void testAreArraysEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {1, 2, 4};

        assertTrue(Equality.areArraysEqual(array1, array2));
        assertFalse(Equality.areArraysEqual(array1, array3));
    }

    @Test
    public void testAreArrayLengthsEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};
        int[] array3 = {7, 8};

        assertTrue(Equality.areArrayLengthsEqual(array1, array2));
        assertFalse(Equality.areArrayLengthsEqual(array1, array3));
    }

    @Test
    public void testAreArrayElementsEqual() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {1, 2, 4};

        assertTrue(Equality.areArrayElementsEqual(array1, array2));
        assertFalse(Equality.areArrayElementsEqual(array1, array3));
    }

    @Test
    public void testIsArray() {
        int[] intArray = {1, 2, 3};
        String string = "test";

        assertTrue(Equality.isArray(intArray));
        assertFalse(Equality.isArray(string));
    }
}
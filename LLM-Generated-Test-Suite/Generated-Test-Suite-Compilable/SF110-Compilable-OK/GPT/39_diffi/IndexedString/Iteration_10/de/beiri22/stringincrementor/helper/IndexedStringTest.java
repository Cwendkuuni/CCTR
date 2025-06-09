package de.beiri22.stringincrementor.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class IndexedStringTest {

    private IndexedString indexedString;

    @Before
    public void setUp() {
        indexedString = new IndexedString("hello world");
    }

    @Test
    public void testConstructor() {
        // Test that the constructor initializes the values array correctly
        char[] expectedValues = {'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'};
        assertArrayEquals(expectedValues, getPrivateFieldValues(indexedString));

        // Test that the constructor initializes the index array correctly
        int[][] index = getPrivateFieldIndex(indexedString);
        assertNotNull(index);
        assertEquals(256, index.length);
        assertArrayEquals(new int[]{0}, index['h']);
        assertArrayEquals(new int[]{1}, index['e']);
        assertArrayEquals(new int[]{2, 3, 9}, index['l']);
        assertArrayEquals(new int[]{4, 7}, index['o']);
        assertArrayEquals(new int[]{5}, index[' ']);
        assertArrayEquals(new int[]{6}, index['w']);
        assertArrayEquals(new int[]{8}, index['r']);
        assertArrayEquals(new int[]{10}, index['d']);
    }

    @Test
    public void testIndexOf_Found() {
        // Test that indexOf returns the correct index when the target is found
        assertEquals(0, indexedString.indexOf(new char[]{'h'}));
        assertEquals(2, indexedString.indexOf(new char[]{'l', 'l'}));
        assertEquals(6, indexedString.indexOf(new char[]{'w', 'o', 'r', 'l', 'd'}));
    }

    @Test
    public void testIndexOf_NotFound() {
        // Test that indexOf returns -1 when the target is not found
        assertEquals(-1, indexedString.indexOf(new char[]{'x'}));
        assertEquals(-1, indexedString.indexOf(new char[]{'h', 'e', 'l', 'l', 'o', 'x'}));
    }

    @Test
    public void testIndexOf_EmptyTarget() {
        // Test that indexOf returns -1 when the target is empty
        assertEquals(-1, indexedString.indexOf(new char[]{}));
    }

    @Test
    public void testIndexOf_TargetLongerThanSource() {
        // Test that indexOf returns -1 when the target is longer than the source
        assertEquals(-1, indexedString.indexOf(new char[]{'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd', '!'}));
    }

    // Helper method to access private field 'values' using reflection
    private char[] getPrivateFieldValues(IndexedString obj) {
        try {
            java.lang.reflect.Field field = IndexedString.class.getDeclaredField("values");
            field.setAccessible(true);
            return (char[]) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to access private field 'index' using reflection
    private int[][] getPrivateFieldIndex(IndexedString obj) {
        try {
            java.lang.reflect.Field field = IndexedString.class.getDeclaredField("index");
            field.setAccessible(true);
            return (int[][]) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
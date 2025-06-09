package de.beiri22.stringincrementor.helper;

import static org.junit.Assert.assertEquals;
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
        // Test if the constructor correctly initializes the values array
        char[] expectedValues = {'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'};
        assertArrayEquals(expectedValues, getValues(indexedString));

        // Test if the constructor correctly initializes the index array
        int[][] expectedIndex = new int[256][];
        expectedIndex['h'] = new int[]{0};
        expectedIndex['e'] = new int[]{1};
        expectedIndex['l'] = new int[]{2, 3, 9};
        expectedIndex['o'] = new int[]{4, 7};
        expectedIndex[' '] = new int[]{5};
        expectedIndex['w'] = new int[]{6};
        expectedIndex['r'] = new int[]{8};
        expectedIndex['d'] = new int[]{10};

        for (char c = '\0'; c < '\u0100'; ++c) {
            if (expectedIndex[c] != null) {
                assertArrayEquals(expectedIndex[c], getIndex(indexedString, c));
            } else {
                assertEquals(0, getIndex(indexedString, c).length);
            }
        }
    }

    @Test
    public void testIndexOf() {
        // Test finding a substring at the beginning
        assertEquals(0, indexedString.indexOf("hello".toCharArray()));

        // Test finding a substring in the middle
        assertEquals(6, indexedString.indexOf("world".toCharArray()));

        // Test finding a single character
        assertEquals(4, indexedString.indexOf("o".toCharArray()));

        // Test finding a substring that doesn't exist
        assertEquals(-1, indexedString.indexOf("bye".toCharArray()));

        // Test finding a substring that partially matches
        assertEquals(-1, indexedString.indexOf("helloo".toCharArray()));

        // Test finding a substring with repeated characters
        assertEquals(2, indexedString.indexOf("ll".toCharArray()));
    }

    // Helper methods to access private fields for testing purposes
    private char[] getValues(IndexedString indexedString) {
        try {
            java.lang.reflect.Field field = IndexedString.class.getDeclaredField("values");
            field.setAccessible(true);
            return (char[]) field.get(indexedString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int[] getIndex(IndexedString indexedString, char c) {
        try {
            java.lang.reflect.Field field = IndexedString.class.getDeclaredField("index");
            field.setAccessible(true);
            int[][] index = (int[][]) field.get(indexedString);
            return index[c];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
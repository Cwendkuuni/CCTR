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
        assertArrayEquals(expectedValues, getPrivateFieldValues(indexedString, "values"));

        // Test if the constructor correctly initializes the index array
        int[][] index = getPrivateFieldValues(indexedString, "index");
        assertEquals(1, index['h'].length);
        assertEquals(1, index['e'].length);
        assertEquals(3, index['l'].length);
        assertEquals(2, index['o'].length);
        assertEquals(1, index[' '].length);
        assertEquals(1, index['w'].length);
        assertEquals(1, index['r'].length);
        assertEquals(1, index['d'].length);
    }

    @Test
    public void testIndexOf() {
        // Test finding a single character
        assertEquals(0, indexedString.indexOf(new char[]{'h'}));
        assertEquals(4, indexedString.indexOf(new char[]{'o'}));
        assertEquals(6, indexedString.indexOf(new char[]{'w'}));

        // Test finding a substring
        assertEquals(0, indexedString.indexOf(new char[]{'h', 'e'}));
        assertEquals(2, indexedString.indexOf(new char[]{'l', 'l'}));
        assertEquals(3, indexedString.indexOf(new char[]{'l', 'o'}));
        assertEquals(6, indexedString.indexOf(new char[]{'w', 'o', 'r'}));

        // Test not finding a substring
        assertEquals(-1, indexedString.indexOf(new char[]{'x'}));
        assertEquals(-1, indexedString.indexOf(new char[]{'h', 'a'}));
        assertEquals(-1, indexedString.indexOf(new char[]{'o', 'l', 'l'}));
    }

    @Test
    public void testIndexOfWithEmptyTarget() {
        // Test with an empty target array
        assertEquals(-1, indexedString.indexOf(new char[]{}));
    }

    @Test
    public void testIndexOfWithFullMatch() {
        // Test with a target array that matches the entire string
        assertEquals(0, indexedString.indexOf(new char[]{'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'}));
    }

    @Test
    public void testIndexOfWithNonExistentCharacter() {
        // Test with a character that does not exist in the string
        assertEquals(-1, indexedString.indexOf(new char[]{'z'}));
    }

    // Helper method to access private fields using reflection
    @SuppressWarnings("unchecked")
    private <T> T getPrivateFieldValues(Object instance, String fieldName) {
        try {
            java.lang.reflect.Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
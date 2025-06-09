package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class ValidateTest {

    @Test
    public void testIsTrueWithLong() {
        Validate.isTrue(true, "The value %d is not true", 1L);
        try {
            Validate.isTrue(false, "The value %d is not true", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1 is not true", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithDouble() {
        Validate.isTrue(true, "The value %f is not true", 1.0);
        try {
            Validate.isTrue(false, "The value %f is not true", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1.000000 is not true", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithVarargs() {
        Validate.isTrue(true, "The value %s is not true", "test");
        try {
            Validate.isTrue(false, "The value %s is not true", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value test is not true", e.getMessage());
        }
    }

    @Test
    public void testIsTrue() {
        Validate.isTrue(true);
        try {
            Validate.isTrue(false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated expression is false", e.getMessage());
        }
    }

    @Test
    public void testNotNull() {
        String obj = "test";
        assertSame(obj, Validate.notNull(obj));
        try {
            Validate.notNull(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("The validated object is null", e.getMessage());
        }
    }

    @Test
    public void testNotNullWithMessage() {
        String obj = "test";
        assertSame(obj, Validate.notNull(obj, "Object is null"));
        try {
            Validate.notNull(null, "Object is null");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Object is null", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyArray() {
        String[] array = {"test"};
        assertSame(array, Validate.notEmpty(array));
        try {
            Validate.notEmpty(new String[0]);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated array is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyArrayWithMessage() {
        String[] array = {"test"};
        assertSame(array, Validate.notEmpty(array, "Array is empty"));
        try {
            Validate.notEmpty(new String[0], "Array is empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Array is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCollection() {
        List<String> collection = Arrays.asList("test");
        assertSame(collection, Validate.notEmpty(collection));
        try {
            Validate.notEmpty(new ArrayList<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated collection is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCollectionWithMessage() {
        List<String> collection = Arrays.asList("test");
        assertSame(collection, Validate.notEmpty(collection, "Collection is empty"));
        try {
            Validate.notEmpty(new ArrayList<>(), "Collection is empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Collection is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertSame(map, Validate.notEmpty(map));
        try {
            Validate.notEmpty(new HashMap<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated map is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyMapWithMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertSame(map, Validate.notEmpty(map, "Map is empty"));
        try {
            Validate.notEmpty(new HashMap<>(), "Map is empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Map is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCharSequence() {
        String chars = "test";
        assertSame(chars, Validate.notEmpty(chars));
        try {
            Validate.notEmpty("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCharSequenceWithMessage() {
        String chars = "test";
        assertSame(chars, Validate.notEmpty(chars, "CharSequence is empty"));
        try {
            Validate.notEmpty("", "CharSequence is empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("CharSequence is empty", e.getMessage());
        }
    }

    @Test
    public void testNotBlank() {
        String chars = "test";
        assertSame(chars, Validate.notBlank(chars));
        try {
            Validate.notBlank(" ");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    @Test
    public void testNotBlankWithMessage() {
        String chars = "test";
        assertSame(chars, Validate.notBlank(chars, "CharSequence is blank"));
        try {
            Validate.notBlank(" ", "CharSequence is blank");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("CharSequence is blank", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsArray() {
        String[] array = {"test"};
        assertSame(array, Validate.noNullElements(array));
        try {
            Validate.noNullElements(new String[]{null});
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated array contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsArrayWithMessage() {
        String[] array = {"test"};
        assertSame(array, Validate.noNullElements(array, "Array contains null element at index: %d"));
        try {
            Validate.noNullElements(new String[]{null}, "Array contains null element at index: %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Array contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsIterable() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.noNullElements(list));
        try {
            Validate.noNullElements(Arrays.asList((String) null));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated collection contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsIterableWithMessage() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.noNullElements(list, "Collection contains null element at index: %d"));
        try {
            Validate.noNullElements(Arrays.asList((String) null), "Collection contains null element at index: %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Collection contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testValidIndexArray() {
        String[] array = {"test"};
        assertSame(array, Validate.validIndex(array, 0));
        try {
            Validate.validIndex(array, 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated array index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndexArrayWithMessage() {
        String[] array = {"test"};
        assertSame(array, Validate.validIndex(array, 0, "Array index is invalid: %d"));
        try {
            Validate.validIndex(array, 1, "Array index is invalid: %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Array index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCollection() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.validIndex(list, 0));
        try {
            Validate.validIndex(list, 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated collection index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCollectionWithMessage() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.validIndex(list, 0, "Collection index is invalid: %d"));
        try {
            Validate.validIndex(list, 1, "Collection index is invalid: %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Collection index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCharSequence() {
        String chars = "test";
        assertSame(chars, Validate.validIndex(chars, 0));
        try {
            Validate.validIndex(chars, 4);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated character sequence index is invalid: 4", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCharSequenceWithMessage() {
        String chars = "test";
        assertSame(chars, Validate.validIndex(chars, 0, "CharSequence index is invalid: %d"));
        try {
            Validate.validIndex(chars, 4, "CharSequence index is invalid: %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("CharSequence index is invalid: 4", e.getMessage());
        }
    }

    @Test
    public void testValidState() {
        Validate.validState(true);
        try {
            Validate.validState(false);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("The validated state is false", e.getMessage());
        }
    }

    @Test
    public void testValidStateWithMessage() {
        Validate.validState(true, "State is false");
        try {
            Validate.validState(false, "State is false");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("State is false", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern() {
        Validate.matchesPattern("test", "test");
        try {
            Validate.matchesPattern("test", "wrong");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The string test does not match the pattern wrong", e.getMessage());
        }
    }

    @Test
    public void testMatchesPatternWithMessage() {
        Validate.matchesPattern("test", "test", "Pattern does not match");
        try {
            Validate.matchesPattern("test", "wrong", "Pattern does not match");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Pattern does not match", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween() {
        Validate.inclusiveBetween(1, 3, 2);
        try {
            Validate.inclusiveBetween(1, 3, 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenWithMessage() {
        Validate.inclusiveBetween(1, 3, 2, "Value is out of range");
        try {
            Validate.inclusiveBetween(1, 3, 4, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        Validate.inclusiveBetween(1L, 3L, 2L);
        try {
            Validate.inclusiveBetween(1L, 3L, 4L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLongWithMessage() {
        Validate.inclusiveBetween(1L, 3L, 2L, "Value is out of range");
        try {
            Validate.inclusiveBetween(1L, 3L, 4L, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0);
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4.0 is not in the specified inclusive range of 1.0 to 3.0", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDoubleWithMessage() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0, "Value is out of range");
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        Validate.exclusiveBetween(1, 3, 2);
        try {
            Validate.exclusiveBetween(1, 3, 3);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenWithMessage() {
        Validate.exclusiveBetween(1, 3, 2, "Value is out of range");
        try {
            Validate.exclusiveBetween(1, 3, 3, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Validate.exclusiveBetween(1L, 3L, 2L);
        try {
            Validate.exclusiveBetween(1L, 3L, 3L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLongWithMessage() {
        Validate.exclusiveBetween(1L, 3L, 2L, "Value is out of range");
        try {
            Validate.exclusiveBetween(1L, 3L, 3L, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0);
        try {
            Validate.exclusiveBetween(1.0, 3.0, 3.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3.0 is not in the specified exclusive range of 1.0 to 3.0", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDoubleWithMessage() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0, "Value is out of range");
        try {
            Validate.exclusiveBetween(1.0, 3.0, 3.0, "Value is out of range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value is out of range", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf() {
        Validate.isInstanceOf(String.class, "test");
        try {
            Validate.isInstanceOf(Integer.class, "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Expected type: java.lang.Integer, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOfWithMessage() {
        Validate.isInstanceOf(String.class, "test", "Not an instance");
        try {
            Validate.isInstanceOf(Integer.class, "test", "Not an instance");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Not an instance", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom() {
        Validate.isAssignableFrom(Number.class, Integer.class);
        try {
            Validate.isAssignableFrom(String.class, Integer.class);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot assign a java.lang.Integer to a java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFromWithMessage() {
        Validate.isAssignableFrom(Number.class, Integer.class, "Not assignable");
        try {
            Validate.isAssignableFrom(String.class, Integer.class, "Not assignable");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Not assignable", e.getMessage());
        }
    }
}
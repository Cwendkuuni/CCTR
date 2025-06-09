package org.apache.commons.lang3;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class ValidateTest {

    @Test
    public void testIsTrueWithLong() {
        Validate.isTrue(true, "Should not throw", 1L);
        try {
            Validate.isTrue(false, "Error: %d", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: 1", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithDouble() {
        Validate.isTrue(true, "Should not throw", 1.0);
        try {
            Validate.isTrue(false, "Error: %f", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: 1.000000", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithObject() {
        Validate.isTrue(true, "Should not throw", "value");
        try {
            Validate.isTrue(false, "Error: %s", "value");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: value", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithoutMessage() {
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
        assertNotNull(Validate.notNull("test"));
        try {
            Validate.notNull(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("The validated object is null", e.getMessage());
        }
    }

    @Test
    public void testNotNullWithMessage() {
        assertNotNull(Validate.notNull("test", "Should not throw"));
        try {
            Validate.notNull(null, "Error: %s", "null");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Error: null", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyArray() {
        String[] array = {"test"};
        assertArrayEquals(array, Validate.notEmpty(array));
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
        assertArrayEquals(array, Validate.notEmpty(array, "Should not throw"));
        try {
            Validate.notEmpty(new String[0], "Error: %s", "empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCollection() {
        List<String> list = Arrays.asList("test");
        assertEquals(list, Validate.notEmpty(list));
        try {
            Validate.notEmpty(new ArrayList<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated collection is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCollectionWithMessage() {
        List<String> list = Arrays.asList("test");
        assertEquals(list, Validate.notEmpty(list, "Should not throw"));
        try {
            Validate.notEmpty(new ArrayList<>(), "Error: %s", "empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertEquals(map, Validate.notEmpty(map));
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
        assertEquals(map, Validate.notEmpty(map, "Should not throw"));
        try {
            Validate.notEmpty(new HashMap<>(), "Error: %s", "empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCharSequence() {
        String str = "test";
        assertEquals(str, Validate.notEmpty(str));
        try {
            Validate.notEmpty("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmptyCharSequenceWithMessage() {
        String str = "test";
        assertEquals(str, Validate.notEmpty(str, "Should not throw"));
        try {
            Validate.notEmpty("", "Error: %s", "empty");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: empty", e.getMessage());
        }
    }

    @Test
    public void testNotBlank() {
        String str = "test";
        assertEquals(str, Validate.notBlank(str));
        try {
            Validate.notBlank(" ");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    @Test
    public void testNotBlankWithMessage() {
        String str = "test";
        assertEquals(str, Validate.notBlank(str, "Should not throw"));
        try {
            Validate.notBlank(" ", "Error: %s", "blank");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: blank", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsArray() {
        String[] array = {"test"};
        assertArrayEquals(array, Validate.noNullElements(array));
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
        assertArrayEquals(array, Validate.noNullElements(array, "Should not throw"));
        try {
            Validate.noNullElements(new String[]{null}, "Error: %s", "null");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: null", e.getMessage());
        }
    }

    @Test
    public void testNoNullElementsIterable() {
        List<String> list = Arrays.asList("test");
        assertEquals(list, Validate.noNullElements(list));
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
        assertEquals(list, Validate.noNullElements(list, "Should not throw"));
        try {
            Validate.noNullElements(Arrays.asList((String) null), "Error: %s", "null");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: null", e.getMessage());
        }
    }

    @Test
    public void testValidIndexArray() {
        String[] array = {"test"};
        assertArrayEquals(array, Validate.validIndex(array, 0));
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
        assertArrayEquals(array, Validate.validIndex(array, 0, "Should not throw"));
        try {
            Validate.validIndex(array, 1, "Error: %s", "index");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Error: index", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCollection() {
        List<String> list = Arrays.asList("test");
        assertEquals(list, Validate.validIndex(list, 0));
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
        assertEquals(list, Validate.validIndex(list, 0, "Should not throw"));
        try {
            Validate.validIndex(list, 1, "Error: %s", "index");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Error: index", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCharSequence() {
        String str = "test";
        assertEquals(str, Validate.validIndex(str, 0));
        try {
            Validate.validIndex(str, 4);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated character sequence index is invalid: 4", e.getMessage());
        }
    }

    @Test
    public void testValidIndexCharSequenceWithMessage() {
        String str = "test";
        assertEquals(str, Validate.validIndex(str, 0, "Should not throw"));
        try {
            Validate.validIndex(str, 4, "Error: %s", "index");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Error: index", e.getMessage());
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
        Validate.validState(true, "Should not throw");
        try {
            Validate.validState(false, "Error: %s", "state");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Error: state", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern() {
        Validate.matchesPattern("abc", "a.c");
        try {
            Validate.matchesPattern("abc", "d.e");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The string abc does not match the pattern d.e", e.getMessage());
        }
    }

    @Test
    public void testMatchesPatternWithMessage() {
        Validate.matchesPattern("abc", "a.c", "Should not throw");
        try {
            Validate.matchesPattern("abc", "d.e", "Error: %s", "pattern");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: pattern", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenComparable() {
        Validate.inclusiveBetween(1, 3, 2);
        try {
            Validate.inclusiveBetween(1, 3, 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenComparableWithMessage() {
        Validate.inclusiveBetween(1, 3, 2, "Should not throw");
        try {
            Validate.inclusiveBetween(1, 3, 4, "Error: %s", "range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
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
        Validate.inclusiveBetween(1L, 3L, 2L, "Should not throw");
        try {
            Validate.inclusiveBetween(1L, 3L, 4L, "Error: range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
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
        Validate.inclusiveBetween(1.0, 3.0, 2.0, "Should not throw");
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0, "Error: range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenComparable() {
        Validate.exclusiveBetween(1, 3, 2);
        try {
            Validate.exclusiveBetween(1, 3, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenComparableWithMessage() {
        Validate.exclusiveBetween(1, 3, 2, "Should not throw");
        try {
            Validate.exclusiveBetween(1, 3, 1, "Error: %s", "range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Validate.exclusiveBetween(1L, 3L, 2L);
        try {
            Validate.exclusiveBetween(1L, 3L, 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLongWithMessage() {
        Validate.exclusiveBetween(1L, 3L, 2L, "Should not throw");
        try {
            Validate.exclusiveBetween(1L, 3L, 1L, "Error: range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0);
        try {
            Validate.exclusiveBetween(1.0, 3.0, 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1.0 is not in the specified exclusive range of 1.0 to 3.0", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDoubleWithMessage() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0, "Should not throw");
        try {
            Validate.exclusiveBetween(1.0, 3.0, 1.0, "Error: range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: range", e.getMessage());
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
        Validate.isInstanceOf(String.class, "test", "Should not throw");
        try {
            Validate.isInstanceOf(Integer.class, "test", "Error: %s", "type");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: type", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom() {
        Validate.isAssignableFrom(Object.class, String.class);
        try {
            Validate.isAssignableFrom(String.class, Object.class);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot assign a java.lang.Object to a java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFromWithMessage() {
        Validate.isAssignableFrom(Object.class, String.class, "Should not throw");
        try {
            Validate.isAssignableFrom(String.class, Object.class, "Error: %s", "assignment");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error: assignment", e.getMessage());
        }
    }
}
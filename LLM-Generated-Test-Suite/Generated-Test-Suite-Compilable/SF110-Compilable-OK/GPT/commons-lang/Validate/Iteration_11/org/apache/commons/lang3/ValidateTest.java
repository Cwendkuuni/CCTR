package org.apache.commons.lang3;

import org.junit.Test;
import org.apache.commons.lang3.Validate;

import java.util.*;

import static org.junit.Assert.*;

public class ValidateTest {

    @Test
    public void testIsTrueWithMessageAndLong() {
        Validate.isTrue(true, "This should not fail", 1L);
        try {
            Validate.isTrue(false, "This should fail with value %d", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with value 1", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithMessageAndDouble() {
        Validate.isTrue(true, "This should not fail", 1.0);
        try {
            Validate.isTrue(false, "This should fail with value %f", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with value 1.000000", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithMessageAndValues() {
        Validate.isTrue(true, "This should not fail", 1, 2, 3);
        try {
            Validate.isTrue(false, "This should fail with values %d, %d, %d", 1, 2, 3);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with values 1, 2, 3", e.getMessage());
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
        assertNotNull(Validate.notNull("test", "This should not fail"));
        try {
            Validate.notNull(null, "This should fail");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertArrayEquals(array, Validate.notEmpty(array, "This should not fail"));
        try {
            Validate.notEmpty(new String[0], "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertEquals(list, Validate.notEmpty(list, "This should not fail"));
        try {
            Validate.notEmpty(new ArrayList<>(), "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertEquals(map, Validate.notEmpty(map, "This should not fail"));
        try {
            Validate.notEmpty(new HashMap<>(), "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertEquals(str, Validate.notEmpty(str, "This should not fail"));
        try {
            Validate.notEmpty("", "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertEquals(str, Validate.notBlank(str, "This should not fail"));
        try {
            Validate.notBlank(" ", "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        assertArrayEquals(array, Validate.noNullElements(array, "This should not fail"));
        try {
            Validate.noNullElements(new String[]{null}, "This should fail at index: %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail at index: 0", e.getMessage());
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
        assertEquals(list, Validate.noNullElements(list, "This should not fail"));
        try {
            Validate.noNullElements(Arrays.asList((String) null), "This should fail at index: %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail at index: 0", e.getMessage());
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
        assertArrayEquals(array, Validate.validIndex(array, 0, "This should not fail"));
        try {
            Validate.validIndex(array, 1, "This should fail at index: %d", 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("This should fail at index: 1", e.getMessage());
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
        assertEquals(list, Validate.validIndex(list, 0, "This should not fail"));
        try {
            Validate.validIndex(list, 1, "This should fail at index: %d", 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("This should fail at index: 1", e.getMessage());
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
        assertEquals(str, Validate.validIndex(str, 0, "This should not fail"));
        try {
            Validate.validIndex(str, 4, "This should fail at index: %d", 4);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("This should fail at index: 4", e.getMessage());
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
        Validate.validState(true, "This should not fail");
        try {
            Validate.validState(false, "This should fail");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("This should fail", e.getMessage());
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
        Validate.matchesPattern("abc", "a.c", "This should not fail");
        try {
            Validate.matchesPattern("abc", "d.e", "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenComparable() {
        Validate.inclusiveBetween(1, 10, 5);
        try {
            Validate.inclusiveBetween(1, 10, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 0 is not in the specified inclusive range of 1 to 10", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenComparableWithMessage() {
        Validate.inclusiveBetween(1, 10, 5, "This should not fail");
        try {
            Validate.inclusiveBetween(1, 10, 0, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLong() {
        Validate.inclusiveBetween(1L, 10L, 5L);
        try {
            Validate.inclusiveBetween(1L, 10L, 0L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 0 is not in the specified inclusive range of 1 to 10", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenLongWithMessage() {
        Validate.inclusiveBetween(1L, 10L, 5L, "This should not fail");
        try {
            Validate.inclusiveBetween(1L, 10L, 0L, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDouble() {
        Validate.inclusiveBetween(1.0, 10.0, 5.0);
        try {
            Validate.inclusiveBetween(1.0, 10.0, 0.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 0.0 is not in the specified inclusive range of 1.0 to 10.0", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetweenDoubleWithMessage() {
        Validate.inclusiveBetween(1.0, 10.0, 5.0, "This should not fail");
        try {
            Validate.inclusiveBetween(1.0, 10.0, 0.0, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenComparable() {
        Validate.exclusiveBetween(1, 10, 5);
        try {
            Validate.exclusiveBetween(1, 10, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1 is not in the specified exclusive range of 1 to 10", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenComparableWithMessage() {
        Validate.exclusiveBetween(1, 10, 5, "This should not fail");
        try {
            Validate.exclusiveBetween(1, 10, 1, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLong() {
        Validate.exclusiveBetween(1L, 10L, 5L);
        try {
            Validate.exclusiveBetween(1L, 10L, 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1 is not in the specified exclusive range of 1 to 10", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenLongWithMessage() {
        Validate.exclusiveBetween(1L, 10L, 5L, "This should not fail");
        try {
            Validate.exclusiveBetween(1L, 10L, 1L, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDouble() {
        Validate.exclusiveBetween(1.0, 10.0, 5.0);
        try {
            Validate.exclusiveBetween(1.0, 10.0, 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 1.0 is not in the specified exclusive range of 1.0 to 10.0", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetweenDoubleWithMessage() {
        Validate.exclusiveBetween(1.0, 10.0, 5.0, "This should not fail");
        try {
            Validate.exclusiveBetween(1.0, 10.0, 1.0, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        Validate.isInstanceOf(String.class, "test", "This should not fail");
        try {
            Validate.isInstanceOf(Integer.class, "test", "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
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
        Validate.isAssignableFrom(Object.class, String.class, "This should not fail");
        try {
            Validate.isAssignableFrom(String.class, Object.class, "This should fail");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail", e.getMessage());
        }
    }
}
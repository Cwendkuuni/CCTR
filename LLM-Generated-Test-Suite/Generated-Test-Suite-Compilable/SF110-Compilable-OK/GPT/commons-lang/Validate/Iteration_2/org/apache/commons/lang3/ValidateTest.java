package org.apache.commons.lang3;

import org.apache.commons.lang3.Validate;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ValidateTest {

    @Test
    public void testIsTrueWithLong() {
        Validate.isTrue(true, "This should not fail", 1L);
        try {
            Validate.isTrue(false, "This should fail with value %s", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with value 1", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithDouble() {
        Validate.isTrue(true, "This should not fail", 1.0);
        try {
            Validate.isTrue(false, "This should fail with value %s", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with value 1.0", e.getMessage());
        }
    }

    @Test
    public void testIsTrueWithObject() {
        Validate.isTrue(true, "This should not fail", new Object());
        try {
            Validate.isTrue(false, "This should fail with value %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("This should fail with value test", e.getMessage());
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
    public void testNotEmptyMap() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        assertEquals(map, Validate.notEmpty(map));
        try {
            Validate.notEmpty(new HashMap<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated map is empty", e.getMessage());
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
    public void testInclusiveBetween() {
        Validate.inclusiveBetween(1, 10, 5);
        try {
            Validate.inclusiveBetween(1, 10, 11);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 11 is not in the specified inclusive range of 1 to 10", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween() {
        Validate.exclusiveBetween(1, 10, 5);
        try {
            Validate.exclusiveBetween(1, 10, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 10 is not in the specified exclusive range of 1 to 10", e.getMessage());
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
    public void testIsAssignableFrom() {
        Validate.isAssignableFrom(CharSequence.class, String.class);
        try {
            Validate.isAssignableFrom(String.class, Integer.class);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot assign a java.lang.Integer to a java.lang.String", e.getMessage());
        }
    }
}
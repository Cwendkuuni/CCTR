package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ValidateTest {

    @Test
    public void testIsTrue_boolean_String_long() {
        Validate.isTrue(true, "Test message %d", 1L);
        try {
            Validate.isTrue(false, "Test message %d", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message 1", e.getMessage());
        }
    }

    @Test
    public void testIsTrue_boolean_String_double() {
        Validate.isTrue(true, "Test message %f", 1.0);
        try {
            Validate.isTrue(false, "Test message %f", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message 1.000000", e.getMessage());
        }
    }

    @Test
    public void testIsTrue_boolean_String_Object() {
        Validate.isTrue(true, "Test message %s", "test");
        try {
            Validate.isTrue(false, "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testIsTrue_boolean() {
        Validate.isTrue(true);
        try {
            Validate.isTrue(false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated expression is false", e.getMessage());
        }
    }

    @Test
    public void testNotNull_T() {
        assertNotNull(Validate.notNull("test"));
        try {
            Validate.notNull(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("The validated object is null", e.getMessage());
        }
    }

    @Test
    public void testNotNull_T_String_Object() {
        assertNotNull(Validate.notNull("test", "Test message %s", "test"));
        try {
            Validate.notNull(null, "Test message %s", "test");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TArray_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.notEmpty(array, "Test message %s", "test"));
        try {
            Validate.notEmpty(new String[0], "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TArray() {
        String[] array = {"test"};
        assertNotNull(Validate.notEmpty(array));
        try {
            Validate.notEmpty(new String[0]);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated array is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TCollection_String_Object() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.notEmpty(list, "Test message %s", "test"));
        try {
            Validate.notEmpty(new ArrayList<>(), "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TCollection() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.notEmpty(list));
        try {
            Validate.notEmpty(new ArrayList<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated collection is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TMap_String_Object() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        assertNotNull(Validate.notEmpty(map, "Test message %s", "test"));
        try {
            Validate.notEmpty(new HashMap<>(), "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TMap() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        assertNotNull(Validate.notEmpty(map));
        try {
            Validate.notEmpty(new HashMap<>());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated map is empty", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TCharSequence_String_Object() {
        assertNotNull(Validate.notEmpty("test", "Test message %s", "test"));
        try {
            Validate.notEmpty("", "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TCharSequence() {
        assertNotNull(Validate.notEmpty("test"));
        try {
            Validate.notEmpty("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is empty", e.getMessage());
        }
    }

    @Test
    public void testNotBlank_TCharSequence_String_Object() {
        assertNotNull(Validate.notBlank("test", "Test message %s", "test"));
        try {
            Validate.notBlank(" ", "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testNotBlank_TCharSequence() {
        assertNotNull(Validate.notBlank("test"));
        try {
            Validate.notBlank(" ");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated character sequence is blank", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TArray_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.noNullElements(array, "Test message %d", 0));
        try {
            Validate.noNullElements(new String[]{null}, "Test message %d", 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TArray() {
        String[] array = {"test"};
        assertNotNull(Validate.noNullElements(array));
        try {
            Validate.noNullElements(new String[]{null});
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated array contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TIterable_String_Object() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.noNullElements(list, "Test message %d", 0));
        try {
            Validate.noNullElements(Collections.singletonList(null), "Test message %d", 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TIterable() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.noNullElements(list));
        try {
            Validate.noNullElements(Collections.singletonList(null));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The validated collection contains null element at index: 0", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TArray_int_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.validIndex(array, 0, "Test message %d", 0));
        try {
            Validate.validIndex(array, 1, "Test message %d", 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Test message 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TArray_int() {
        String[] array = {"test"};
        assertNotNull(Validate.validIndex(array, 0));
        try {
            Validate.validIndex(array, 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated array index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCollection_int_String_Object() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.validIndex(list, 0, "Test message %d", 0));
        try {
            Validate.validIndex(list, 1, "Test message %d", 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Test message 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCollection_int() {
        List<String> list = Collections.singletonList("test");
        assertNotNull(Validate.validIndex(list, 0));
        try {
            Validate.validIndex(list, 1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated collection index is invalid: 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCharSequence_int_String_Object() {
        assertNotNull(Validate.validIndex("test", 0, "Test message %d", 0));
        try {
            Validate.validIndex("test", 4, "Test message %d", 4);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Test message 4", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCharSequence_int() {
        assertNotNull(Validate.validIndex("test", 0));
        try {
            Validate.validIndex("test", 4);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("The validated character sequence index is invalid: 4", e.getMessage());
        }
    }

    @Test
    public void testValidState_boolean() {
        Validate.validState(true);
        try {
            Validate.validState(false);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("The validated state is false", e.getMessage());
        }
    }

    @Test
    public void testValidState_boolean_String_Object() {
        Validate.validState(true, "Test message %s", "test");
        try {
            Validate.validState(false, "Test message %s", "test");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_CharSequence_String() {
        Validate.matchesPattern("test", "test");
        try {
            Validate.matchesPattern("test", "wrong");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The string test does not match the pattern wrong", e.getMessage());
        }
    }

    @Test
    public void testMatchesPattern_CharSequence_String_String_Object() {
        Validate.matchesPattern("test", "test", "Test message %s", "test");
        try {
            Validate.matchesPattern("test", "wrong", "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable() {
        Validate.inclusiveBetween(1, 3, 2);
        try {
            Validate.inclusiveBetween(1, 3, 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable_String_Object() {
        Validate.inclusiveBetween(1, 3, 2, "Test message %s", "test");
        try {
            Validate.inclusiveBetween(1, 3, 4, "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_long_long_long() {
        Validate.inclusiveBetween(1L, 3L, 2L);
        try {
            Validate.inclusiveBetween(1L, 3L, 4L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_long_long_long_String() {
        Validate.inclusiveBetween(1L, 3L, 2L, "Test message");
        try {
            Validate.inclusiveBetween(1L, 3L, 4L, "Test message");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_double_double_double() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0);
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 4.0 is not in the specified inclusive range of 1.0 to 3.0", e.getMessage());
        }
    }

    @Test
    public void testInclusiveBetween_double_double_double_String() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0, "Test message");
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0, "Test message");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable() {
        Validate.exclusiveBetween(1, 3, 2);
        try {
            Validate.exclusiveBetween(1, 3, 3);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable_String_Object() {
        Validate.exclusiveBetween(1, 3, 2, "Test message %s", "test");
        try {
            Validate.exclusiveBetween(1, 3, 3, "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_long_long_long() {
        Validate.exclusiveBetween(1L, 3L, 2L);
        try {
            Validate.exclusiveBetween(1L, 3L, 3L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_long_long_long_String() {
        Validate.exclusiveBetween(1L, 3L, 2L, "Test message");
        try {
            Validate.exclusiveBetween(1L, 3L, 3L, "Test message");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_double_double_double() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0);
        try {
            Validate.exclusiveBetween(1.0, 3.0, 3.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("The value 3.0 is not in the specified exclusive range of 1.0 to 3.0", e.getMessage());
        }
    }

    @Test
    public void testExclusiveBetween_double_double_double_String() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0, "Test message");
        try {
            Validate.exclusiveBetween(1.0, 3.0, 3.0, "Test message");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_Class_Object() {
        Validate.isInstanceOf(String.class, "test");
        try {
            Validate.isInstanceOf(Integer.class, "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Expected type: java.lang.Integer, actual: java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_Class_Object_String_Object() {
        Validate.isInstanceOf(String.class, "test", "Test message %s", "test");
        try {
            Validate.isInstanceOf(Integer.class, "test", "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom_Class_Class() {
        Validate.isAssignableFrom(Object.class, String.class);
        try {
            Validate.isAssignableFrom(String.class, Integer.class);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot assign a java.lang.Integer to a java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom_Class_Class_String_Object() {
        Validate.isAssignableFrom(Object.class, String.class, "Test message %s", "test");
        try {
            Validate.isAssignableFrom(String.class, Integer.class, "Test message %s", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Test message test", e.getMessage());
        }
    }
}
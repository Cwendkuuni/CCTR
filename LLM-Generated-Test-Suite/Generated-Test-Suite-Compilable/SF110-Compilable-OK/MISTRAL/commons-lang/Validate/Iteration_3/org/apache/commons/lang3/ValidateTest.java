package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class ValidateTest {

    @Test
    public void testIsTrue_boolean_String_long() {
        Validate.isTrue(true, "Value %d is not true", 1L);
        try {
            Validate.isTrue(false, "Value %d is not true", 1L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 1 is not true", e.getMessage());
        }
    }

    @Test
    public void testIsTrue_boolean_String_double() {
        Validate.isTrue(true, "Value %f is not true", 1.0);
        try {
            Validate.isTrue(false, "Value %f is not true", 1.0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 1.000000 is not true", e.getMessage());
        }
    }

    @Test
    public void testIsTrue_boolean_String_ObjectArray() {
        Validate.isTrue(true, "Value %s is not true", "test");
        try {
            Validate.isTrue(false, "Value %s is not true", "test");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value test is not true", e.getMessage());
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
    public void testNotNull_T_String_ObjectArray() {
        String obj = "test";
        assertSame(obj, Validate.notNull(obj, "Object %s is null", "test"));
        try {
            Validate.notNull(null, "Object %s is null", "test");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Object test is null", e.getMessage());
        }
    }

    @Test
    public void testNotEmpty_TArray_String_ObjectArray() {
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
    public void testNotEmpty_TArray() {
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
    public void testNotEmpty_TCollection_String_ObjectArray() {
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
    public void testNotEmpty_TCollection() {
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
    public void testNotEmpty_TMap_String_ObjectArray() {
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
    public void testNotEmpty_TMap() {
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
    public void testNotEmpty_TCharSequence_String_ObjectArray() {
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
    public void testNotEmpty_TCharSequence() {
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
    public void testNotBlank_TCharSequence_String_ObjectArray() {
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
    public void testNotBlank_TCharSequence() {
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
    public void testNoNullElements_TArray_String_ObjectArray() {
        String[] array = {"test"};
        assertSame(array, Validate.noNullElements(array, "Array contains null at index %d"));
        try {
            Validate.noNullElements(new String[]{null}, "Array contains null at index %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Array contains null at index 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TArray() {
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
    public void testNoNullElements_TIterable_String_ObjectArray() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.noNullElements(list, "Collection contains null at index %d"));
        try {
            Validate.noNullElements(Arrays.asList((String) null), "Collection contains null at index %d");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Collection contains null at index 0", e.getMessage());
        }
    }

    @Test
    public void testNoNullElements_TIterable() {
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
    public void testValidIndex_TArray_int_String_ObjectArray() {
        String[] array = {"test"};
        assertSame(array, Validate.validIndex(array, 0, "Invalid index %d"));
        try {
            Validate.validIndex(array, 1, "Invalid index %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Invalid index 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TArray_int() {
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
    public void testValidIndex_TCollection_int_String_ObjectArray() {
        List<String> list = Arrays.asList("test");
        assertSame(list, Validate.validIndex(list, 0, "Invalid index %d"));
        try {
            Validate.validIndex(list, 1, "Invalid index %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Invalid index 1", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCollection_int() {
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
    public void testValidIndex_TCharSequence_int_String_ObjectArray() {
        String chars = "test";
        assertSame(chars, Validate.validIndex(chars, 0, "Invalid index %d"));
        try {
            Validate.validIndex(chars, 4, "Invalid index %d");
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Invalid index 4", e.getMessage());
        }
    }

    @Test
    public void testValidIndex_TCharSequence_int() {
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
    public void testValidState_boolean_String_ObjectArray() {
        Validate.validState(true, "State is false");
        try {
            Validate.validState(false, "State is false");
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("State is false", e.getMessage());
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
    public void testMatchesPattern_CharSequence_String_String_ObjectArray() {
        Validate.matchesPattern("test", "test", "Pattern does not match");
        try {
            Validate.matchesPattern("test", "wrong", "Pattern does not match");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Pattern does not match", e.getMessage());
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
    public void testInclusiveBetween_T_T_Comparable_String_ObjectArray() {
        Validate.inclusiveBetween(1, 3, 2, "Value %s is not in range", 2);
        try {
            Validate.inclusiveBetween(1, 3, 4, "Value %s is not in range", 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 4 is not in range", e.getMessage());
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
        Validate.inclusiveBetween(1L, 3L, 2L, "Value %s is not in range");
        try {
            Validate.inclusiveBetween(1L, 3L, 4L, "Value %s is not in range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 4 is not in range", e.getMessage());
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
        Validate.inclusiveBetween(1.0, 3.0, 2.0, "Value %s is not in range");
        try {
            Validate.inclusiveBetween(1.0, 3.0, 4.0, "Value %s is not in range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 4.0 is not in range", e.getMessage());
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
    public void testExclusiveBetween_T_T_Comparable_String_ObjectArray() {
        Validate.exclusiveBetween(1, 3, 2, "Value %s is not in range", 2);
        try {
            Validate.exclusiveBetween(1, 3, 3, "Value %s is not in range", 3);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 3 is not in range", e.getMessage());
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
        Validate.exclusiveBetween(1L, 3L, 2L, "Value %s is not in range");
        try {
            Validate.exclusiveBetween(1L, 3L, 3L, "Value %s is not in range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 3 is not in range", e.getMessage());
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
        Validate.exclusiveBetween(1.0, 3.0, 2.0, "Value %s is not in range");
        try {
            Validate.exclusiveBetween(1.0, 3.0, 3.0, "Value %s is not in range");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Value 3.0 is not in range", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_Class_Object() {
        Validate.isInstanceOf(String.class, "test");
        try {
            Validate.isInstanceOf(String.class, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Expected type: java.lang.String, actual: java.lang.Integer", e.getMessage());
        }
    }

    @Test
    public void testIsInstanceOf_Class_Object_String_ObjectArray() {
        Validate.isInstanceOf(String.class, "test", "Object is not a String");
        try {
            Validate.isInstanceOf(String.class, 1, "Object is not a String");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Object is not a String", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom_Class_Class() {
        Validate.isAssignableFrom(Object.class, String.class);
        try {
            Validate.isAssignableFrom(String.class, Object.class);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot assign a java.lang.Object to a java.lang.String", e.getMessage());
        }
    }

    @Test
    public void testIsAssignableFrom_Class_Class_String_ObjectArray() {
        Validate.isAssignableFrom(Object.class, String.class, "Class is not assignable");
        try {
            Validate.isAssignableFrom(String.class, Object.class, "Class is not assignable");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Class is not assignable", e.getMessage());
        }
    }
}
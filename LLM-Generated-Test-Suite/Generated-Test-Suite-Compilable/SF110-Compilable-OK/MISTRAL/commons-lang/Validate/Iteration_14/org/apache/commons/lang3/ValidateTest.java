package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class ValidateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        // Any setup code if needed
    }

    @Test
    public void testIsTrue_boolean_long() {
        Validate.isTrue(true, "The value %d is not true", 1L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 0 is not true");
        Validate.isTrue(false, "The value %d is not true", 0L);
    }

    @Test
    public void testIsTrue_boolean_double() {
        Validate.isTrue(true, "The value %f is not true", 1.0);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 0.000000 is not true");
        Validate.isTrue(false, "The value %f is not true", 0.0);
    }

    @Test
    public void testIsTrue_boolean_Object() {
        Validate.isTrue(true, "The value %s is not true", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value false is not true");
        Validate.isTrue(false, "The value %s is not true", "false");
    }

    @Test
    public void testIsTrue_boolean() {
        Validate.isTrue(true);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated expression is false");
        Validate.isTrue(false);
    }

    @Test
    public void testNotNull_T() {
        String obj = "test";
        assertSame(obj, Validate.notNull(obj));
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("The validated object is null");
        Validate.notNull(null);
    }

    @Test
    public void testNotNull_T_String_Object() {
        String obj = "test";
        assertSame(obj, Validate.notNull(obj, "Object is null"));
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Object is null");
        Validate.notNull(null, "Object is null");
    }

    @Test
    public void testNotEmpty_T_array() {
        String[] array = {"test"};
        assertSame(array, Validate.notEmpty(array));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated array is empty");
        Validate.notEmpty(new String[0]);
    }

    @Test
    public void testNotEmpty_T_array_String_Object() {
        String[] array = {"test"};
        assertSame(array, Validate.notEmpty(array, "Array is empty"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Array is empty");
        Validate.notEmpty(new String[0], "Array is empty");
    }

    @Test
    public void testNotEmpty_T_collection() {
        List<String> collection = new ArrayList<>(Arrays.asList("test"));
        assertSame(collection, Validate.notEmpty(collection));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated collection is empty");
        Validate.notEmpty(new ArrayList<>());
    }

    @Test
    public void testNotEmpty_T_collection_String_Object() {
        List<String> collection = new ArrayList<>(Arrays.asList("test"));
        assertSame(collection, Validate.notEmpty(collection, "Collection is empty"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Collection is empty");
        Validate.notEmpty(new ArrayList<>(), "Collection is empty");
    }

    @Test
    public void testNotEmpty_T_map() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertSame(map, Validate.notEmpty(map));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated map is empty");
        Validate.notEmpty(new HashMap<>());
    }

    @Test
    public void testNotEmpty_T_map_String_Object() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertSame(map, Validate.notEmpty(map, "Map is empty"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Map is empty");
        Validate.notEmpty(new HashMap<>(), "Map is empty");
    }

    @Test
    public void testNotEmpty_T_charSequence() {
        String chars = "test";
        assertSame(chars, Validate.notEmpty(chars));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated character sequence is empty");
        Validate.notEmpty("");
    }

    @Test
    public void testNotEmpty_T_charSequence_String_Object() {
        String chars = "test";
        assertSame(chars, Validate.notEmpty(chars, "CharSequence is empty"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CharSequence is empty");
        Validate.notEmpty("", "CharSequence is empty");
    }

    @Test
    public void testNotBlank_T_charSequence() {
        String chars = "test";
        assertSame(chars, Validate.notBlank(chars));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated character sequence is blank");
        Validate.notBlank(" ");
    }

    @Test
    public void testNotBlank_T_charSequence_String_Object() {
        String chars = "test";
        assertSame(chars, Validate.notBlank(chars, "CharSequence is blank"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CharSequence is blank");
        Validate.notBlank(" ", "CharSequence is blank");
    }

    @Test
    public void testNoNullElements_T_array() {
        String[] array = {"test"};
        assertSame(array, Validate.noNullElements(array));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated array contains null element at index: 0");
        Validate.noNullElements(new String[]{null});
    }

    @Test
    public void testNoNullElements_T_array_String_Object() {
        String[] array = {"test"};
        assertSame(array, Validate.noNullElements(array, "Array contains null"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Array contains null");
        Validate.noNullElements(new String[]{null}, "Array contains null");
    }

    @Test
    public void testNoNullElements_T_iterable() {
        List<String> list = new ArrayList<>(Arrays.asList("test"));
        assertSame(list, Validate.noNullElements(list));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated collection contains null element at index: 0");
        Validate.noNullElements(new ArrayList<>(Arrays.asList((String) null)));
    }

    @Test
    public void testNoNullElements_T_iterable_String_Object() {
        List<String> list = new ArrayList<>(Arrays.asList("test"));
        assertSame(list, Validate.noNullElements(list, "Collection contains null"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Collection contains null");
        Validate.noNullElements(new ArrayList<>(Arrays.asList((String) null)), "Collection contains null");
    }

    @Test
    public void testValidIndex_T_array() {
        String[] array = {"test"};
        assertSame(array, Validate.validIndex(array, 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated array index is invalid: 1");
        Validate.validIndex(array, 1);
    }

    @Test
    public void testValidIndex_T_array_String_Object() {
        String[] array = {"test"};
        assertSame(array, Validate.validIndex(array, 0, "Invalid index"));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Invalid index");
        Validate.validIndex(array, 1, "Invalid index");
    }

    @Test
    public void testValidIndex_T_collection() {
        List<String> list = new ArrayList<>(Arrays.asList("test"));
        assertSame(list, Validate.validIndex(list, 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated collection index is invalid: 1");
        Validate.validIndex(list, 1);
    }

    @Test
    public void testValidIndex_T_collection_String_Object() {
        List<String> list = new ArrayList<>(Arrays.asList("test"));
        assertSame(list, Validate.validIndex(list, 0, "Invalid index"));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Invalid index");
        Validate.validIndex(list, 1, "Invalid index");
    }

    @Test
    public void testValidIndex_T_charSequence() {
        String chars = "test";
        assertSame(chars, Validate.validIndex(chars, 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated character sequence index is invalid: 4");
        Validate.validIndex(chars, 4);
    }

    @Test
    public void testValidIndex_T_charSequence_String_Object() {
        String chars = "test";
        assertSame(chars, Validate.validIndex(chars, 0, "Invalid index"));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Invalid index");
        Validate.validIndex(chars, 4, "Invalid index");
    }

    @Test
    public void testValidState_boolean() {
        Validate.validState(true);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("The validated state is false");
        Validate.validState(false);
    }

    @Test
    public void testValidState_boolean_String_Object() {
        Validate.validState(true, "State is false");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("State is false");
        Validate.validState(false, "State is false");
    }

    @Test
    public void testMatchesPattern_CharSequence_String() {
        Validate.matchesPattern("test", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The string test does not match the pattern different");
        Validate.matchesPattern("test", "different");
    }

    @Test
    public void testMatchesPattern_CharSequence_String_String_Object() {
        Validate.matchesPattern("test", "test", "Pattern does not match");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Pattern does not match");
        Validate.matchesPattern("test", "different", "Pattern does not match");
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable() {
        Validate.inclusiveBetween(1, 10, 5);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 11 is not in the specified inclusive range of 1 to 10");
        Validate.inclusiveBetween(1, 10, 11);
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable_String_Object() {
        Validate.inclusiveBetween(1, 10, 5, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.inclusiveBetween(1, 10, 11, "Value not in range");
    }

    @Test
    public void testInclusiveBetween_long_long_long() {
        Validate.inclusiveBetween(1L, 10L, 5L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 11 is not in the specified inclusive range of 1 to 10");
        Validate.inclusiveBetween(1L, 10L, 11L);
    }

    @Test
    public void testInclusiveBetween_long_long_long_String() {
        Validate.inclusiveBetween(1L, 10L, 5L, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.inclusiveBetween(1L, 10L, 11L, "Value not in range");
    }

    @Test
    public void testInclusiveBetween_double_double_double() {
        Validate.inclusiveBetween(1.0, 10.0, 5.0);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 11.0 is not in the specified inclusive range of 1.0 to 10.0");
        Validate.inclusiveBetween(1.0, 10.0, 11.0);
    }

    @Test
    public void testInclusiveBetween_double_double_double_String() {
        Validate.inclusiveBetween(1.0, 10.0, 5.0, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.inclusiveBetween(1.0, 10.0, 11.0, "Value not in range");
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable() {
        Validate.exclusiveBetween(1, 10, 5);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 10 is not in the specified exclusive range of 1 to 10");
        Validate.exclusiveBetween(1, 10, 10);
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable_String_Object() {
        Validate.exclusiveBetween(1, 10, 5, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.exclusiveBetween(1, 10, 10, "Value not in range");
    }

    @Test
    public void testExclusiveBetween_long_long_long() {
        Validate.exclusiveBetween(1L, 10L, 5L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 10 is not in the specified exclusive range of 1 to 10");
        Validate.exclusiveBetween(1L, 10L, 10L);
    }

    @Test
    public void testExclusiveBetween_long_long_long_String() {
        Validate.exclusiveBetween(1L, 10L, 5L, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.exclusiveBetween(1L, 10L, 10L, "Value not in range");
    }

    @Test
    public void testExclusiveBetween_double_double_double() {
        Validate.exclusiveBetween(1.0, 10.0, 5.0);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 10.0 is not in the specified exclusive range of 1.0 to 10.0");
        Validate.exclusiveBetween(1.0, 10.0, 10.0);
    }

    @Test
    public void testExclusiveBetween_double_double_double_String() {
        Validate.exclusiveBetween(1.0, 10.0, 5.0, "Value not in range");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value not in range");
        Validate.exclusiveBetween(1.0, 10.0, 10.0, "Value not in range");
    }

    @Test
    public void testIsInstanceOf_Class_Object() {
        Validate.isInstanceOf(String.class, "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Expected type: java.lang.String, actual: java.lang.Integer");
        Validate.isInstanceOf(String.class, 123);
    }

    @Test
    public void testIsInstanceOf_Class_Object_String_Object() {
        Validate.isInstanceOf(String.class, "test", "Not an instance");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Not an instance");
        Validate.isInstanceOf(String.class, 123, "Not an instance");
    }

    @Test
    public void testIsAssignableFrom_Class_Class() {
        Validate.isAssignableFrom(Object.class, String.class);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot assign a java.lang.Integer to a java.lang.String");
        Validate.isAssignableFrom(String.class, Integer.class);
    }

    @Test
    public void testIsAssignableFrom_Class_Class_String_Object() {
        Validate.isAssignableFrom(Object.class, String.class, "Not assignable");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Not assignable");
        Validate.isAssignableFrom(String.class, Integer.class, "Not assignable");
    }
}
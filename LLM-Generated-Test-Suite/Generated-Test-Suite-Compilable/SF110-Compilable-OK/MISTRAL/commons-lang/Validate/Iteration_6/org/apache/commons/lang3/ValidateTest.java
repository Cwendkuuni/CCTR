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
        // Initialize any common setup if needed
    }

    @Test
    public void testIsTrue_boolean_String_long() {
        Validate.isTrue(true, "Test message %d", 123L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message 123");
        Validate.isTrue(false, "Test message %d", 123L);
    }

    @Test
    public void testIsTrue_boolean_String_double() {
        Validate.isTrue(true, "Test message %f", 123.45);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message 123.450000");
        Validate.isTrue(false, "Test message %f", 123.45);
    }

    @Test
    public void testIsTrue_boolean_String_Object() {
        Validate.isTrue(true, "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.isTrue(false, "Test message %s", "test");
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
        assertNotNull(Validate.notNull("test"));
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("The validated object is null");
        Validate.notNull(null);
    }

    @Test
    public void testNotNull_T_String_Object() {
        assertNotNull(Validate.notNull("test", "Test message %s", "test"));
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Test message test");
        Validate.notNull(null, "Test message %s", "test");
    }

    @Test
    public void testNotEmpty_T_array() {
        String[] array = {"test"};
        assertNotNull(Validate.notEmpty(array));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated array is empty");
        Validate.notEmpty(new String[0]);
    }

    @Test
    public void testNotEmpty_T_array_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.notEmpty(array, "Test message %s", "test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.notEmpty(new String[0], "Test message %s", "test");
    }

    @Test
    public void testNotEmpty_T_Collection() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.notEmpty(list));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated collection is empty");
        Validate.notEmpty(new ArrayList<>());
    }

    @Test
    public void testNotEmpty_T_Collection_String_Object() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.notEmpty(list, "Test message %s", "test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.notEmpty(new ArrayList<>(), "Test message %s", "test");
    }

    @Test
    public void testNotEmpty_T_Map() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertNotNull(Validate.notEmpty(map));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated map is empty");
        Validate.notEmpty(new HashMap<>());
    }

    @Test
    public void testNotEmpty_T_Map_String_Object() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertNotNull(Validate.notEmpty(map, "Test message %s", "test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.notEmpty(new HashMap<>(), "Test message %s", "test");
    }

    @Test
    public void testNotEmpty_T_CharSequence() {
        assertNotNull(Validate.notEmpty("test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated character sequence is empty");
        Validate.notEmpty("");
    }

    @Test
    public void testNotEmpty_T_CharSequence_String_Object() {
        assertNotNull(Validate.notEmpty("test", "Test message %s", "test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.notEmpty("", "Test message %s", "test");
    }

    @Test
    public void testNotBlank_T_CharSequence() {
        assertNotNull(Validate.notBlank("test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated character sequence is blank");
        Validate.notBlank(" ");
    }

    @Test
    public void testNotBlank_T_CharSequence_String_Object() {
        assertNotNull(Validate.notBlank("test", "Test message %s", "test"));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.notBlank(" ", "Test message %s", "test");
    }

    @Test
    public void testNoNullElements_T_array() {
        String[] array = {"test"};
        assertNotNull(Validate.noNullElements(array));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated array contains null element at index: 0");
        Validate.noNullElements(new String[]{null});
    }

    @Test
    public void testNoNullElements_T_array_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.noNullElements(array, "Test message %d", 0));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message 0");
        Validate.noNullElements(new String[]{null}, "Test message %d", 0);
    }

    @Test
    public void testNoNullElements_T_Iterable() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.noNullElements(list));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The validated collection contains null element at index: 0");
        Validate.noNullElements(Arrays.asList((String) null));
    }

    @Test
    public void testNoNullElements_T_Iterable_String_Object() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.noNullElements(list, "Test message %d", 0));
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message 0");
        Validate.noNullElements(Arrays.asList((String) null), "Test message %d", 0);
    }

    @Test
    public void testValidIndex_T_array() {
        String[] array = {"test"};
        assertNotNull(Validate.validIndex(array, 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated array index is invalid: 1");
        Validate.validIndex(array, 1);
    }

    @Test
    public void testValidIndex_T_array_String_Object() {
        String[] array = {"test"};
        assertNotNull(Validate.validIndex(array, 0, "Test message %d", 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Test message 1");
        Validate.validIndex(array, 1, "Test message %d", 1);
    }

    @Test
    public void testValidIndex_T_Collection() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.validIndex(list, 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated collection index is invalid: 1");
        Validate.validIndex(list, 1);
    }

    @Test
    public void testValidIndex_T_Collection_String_Object() {
        List<String> list = new ArrayList<>();
        list.add("test");
        assertNotNull(Validate.validIndex(list, 0, "Test message %d", 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Test message 1");
        Validate.validIndex(list, 1, "Test message %d", 1);
    }

    @Test
    public void testValidIndex_T_CharSequence() {
        assertNotNull(Validate.validIndex("test", 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("The validated character sequence index is invalid: 4");
        Validate.validIndex("test", 4);
    }

    @Test
    public void testValidIndex_T_CharSequence_String_Object() {
        assertNotNull(Validate.validIndex("test", 0, "Test message %d", 0));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Test message 4");
        Validate.validIndex("test", 4, "Test message %d", 4);
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
        Validate.validState(true, "Test message %s", "test");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Test message test");
        Validate.validState(false, "Test message %s", "test");
    }

    @Test
    public void testMatchesPattern_CharSequence_String() {
        Validate.matchesPattern("test", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The string test does not match the pattern test2");
        Validate.matchesPattern("test", "test2");
    }

    @Test
    public void testMatchesPattern_CharSequence_String_String_Object() {
        Validate.matchesPattern("test", "test", "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.matchesPattern("test", "test2", "Test message %s", "test");
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable() {
        Validate.inclusiveBetween(1, 3, 2);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 4 is not in the specified inclusive range of 1 to 3");
        Validate.inclusiveBetween(1, 3, 4);
    }

    @Test
    public void testInclusiveBetween_T_T_Comparable_String_Object() {
        Validate.inclusiveBetween(1, 3, 2, "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.inclusiveBetween(1, 3, 4, "Test message %s", "test");
    }

    @Test
    public void testInclusiveBetween_long_long_long() {
        Validate.inclusiveBetween(1L, 3L, 2L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 4 is not in the specified inclusive range of 1 to 3");
        Validate.inclusiveBetween(1L, 3L, 4L);
    }

    @Test
    public void testInclusiveBetween_long_long_long_String() {
        Validate.inclusiveBetween(1L, 3L, 2L, "Test message");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message");
        Validate.inclusiveBetween(1L, 3L, 4L, "Test message");
    }

    @Test
    public void testInclusiveBetween_double_double_double() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 4.0 is not in the specified inclusive range of 1.0 to 3.0");
        Validate.inclusiveBetween(1.0, 3.0, 4.0);
    }

    @Test
    public void testInclusiveBetween_double_double_double_String() {
        Validate.inclusiveBetween(1.0, 3.0, 2.0, "Test message");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message");
        Validate.inclusiveBetween(1.0, 3.0, 4.0, "Test message");
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable() {
        Validate.exclusiveBetween(1, 3, 2);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 3 is not in the specified exclusive range of 1 to 3");
        Validate.exclusiveBetween(1, 3, 3);
    }

    @Test
    public void testExclusiveBetween_T_T_Comparable_String_Object() {
        Validate.exclusiveBetween(1, 3, 2, "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.exclusiveBetween(1, 3, 3, "Test message %s", "test");
    }

    @Test
    public void testExclusiveBetween_long_long_long() {
        Validate.exclusiveBetween(1L, 3L, 2L);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 3 is not in the specified exclusive range of 1 to 3");
        Validate.exclusiveBetween(1L, 3L, 3L);
    }

    @Test
    public void testExclusiveBetween_long_long_long_String() {
        Validate.exclusiveBetween(1L, 3L, 2L, "Test message");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message");
        Validate.exclusiveBetween(1L, 3L, 3L, "Test message");
    }

    @Test
    public void testExclusiveBetween_double_double_double() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The value 3.0 is not in the specified exclusive range of 1.0 to 3.0");
        Validate.exclusiveBetween(1.0, 3.0, 3.0);
    }

    @Test
    public void testExclusiveBetween_double_double_double_String() {
        Validate.exclusiveBetween(1.0, 3.0, 2.0, "Test message");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message");
        Validate.exclusiveBetween(1.0, 3.0, 3.0, "Test message");
    }

    @Test
    public void testIsInstanceOf_Class_Object() {
        Validate.isInstanceOf(String.class, "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Expected type: java.lang.Integer, actual: java.lang.String");
        Validate.isInstanceOf(Integer.class, "test");
    }

    @Test
    public void testIsInstanceOf_Class_Object_String_Object() {
        Validate.isInstanceOf(String.class, "test", "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.isInstanceOf(Integer.class, "test", "Test message %s", "test");
    }

    @Test
    public void testIsAssignableFrom_Class_Class() {
        Validate.isAssignableFrom(Object.class, String.class);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot assign a java.lang.String to a java.lang.Integer");
        Validate.isAssignableFrom(Integer.class, String.class);
    }

    @Test
    public void testIsAssignableFrom_Class_Class_String_Object() {
        Validate.isAssignableFrom(Object.class, String.class, "Test message %s", "test");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Test message test");
        Validate.isAssignableFrom(Integer.class, String.class, "Test message %s", "test");
    }
}
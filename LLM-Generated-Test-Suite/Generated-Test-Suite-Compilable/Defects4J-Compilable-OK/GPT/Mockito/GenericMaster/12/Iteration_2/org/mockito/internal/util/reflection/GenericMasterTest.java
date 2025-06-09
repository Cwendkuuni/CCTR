package org.mockito.internal.util.reflection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    // Helper classes for testing
    private static class TestClassWithGenericField {
        List<String> stringList;
    }

    private static class TestClassWithNestedGenericField {
        Map<String, List<Integer>> nestedGenericMap;
    }

    private static class TestClassWithNonGenericField {
        String nonGenericField;
    }

    @Test
    public void testGetGenericType_withGenericField() throws NoSuchFieldException {
        Field field = TestClassWithGenericField.class.getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_withNestedGenericField() throws NoSuchFieldException {
        Field field = TestClassWithNestedGenericField.class.getDeclaredField("nestedGenericMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result); // Only the first level generic type is considered
    }

    @Test
    public void testGetGenericType_withNonGenericField() throws NoSuchFieldException {
        Field field = TestClassWithNonGenericField.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_withNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertEquals(Object.class, result);
    }
}
package org.mockito.internal.util.reflection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.GenericMaster;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    // Helper classes for testing
    private static class TestClassWithGenericField {
        List<String> stringList;
        Map<String, Integer> stringIntegerMap;
    }

    private static class TestClassWithNonGenericField {
        String nonGenericField;
    }

    @Test
    public void testGetGenericTypeWithGenericField() throws NoSuchFieldException {
        Field field = TestClassWithGenericField.class.getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithGenericMapField() throws NoSuchFieldException {
        Field field = TestClassWithGenericField.class.getDeclaredField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithNonGenericField() throws NoSuchFieldException {
        Field field = TestClassWithNonGenericField.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericTypeWithNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertEquals(Object.class, result);
    }
}
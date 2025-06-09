package org.mockito.internal.util.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.GenericMaster;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    // Helper classes for testing
    private static class TestClass {
        List<String> stringList;
        Map<String, Integer> stringIntegerMap;
        String nonGenericField;
    }

    @Test
    public void testGetGenericType_withGenericListField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertNotNull(result);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_withGenericMapField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertNotNull(result);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_withNonGenericField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertNotNull(result);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_withNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertNotNull(result);
        assertEquals(Object.class, result);
    }
}
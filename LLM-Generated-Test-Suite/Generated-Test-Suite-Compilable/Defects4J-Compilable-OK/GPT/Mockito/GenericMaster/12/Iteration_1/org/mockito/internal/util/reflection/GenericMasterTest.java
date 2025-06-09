package org.mockito.internal.util.reflection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GenericMasterTest {

    private static class TestClass {
        List<String> stringList;
        Map<String, Integer> stringIntegerMap;
        String nonGenericField;
    }

    private GenericMaster genericMaster = new GenericMaster();

    @Test
    public void testGetGenericTypeWithGenericField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithNonGenericField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericTypeWithMapField() throws NoSuchFieldException {
        Field field = TestClass.class.getDeclaredField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertEquals(Object.class, result);
    }
}
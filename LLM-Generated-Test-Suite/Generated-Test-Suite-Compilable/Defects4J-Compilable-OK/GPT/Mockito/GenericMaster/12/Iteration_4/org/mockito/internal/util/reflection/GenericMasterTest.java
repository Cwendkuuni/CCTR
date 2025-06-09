package org.mockito.internal.util.reflection;

import org.junit.Test;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GenericMasterTest {

    private static class TestClass {
        List<String> stringList;
        Map<String, Integer> stringIntegerMap;
        String nonGenericField;
    }

    @Test
    public void testGetGenericTypeWithGenericField() throws NoSuchFieldException {
        GenericMaster genericMaster = new GenericMaster();
        Field field = TestClass.class.getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithNonGenericField() throws NoSuchFieldException {
        GenericMaster genericMaster = new GenericMaster();
        Field field = TestClass.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericTypeWithMapField() throws NoSuchFieldException {
        GenericMaster genericMaster = new GenericMaster();
        Field field = TestClass.class.getDeclaredField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }
}
package org.mockito.internal.util.reflection;

import org.junit.Test;
import org.mockito.internal.util.reflection.GenericMaster;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GenericMasterTest {

    private GenericMaster genericMaster = new GenericMaster();

    // Sample fields to test
    private List<String> stringList;
    private Map<String, Integer> stringIntegerMap;
    private String nonGenericField;

    @Test
    public void testGetGenericTypeWithGenericField() throws NoSuchFieldException {
        Field field = this.getClass().getDeclaredField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithMapField() throws NoSuchFieldException {
        Field field = this.getClass().getDeclaredField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result); // First generic type argument
    }

    @Test
    public void testGetGenericTypeWithNonGenericField() throws NoSuchFieldException {
        Field field = this.getClass().getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericTypeWithNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertEquals(Object.class, result);
    }
}
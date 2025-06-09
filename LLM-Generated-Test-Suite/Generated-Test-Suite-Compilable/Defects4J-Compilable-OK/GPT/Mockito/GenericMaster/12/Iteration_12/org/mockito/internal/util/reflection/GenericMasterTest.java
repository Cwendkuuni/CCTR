package org.mockito.internal.util.reflection;

import org.junit.Before;
import org.junit.Test;

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
    private static class TestClass {
        public List<String> stringList;
        public Map<String, Integer> stringIntegerMap;
        public String nonGenericField;
    }

    @Test
    public void testGetGenericTypeWithGenericField() throws NoSuchFieldException {
        Field field = TestClass.class.getField("stringList");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithMapField() throws NoSuchFieldException {
        Field field = TestClass.class.getField("stringIntegerMap");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericTypeWithNonGenericField() throws NoSuchFieldException {
        Field field = TestClass.class.getField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericTypeWithNullField() {
        Class<?> result = genericMaster.getGenericType(null);
        assertEquals(Object.class, result);
    }
}
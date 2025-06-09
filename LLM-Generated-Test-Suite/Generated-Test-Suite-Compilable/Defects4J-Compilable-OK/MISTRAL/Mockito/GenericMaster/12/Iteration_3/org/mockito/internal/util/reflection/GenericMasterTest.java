package org.mockito.internal.util.reflection;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.List;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    @Test
    public void testGetGenericType_GenericField() throws NoSuchFieldException {
        Field field = GenericClass.class.getDeclaredField("genericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_NonGenericField() throws NoSuchFieldException {
        Field field = GenericClass.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_NestedGenericField() throws NoSuchFieldException {
        Field field = GenericClass.class.getDeclaredField("nestedGenericField");
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result); // Since we don't go deep into nested generics
    }

    @Test
    public void testGetGenericType_NullField() {
        Field field = null;
        Class<?> result = genericMaster.getGenericType(field);
        assertEquals(Object.class, result);
    }

    // Helper class for testing
    private static class GenericClass {
        public List<String> genericField;
        public Object nonGenericField;
        public List<List<String>> nestedGenericField;
    }
}
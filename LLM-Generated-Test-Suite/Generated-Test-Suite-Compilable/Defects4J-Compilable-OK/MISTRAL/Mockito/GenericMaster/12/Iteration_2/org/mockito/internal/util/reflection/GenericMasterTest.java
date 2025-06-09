package org.mockito.internal.util.reflection;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    @Test
    public void testGetGenericType_WithGenericField() throws NoSuchFieldException {
        class TestClass {
            List<String> genericField;
        }

        Field field = TestClass.class.getDeclaredField("genericField");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_WithNonGenericField() throws NoSuchFieldException {
        class TestClass {
            String nonGenericField;
        }

        Field field = TestClass.class.getDeclaredField("nonGenericField");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNestedGenericField() throws NoSuchFieldException {
        class TestClass {
            List<List<String>> nestedGenericField;
        }

        Field field = TestClass.class.getDeclaredField("nestedGenericField");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNullField() {
        Field field = null;
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNonParameterizedTypeField() throws NoSuchFieldException {
        class TestClass {
            int[] arrayField;
        }

        Field field = TestClass.class.getDeclaredField("arrayField");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }
}
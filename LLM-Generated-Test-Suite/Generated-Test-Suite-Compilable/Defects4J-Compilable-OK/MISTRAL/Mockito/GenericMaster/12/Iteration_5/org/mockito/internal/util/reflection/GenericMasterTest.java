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
        class GenericClass<T> {
            List<T> list;
        }

        Field field = GenericClass.class.getDeclaredField("list");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNonGenericField() throws NoSuchFieldException {
        class NonGenericClass {
            String field;
        }

        Field field = NonGenericClass.class.getDeclaredField("field");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNestedGenericField() throws NoSuchFieldException {
        class NestedGenericClass<T> {
            List<List<T>> nestedList;
        }

        Field field = NestedGenericClass.class.getDeclaredField("nestedList");
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNullField() {
        Field field = null;
        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }
}
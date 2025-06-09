package org.mockito.internal.util.reflection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GenericMasterTest {

    private GenericMaster genericMaster;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
    }

    @Test
    public void testGetGenericType_WithGenericField() throws NoSuchFieldException {
        // Arrange
        class GenericClass<T> {
            List<T> list;
        }

        Field field = GenericClass.class.getDeclaredField("list");

        // Act
        Class<?> result = genericMaster.getGenericType(field);

        // Assert
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNonGenericField() throws NoSuchFieldException {
        // Arrange
        class NonGenericClass {
            String field;
        }

        Field field = NonGenericClass.class.getDeclaredField("field");

        // Act
        Class<?> result = genericMaster.getGenericType(field);

        // Assert
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNullGenericType() throws NoSuchFieldException {
        // Arrange
        Field mockField = Mockito.mock(Field.class);
        Mockito.when(mockField.getGenericType()).thenReturn(null);

        // Act
        Class<?> result = genericMaster.getGenericType(mockField);

        // Assert
        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_WithNonParameterizedType() throws NoSuchFieldException {
        // Arrange
        class NonParameterizedClass {
            List list;
        }

        Field field = NonParameterizedClass.class.getDeclaredField("list");

        // Act
        Class<?> result = genericMaster.getGenericType(field);

        // Assert
        assertEquals(Object.class, result);
    }
}
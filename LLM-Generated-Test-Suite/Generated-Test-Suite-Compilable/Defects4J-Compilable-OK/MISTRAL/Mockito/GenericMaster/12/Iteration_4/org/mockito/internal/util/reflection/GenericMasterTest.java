package org.mockito.internal.util.reflection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GenericMasterTest {

    private GenericMaster genericMaster;
    private Field field;

    @Before
    public void setUp() {
        genericMaster = new GenericMaster();
        field = Mockito.mock(Field.class);
    }

    @Test
    public void testGetGenericType_NonGenericField() throws Exception {
        when(field.getGenericType()).thenReturn(String.class);

        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_GenericField() throws Exception {
        ParameterizedType parameterizedType = Mockito.mock(ParameterizedType.class);
        when(field.getGenericType()).thenReturn(parameterizedType);
        when(parameterizedType.getActualTypeArguments()).thenReturn(new Type[]{String.class});

        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(String.class, result);
    }

    @Test
    public void testGetGenericType_NestedGenericField() throws Exception {
        ParameterizedType parameterizedType = Mockito.mock(ParameterizedType.class);
        when(field.getGenericType()).thenReturn(parameterizedType);
        when(parameterizedType.getActualTypeArguments()).thenReturn(new Type[]{Mockito.mock(ParameterizedType.class)});

        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }

    @Test
    public void testGetGenericType_NullGenericType() throws Exception {
        when(field.getGenericType()).thenReturn(null);

        Class<?> result = genericMaster.getGenericType(field);

        assertEquals(Object.class, result);
    }
}
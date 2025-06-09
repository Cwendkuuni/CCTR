package com.google.gson;

import com.google.gson.TypeInfoFactory;
import com.google.gson.TypeInfo;
import com.google.gson.TypeInfoArray;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.WildcardType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TypeInfoFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Field mockField;
    private Type mockType;
    private Class<?> mockClass;

    @Before
    public void setUp() {
        mockField = mock(Field.class);
        mockType = mock(Type.class);
        mockClass = mock(Class.class);
    }

    @After
    public void tearDown() {
        mockField = null;
        mockType = null;
        mockClass = null;
    }

    @Test
    public void testGetTypeInfoForArray_ValidArrayType() {
        when(TypeUtils.isArray(mockType)).thenReturn(true);
        TypeInfoArray typeInfoArray = TypeInfoFactory.getTypeInfoForArray(mockType);
        assertNotNull(typeInfoArray);
    }

    @Test
    public void testGetTypeInfoForArray_InvalidArrayType() {
        when(TypeUtils.isArray(mockType)).thenReturn(false);
        thrown.expect(IllegalArgumentException.class);
        TypeInfoFactory.getTypeInfoForArray(mockType);
    }

    @Test
    public void testGetTypeInfoForField_ValidField() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("sampleField");
        TypeInfo typeInfo = TypeInfoFactory.getTypeInfoForField(field, SampleClass.class);
        assertNotNull(typeInfo);
    }

    @Test
    public void testGetActualType_ClassType() throws Exception {
        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockClass, mockType, mockClass);
        assertEquals(mockClass, result);
    }

    @Test
    public void testGetActualType_ParameterizedType() throws Exception {
        ParameterizedType mockParameterizedType = mock(ParameterizedType.class);
        when(mockParameterizedType.getRawType()).thenReturn(mockClass);
        when(mockParameterizedType.getActualTypeArguments()).thenReturn(new Type[]{mockType});

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockParameterizedType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testGetActualType_GenericArrayType() throws Exception {
        GenericArrayType mockGenericArrayType = mock(GenericArrayType.class);
        when(mockGenericArrayType.getGenericComponentType()).thenReturn(mockType);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockGenericArrayType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testGetActualType_TypeVariable() throws Exception {
        TypeVariable<?> mockTypeVariable = mock(TypeVariable.class);
        ParameterizedType mockParameterizedType = mock(ParameterizedType.class);
        when(mockParameterizedType.getActualTypeArguments()).thenReturn(new Type[]{mockType});
        when(mockParameterizedType.getRawType()).thenReturn(mockClass);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockTypeVariable, mockParameterizedType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testGetActualType_WildcardType() throws Exception {
        WildcardType mockWildcardType = mock(WildcardType.class);
        when(mockWildcardType.getUpperBounds()).thenReturn(new Type[]{mockType});

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockWildcardType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testExtractRealTypes() throws Exception {
        Method method = TypeInfoFactory.class.getDeclaredMethod("extractRealTypes", Type[].class, Type.class, Class.class);
        method.setAccessible(true);
        Type[] result = (Type[]) method.invoke(null, new Type[]{mockType}, mockType, mockClass);
        assertNotNull(result);
        assertEquals(1, result.length);
    }

    @Test
    public void testGetIndex() throws Exception {
        TypeVariable<?> mockTypeVariable = mock(TypeVariable.class);
        when(mockTypeVariable.equals(any())).thenReturn(true);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getIndex", TypeVariable[].class, TypeVariable.class);
        method.setAccessible(true);
        int index = (int) method.invoke(null, new TypeVariable[]{mockTypeVariable}, mockTypeVariable);
        assertEquals(0, index);
    }

    // Sample class for testing
    static class SampleClass<T> {
        T sampleField;
    }
}
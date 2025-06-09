package com.google.gson;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gson.*;
import org.junit.Test;
import org.junit.Before;
import java.lang.reflect.*;
import java.util.List;

public class TypeInfoFactoryTest {

    private Field mockField;
    private Type mockType;
    private TypeVariable<?> mockTypeVariable;
    private ParameterizedType mockParameterizedType;
    private GenericArrayType mockGenericArrayType;
    private WildcardType mockWildcardType;
    private Class<?> mockClass;

    @Before
    public void setUp() {
        mockField = mock(Field.class);
        mockType = mock(Type.class);
        mockTypeVariable = mock(TypeVariable.class);
        mockParameterizedType = mock(ParameterizedType.class);
        mockGenericArrayType = mock(GenericArrayType.class);
        mockWildcardType = mock(WildcardType.class);
        mockClass = mock(Class.class);
    }

    @Test
    public void testGetTypeInfoForArray() {
        when(TypeUtils.isArray(mockType)).thenReturn(true);
        TypeInfoArray typeInfoArray = TypeInfoFactory.getTypeInfoForArray(mockType);
        assertNotNull(typeInfoArray);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTypeInfoForArrayWithNonArrayType() {
        when(TypeUtils.isArray(mockType)).thenReturn(false);
        TypeInfoFactory.getTypeInfoForArray(mockType);
    }

    @Test
    public void testGetTypeInfoForField() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("sampleField");
        TypeInfo typeInfo = TypeInfoFactory.getTypeInfoForField(field, SampleClass.class);
        assertNotNull(typeInfo);
    }

    @Test
    public void testGetActualTypeWithClass() throws Exception {
        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockClass, mockType, mockClass);
        assertEquals(mockClass, result);
    }

    @Test
    public void testGetActualTypeWithParameterizedType() throws Exception {
        when(mockParameterizedType.getActualTypeArguments()).thenReturn(new Type[]{mockType});
        when(mockParameterizedType.getRawType()).thenReturn(mockClass);
        when(mockParameterizedType.getOwnerType()).thenReturn(null);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockParameterizedType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testGetActualTypeWithGenericArrayType() throws Exception {
        when(mockGenericArrayType.getGenericComponentType()).thenReturn(mockType);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockGenericArrayType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test
    public void testGetActualTypeWithWildcardType() throws Exception {
        when(mockWildcardType.getUpperBounds()).thenReturn(new Type[]{mockType});

        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        Type result = (Type) method.invoke(null, mockWildcardType, mockType, mockClass);
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActualTypeWithInvalidType() throws Exception {
        Method method = TypeInfoFactory.class.getDeclaredMethod("getActualType", Type.class, Type.class, Class.class);
        method.setAccessible(true);
        method.invoke(null, mockType, mockType, mockClass);
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
        TypeVariable<?>[] typeVariables = new TypeVariable<?>[]{mockTypeVariable};
        when(mockTypeVariable.equals(mockTypeVariable)).thenReturn(true);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getIndex", TypeVariable[].class, TypeVariable.class);
        method.setAccessible(true);
        int index = (int) method.invoke(null, typeVariables, mockTypeVariable);
        assertEquals(0, index);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetIndexWithInvalidTypeVariable() throws Exception {
        TypeVariable<?>[] typeVariables = new TypeVariable<?>[]{mockTypeVariable};
        when(mockTypeVariable.equals(mockTypeVariable)).thenReturn(false);

        Method method = TypeInfoFactory.class.getDeclaredMethod("getIndex", TypeVariable[].class, TypeVariable.class);
        method.setAccessible(true);
        method.invoke(null, typeVariables, mockTypeVariable);
    }

    // Sample class for testing
    private static class SampleClass {
        private List<String> sampleField;
    }
}
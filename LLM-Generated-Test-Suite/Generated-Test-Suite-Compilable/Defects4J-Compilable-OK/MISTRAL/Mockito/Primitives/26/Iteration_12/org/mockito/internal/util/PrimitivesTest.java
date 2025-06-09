package org.mockito.internal.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PrimitivesTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Boolean.class, boolean.class, false},
                {Character.class, char.class, '\u0000'},
                {Byte.class, byte.class, (byte) 0},
                {Short.class, short.class, (short) 0},
                {Integer.class, int.class, 0},
                {Long.class, long.class, 0L},
                {Float.class, float.class, 0F},
                {Double.class, double.class, 0D}
        });
    }

    private final Class<?> wrapperType;
    private final Class<?> primitiveType;
    private final Object defaultValue;

    public PrimitivesTest(Class<?> wrapperType, Class<?> primitiveType, Object defaultValue) {
        this.wrapperType = wrapperType;
        this.primitiveType = primitiveType;
        this.defaultValue = defaultValue;
    }

    @Test
    public void testPrimitiveTypeOf() {
        assertEquals(primitiveType, Primitives.primitiveTypeOf(wrapperType));
    }

    @Test
    public void testIsPrimitiveWrapper() {
        assertTrue(Primitives.isPrimitiveWrapper(wrapperType));
    }

    @Test
    public void testPrimitiveWrapperOf() {
        assertEquals(defaultValue, Primitives.primitiveWrapperOf(wrapperType));
    }

    @Test
    public void testPrimitiveValueOrNullFor() {
        assertEquals(defaultValue, Primitives.primitiveValueOrNullFor(primitiveType));
    }

    @Test
    public void testPrimitiveTypeOfPrimitive() {
        assertEquals(primitiveType, Primitives.primitiveTypeOf(primitiveType));
    }

    @Test
    public void testIsPrimitiveWrapperNonWrapper() {
        assertFalse(Primitives.isPrimitiveWrapper(String.class));
    }

    @Test
    public void testPrimitiveWrapperOfNonWrapper() {
        assertNull(Primitives.primitiveWrapperOf(String.class));
    }

    @Test
    public void testPrimitiveValueOrNullForNonPrimitive() {
        assertNull(Primitives.primitiveValueOrNullFor(String.class));
    }
}
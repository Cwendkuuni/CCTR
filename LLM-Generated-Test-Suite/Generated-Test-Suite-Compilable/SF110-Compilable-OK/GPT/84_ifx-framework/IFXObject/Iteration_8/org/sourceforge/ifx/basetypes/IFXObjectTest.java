package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import java.lang.reflect.Method;

public class IFXObjectTest {

    private IFXObject obj1;
    private IFXObject obj2;
    private IFXObject obj3;
    private IBaseType baseTypeMock;

    @Before
    public void setUp() {
        obj1 = new TestIFXObject();
        obj2 = new TestIFXObject();
        obj3 = new DifferentIFXObject();
        baseTypeMock = Mockito.mock(IBaseType.class);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(obj1.equals(obj1));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(obj1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(obj1.equals(obj3));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(obj1.equals(obj2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        ((TestIFXObject) obj2).setValue("Different Value");
        assertFalse(obj1.equals(obj2));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.TestIFXObject = {\n" +
                          "  Value = Test Value\n" +
                          "}";
        assertEquals(expected, obj1.toString());
    }

    @Test
    public void testToString_WithNullValue() {
        ((TestIFXObject) obj1).setValue(null);
        String expected = "org.sourceforge.ifx.basetypes.TestIFXObject = {\n" +
                          "  Value = NULL\n" +
                          "}";
        assertEquals(expected, obj1.toString());
    }

    @Test
    public void testToString_WithIBaseType() {
        Mockito.when(baseTypeMock.getString()).thenReturn("BaseTypeValue");
        ((TestIFXObject) obj1).setBaseType(baseTypeMock);
        String expected = "org.sourceforge.ifx.basetypes.TestIFXObject = {\n" +
                          "  Value = Test Value\n" +
                          "  BaseType = BaseTypeValue\n" +
                          "}";
        assertEquals(expected, obj1.toString());
    }

    // Mock classes for testing
    private class TestIFXObject extends IFXObject {
        private String value = "Test Value";
        private IBaseType baseType;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public IBaseType getBaseType() {
            return baseType;
        }

        public void setBaseType(IBaseType baseType) {
            this.baseType = baseType;
        }
    }

    private class DifferentIFXObject extends IFXObject {
        private String differentValue = "Different Value";

        public String getDifferentValue() {
            return differentValue;
        }
    }

    // Mock interface for IBaseType
    private interface IBaseType {
        String getString();
    }
}
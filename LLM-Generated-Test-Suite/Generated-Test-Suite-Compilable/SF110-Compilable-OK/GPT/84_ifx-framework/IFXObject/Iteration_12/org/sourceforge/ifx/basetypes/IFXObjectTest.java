package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

public class IFXObjectTest {

    private IFXObject ifxObject1;
    private IFXObject ifxObject2;
    private IFXObject ifxObject3;
    private IBaseType mockBaseType;

    @Before
    public void setUp() {
        ifxObject1 = new TestIFXObject();
        ifxObject2 = new TestIFXObject();
        ifxObject3 = new TestIFXObject();
        mockBaseType = Mockito.mock(IBaseType.class);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(ifxObject1.equals(ifxObject1));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(ifxObject1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(ifxObject1.equals(new Object()));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(ifxObject1.equals(ifxObject2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        ((TestIFXObject) ifxObject2).setValue("Different");
        assertFalse(ifxObject1.equals(ifxObject2));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.TestIFXObject = {\n" +
                          "  Value = TestValue\n" +
                          "}";
        assertEquals(expected, ifxObject1.toString());
    }

    @Test
    public void testToString_WithNullValue() {
        ((TestIFXObject) ifxObject1).setValue(null);
        String expected = "org.sourceforge.ifx.basetypes.TestIFXObject = {\n" +
                          "  Value = NULL\n" +
                          "}";
        assertEquals(expected, ifxObject1.toString());
    }

    @Test
    public void testEquals_WithIBaseType() {
        ((TestIFXObject) ifxObject1).setBaseType(mockBaseType);
        ((TestIFXObject) ifxObject2).setBaseType(mockBaseType);
        Mockito.when(mockBaseType.getString()).thenReturn("BaseTypeValue");
        assertTrue(ifxObject1.equals(ifxObject2));
    }

    @Test
    public void testEquals_WithDifferentIBaseType() {
        IBaseType mockBaseType2 = Mockito.mock(IBaseType.class);
        ((TestIFXObject) ifxObject1).setBaseType(mockBaseType);
        ((TestIFXObject) ifxObject2).setBaseType(mockBaseType2);
        Mockito.when(mockBaseType.getString()).thenReturn("BaseTypeValue1");
        Mockito.when(mockBaseType2.getString()).thenReturn("BaseTypeValue2");
        assertFalse(ifxObject1.equals(ifxObject2));
    }

    // Mock implementation of IFXObject for testing
    private class TestIFXObject extends IFXObject {
        private String value = "TestValue";
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

    // Mock interface for IBaseType
    private interface IBaseType {
        String getString();
    }
}
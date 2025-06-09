package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IFXObjectTest {

    private IFXObject obj1;
    private IFXObject obj2;
    private IFXObject obj3;
    private MockIFXObject mockObj1;
    private MockIFXObject mockObj2;
    private MockIFXObject mockObj3;

    @Before
    public void setUp() {
        obj1 = new IFXObject();
        obj2 = new IFXObject();
        obj3 = new IFXObject();
        
        mockObj1 = new MockIFXObject("value1");
        mockObj2 = new MockIFXObject("value1");
        mockObj3 = new MockIFXObject("value2");
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
        assertFalse(obj1.equals("Some String"));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(mockObj1.equals(mockObj2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(mockObj1.equals(mockObj3));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.IFXObject = {\n}";
        assertEquals(expected, obj1.toString());
    }

    @Test
    public void testToString_WithMockObject() {
        String expected = "org.sourceforge.ifx.basetypes.MockIFXObject = {\n  Value = value1\n}";
        assertEquals(expected, mockObj1.toString());
    }

    // Mock class for testing
    private class MockIFXObject extends IFXObject {
        private String value;

        public MockIFXObject(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Mock interface for IBaseType
    private interface IBaseType {
        String getString();
    }
}
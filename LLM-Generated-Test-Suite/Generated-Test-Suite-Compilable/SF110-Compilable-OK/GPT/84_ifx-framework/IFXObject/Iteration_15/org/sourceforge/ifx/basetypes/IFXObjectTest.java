package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IFXObjectTest {

    private IFXObject obj1;
    private IFXObject obj2;
    private IFXObject obj3;

    @Before
    public void setUp() {
        obj1 = new MockIFXObject("value1", 10);
        obj2 = new MockIFXObject("value1", 10);
        obj3 = new MockIFXObject("value2", 20);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(obj1.equals(obj1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(obj1.equals(obj2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(obj1.equals(obj3));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(obj1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(obj1.equals(new Object()));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.IFXObjectTest$MockIFXObject = {\n" +
                          "  StringValue = value1\n" +
                          "  IntValue = 10\n" +
                          "}";
        assertEquals(expected, obj1.toString().trim());
    }

    // Mock subclass to test IFXObject
    private class MockIFXObject extends IFXObject {
        private String stringValue;
        private int intValue;

        public MockIFXObject(String stringValue, int intValue) {
            this.stringValue = stringValue;
            this.intValue = intValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public int getIntValue() {
            return intValue;
        }
    }
}
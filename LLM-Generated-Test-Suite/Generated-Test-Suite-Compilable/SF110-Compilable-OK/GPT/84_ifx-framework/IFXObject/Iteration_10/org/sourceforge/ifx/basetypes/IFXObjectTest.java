package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IFXObjectTest {

    private IFXObject obj1;
    private IFXObject obj2;
    private IFXObject obj3;

    // Mock subclass of IFXObject for testing
    public class MockIFXObject extends IFXObject {
        private String name;
        private int value;

        public MockIFXObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    @Before
    public void setUp() {
        obj1 = new MockIFXObject("Test", 123);
        obj2 = new MockIFXObject("Test", 123);
        obj3 = new MockIFXObject("Different", 456);
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
        assertFalse(obj1.equals("String"));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.IFXObjectTest$MockIFXObject = {\n" +
                          "  Name = Test\n" +
                          "  Value = 123\n" +
                          "}";
        assertEquals(expected, obj1.toString());
    }

    @Test
    public void testToString_NullValues() {
        MockIFXObject objWithNull = new MockIFXObject(null, 0);
        String expected = "org.sourceforge.ifx.basetypes.IFXObjectTest$MockIFXObject = {\n" +
                          "  Name = NULL\n" +
                          "  Value = 0\n" +
                          "}";
        assertEquals(expected, objWithNull.toString());
    }
}
package org.sourceforge.ifx.basetypes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IFXObjectTest {

    private TestIFXObject obj1;
    private TestIFXObject obj2;
    private TestIFXObject obj3;

    @Before
    public void setUp() {
        obj1 = new TestIFXObject("value1", 123);
        obj2 = new TestIFXObject("value1", 123);
        obj3 = new TestIFXObject("value2", 456);
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
        assertFalse(obj1.equals("Some String"));
    }

    @Test
    public void testToString() {
        String expected = "org.sourceforge.ifx.basetypes.IFXObjectTest$TestIFXObject = {\n" +
                          "  StringField = value1\n" +
                          "  IntField = 123\n" +
                          "}";
        assertEquals(expected, obj1.toString().trim());
    }

    // Mock subclass to facilitate testing
    private class TestIFXObject extends IFXObject {
        private String stringField;
        private int intField;

        public TestIFXObject(String stringField, int intField) {
            this.stringField = stringField;
            this.intField = intField;
        }

        public String getStringField() {
            return stringField;
        }

        public int getIntField() {
            return intField;
        }
    }
}
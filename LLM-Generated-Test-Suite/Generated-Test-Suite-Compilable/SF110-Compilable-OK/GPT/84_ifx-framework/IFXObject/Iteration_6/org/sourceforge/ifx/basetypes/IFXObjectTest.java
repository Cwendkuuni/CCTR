package org.sourceforge.ifx.basetypes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.sourceforge.ifx.basetypes.IFXObject;

public class IFXObjectTest {

    // Mock subclass to test IFXObject
    public static class MockIFXObject extends IFXObject {
        private String name;
        private int[] numbers;
        
        public MockIFXObject(String name, int[] numbers) {
            this.name = name;
            this.numbers = numbers;
        }
        
        public String getName() {
            return name;
        }
        
        public int[] getNumbers() {
            return numbers;
        }
    }

    @Test
    public void testEquals_SameObject() {
        MockIFXObject obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        assertTrue(obj1.equals(obj1));
    }

    @Test
    public void testEquals_EqualObjects() {
        MockIFXObject obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        MockIFXObject obj2 = new MockIFXObject("Test", new int[]{1, 2, 3});
        assertTrue(obj1.equals(obj2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        MockIFXObject obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        MockIFXObject obj2 = new MockIFXObject("Test", new int[]{4, 5, 6});
        assertFalse(obj1.equals(obj2));
    }

    @Test
    public void testEquals_NullObject() {
        MockIFXObject obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        assertFalse(obj1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        MockIFXObject obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        assertFalse(obj1.equals(new Object()));
    }

    @Test
    public void testToString() {
        MockIFXObject obj = new MockIFXObject("Test", new int[]{1, 2, 3});
        String expected = "org.sourceforge.ifx.basetypes.IFXObjectTest$MockIFXObject = {\n" +
                          "  Name = Test\n" +
                          "  Numbers = [\n" +
                          "    [0]:org.sourceforge.ifx.basetypes.IFXObject = {\n" +
                          "    }\n" +
                          "    [1]:org.sourceforge.ifx.basetypes.IFXObject = {\n" +
                          "    }\n" +
                          "    [2]:org.sourceforge.ifx.basetypes.IFXObject = {\n" +
                          "    }\n" +
                          "  ]\n" +
                          "}";
        assertEquals(expected, obj.toString());
    }
}
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

    @Before
    public void setUp() {
        obj1 = new MockIFXObject("Test", new int[]{1, 2, 3});
        obj2 = new MockIFXObject("Test", new int[]{1, 2, 3});
        obj3 = new MockIFXObject("Different", new int[]{4, 5, 6});
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
        assertEquals(expected, obj1.toString());
    }
}
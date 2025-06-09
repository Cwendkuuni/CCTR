package org.apache.commons.lang.enums;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.commons.lang.enums.Enum;

import java.util.List;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static final int TEST_VALUE_1 = 1;
    private static final int TEST_VALUE_2 = 2;
    private static final int TEST_VALUE_3 = 3;

    private TestValuedEnum enum1;
    private TestValuedEnum enum2;
    private TestValuedEnum enum3;

    @Before
    public void setUp() {
        enum1 = new TestValuedEnum("Enum1", TEST_VALUE_1);
        enum2 = new TestValuedEnum("Enum2", TEST_VALUE_2);
        enum3 = new TestValuedEnum("Enum3", TEST_VALUE_3);
    }

    @Test
    public void testGetValue() {
        assertEquals(TEST_VALUE_1, enum1.getValue());
        assertEquals(TEST_VALUE_2, enum2.getValue());
        assertEquals(TEST_VALUE_3, enum3.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(enum1, TestValuedEnum.getEnum(TestValuedEnum.class, TEST_VALUE_1));
        assertEquals(enum2, TestValuedEnum.getEnum(TestValuedEnum.class, TEST_VALUE_2));
        assertEquals(enum3, TestValuedEnum.getEnum(TestValuedEnum.class, TEST_VALUE_3));
        assertNull(TestValuedEnum.getEnum(TestValuedEnum.class, 999));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueWithNullClass() {
        TestValuedEnum.getEnum(null, TEST_VALUE_1);
    }

    @Test
    public void testCompareTo() {
        assertTrue(enum1.compareTo(enum2) < 0);
        assertTrue(enum2.compareTo(enum1) > 0);
        assertTrue(enum1.compareTo(enum1) == 0);
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithInvalidType() {
        enum1.compareTo("InvalidType");
    }

    @Test
    public void testToString() {
        assertEquals("TestValuedEnum[Enum1=1]", enum1.toString());
        assertEquals("TestValuedEnum[Enum2=2]", enum2.toString());
        assertEquals("TestValuedEnum[Enum3=3]", enum3.toString());
    }

    // Helper class for testing
    private static class TestValuedEnum extends ValuedEnum {
        private static final long serialVersionUID = 1L;

        protected TestValuedEnum(String name, int value) {
            super(name, value);
        }

        public static Enum getEnum(Class enumClass, int value) {
            return ValuedEnum.getEnum(enumClass, value);
        }

        public static List getEnumList(Class enumClass) {
            return Enum.getEnumList(enumClass);
        }

        public static Iterator iterator(Class enumClass) {
            return Enum.getEnumList(enumClass).iterator();
        }
    }
}
package org.apache.commons.lang.enums;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.commons.lang.enums.Enum;

import java.util.List;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static final int TEST_VALUE_1 = 100;
    private static final int TEST_VALUE_2 = 200;
    private static final String TEST_NAME_1 = "TestEnum1";
    private static final String TEST_NAME_2 = "TestEnum2";

    private TestValuedEnum testEnum1;
    private TestValuedEnum testEnum2;

    @Before
    public void setUp() {
        testEnum1 = new TestValuedEnum(TEST_NAME_1, TEST_VALUE_1);
        testEnum2 = new TestValuedEnum(TEST_NAME_2, TEST_VALUE_2);
    }

    @Test
    public void testGetValue() {
        assertEquals(TEST_VALUE_1, testEnum1.getValue());
        assertEquals(TEST_VALUE_2, testEnum2.getValue());
    }

    @Test
    public void testCompareTo() {
        assertTrue(testEnum1.compareTo(testEnum2) < 0);
        assertTrue(testEnum2.compareTo(testEnum1) > 0);
        assertEquals(0, testEnum1.compareTo(testEnum1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithInvalidType() {
        testEnum1.compareTo(new Object());
    }

    @Test
    public void testToString() {
        assertEquals("TestValuedEnum[TestEnum1=100]", testEnum1.toString());
        assertEquals("TestValuedEnum[TestEnum2=200]", testEnum2.toString());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(testEnum1, TestValuedEnum.getEnum(TestValuedEnum.class, TEST_VALUE_1));
        assertEquals(testEnum2, TestValuedEnum.getEnum(TestValuedEnum.class, TEST_VALUE_2));
        assertNull(TestValuedEnum.getEnum(TestValuedEnum.class, 999));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueWithNullClass() {
        TestValuedEnum.getEnum(null, TEST_VALUE_1);
    }

    // Test class extending ValuedEnum for testing purposes
    private static class TestValuedEnum extends ValuedEnum {
        private static final long serialVersionUID = 1L;

        protected TestValuedEnum(String name, int value) {
            super(name, value);
        }

        public static Enum getEnum(Class enumClass, int value) {
            return ValuedEnum.getEnum(enumClass, value);
        }
    }
}
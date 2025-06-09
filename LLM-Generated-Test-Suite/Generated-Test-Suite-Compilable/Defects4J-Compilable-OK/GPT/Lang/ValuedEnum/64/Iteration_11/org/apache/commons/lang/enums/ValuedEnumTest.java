package org.apache.commons.lang.enums;

import org.apache.commons.lang.enums.ValuedEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static final int TEST_VALUE_1 = 1;
    private static final int TEST_VALUE_2 = 2;
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
    public void testCompareToInvalidType() {
        testEnum1.compareTo(new Object());
    }

    @Test
    public void testToString() {
        assertEquals("TestValuedEnum[TestEnum1=1]", testEnum1.toString());
        assertEquals("TestValuedEnum[TestEnum2=2]", testEnum2.toString());
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

    // A simple subclass of ValuedEnum for testing purposes
    private static class TestValuedEnum extends ValuedEnum {

        private static final List<TestValuedEnum> ENUM_LIST = new ArrayList<>();

        protected TestValuedEnum(String name, int value) {
            super(name, value);
            ENUM_LIST.add(this);
        }

        public static TestValuedEnum getEnum(Class enumClass, int value) {
            return (TestValuedEnum) ValuedEnum.getEnum(enumClass, value);
        }

        public static List<TestValuedEnum> getEnumList() {
            return ENUM_LIST;
        }
    }
}
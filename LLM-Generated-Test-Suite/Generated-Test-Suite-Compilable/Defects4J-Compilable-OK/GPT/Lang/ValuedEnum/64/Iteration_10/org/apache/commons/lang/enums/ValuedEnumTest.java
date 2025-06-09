package org.apache.commons.lang.enums;

import org.apache.commons.lang.enums.ValuedEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static final int TEST_VALUE_1 = 1;
    private static final int TEST_VALUE_2 = 2;
    private static final int TEST_VALUE_3 = 3;

    private TestEnum testEnum1;
    private TestEnum testEnum2;
    private TestEnum testEnum3;

    @Before
    public void setUp() {
        testEnum1 = new TestEnum("TestEnum1", TEST_VALUE_1);
        testEnum2 = new TestEnum("TestEnum2", TEST_VALUE_2);
        testEnum3 = new TestEnum("TestEnum3", TEST_VALUE_3);
    }

    @Test
    public void testGetValue() {
        assertEquals(TEST_VALUE_1, testEnum1.getValue());
        assertEquals(TEST_VALUE_2, testEnum2.getValue());
        assertEquals(TEST_VALUE_3, testEnum3.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(testEnum1, TestEnum.getEnum(TestEnum.class, TEST_VALUE_1));
        assertEquals(testEnum2, TestEnum.getEnum(TestEnum.class, TEST_VALUE_2));
        assertEquals(testEnum3, TestEnum.getEnum(TestEnum.class, TEST_VALUE_3));
        assertNull(TestEnum.getEnum(TestEnum.class, 999));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueWithNullClass() {
        TestEnum.getEnum(null, TEST_VALUE_1);
    }

    @Test
    public void testCompareTo() {
        assertTrue(testEnum1.compareTo(testEnum2) < 0);
        assertTrue(testEnum2.compareTo(testEnum1) > 0);
        assertEquals(0, testEnum1.compareTo(testEnum1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithDifferentType() {
        testEnum1.compareTo("NotAnEnum");
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[TestEnum1=1]", testEnum1.toString());
        assertEquals("TestEnum[TestEnum2=2]", testEnum2.toString());
        assertEquals("TestEnum[TestEnum3=3]", testEnum3.toString());
    }

    // TestEnum class for testing purposes
    private static class TestEnum extends ValuedEnum {

        private static final List<TestEnum> ENUM_LIST = new ArrayList<>();

        protected TestEnum(String name, int value) {
            super(name, value);
            ENUM_LIST.add(this);
        }

        public static TestEnum getEnum(Class enumClass, int value) {
            return (TestEnum) ValuedEnum.getEnum(enumClass, value);
        }

        public static List<TestEnum> getEnumList() {
            return ENUM_LIST;
        }

        public static Iterator<TestEnum> iterator() {
            return ENUM_LIST.iterator();
        }
    }
}
package org.apache.commons.lang.enums;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;

import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static final int TEST_VALUE_1 = 100;
    private static final int TEST_VALUE_2 = 200;
    private static final String TEST_NAME_1 = "TestEnum1";
    private static final String TEST_NAME_2 = "TestEnum2";

    private TestEnum testEnum1;
    private TestEnum testEnum2;

    @Before
    public void setUp() {
        testEnum1 = new TestEnum(TEST_NAME_1, TEST_VALUE_1);
        testEnum2 = new TestEnum(TEST_NAME_2, TEST_VALUE_2);
    }

    @Test
    public void testConstructor() {
        assertEquals(TEST_NAME_1, testEnum1.getName());
        assertEquals(TEST_VALUE_1, testEnum1.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(testEnum1, ValuedEnum.getEnum(TestEnum.class, TEST_VALUE_1));
        assertEquals(testEnum2, ValuedEnum.getEnum(TestEnum.class, TEST_VALUE_2));
        assertNull(ValuedEnum.getEnum(TestEnum.class, 999));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueWithNullClass() {
        ValuedEnum.getEnum(null, TEST_VALUE_1);
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
    public void testCompareToWithDifferentClass() {
        testEnum1.compareTo("NotAnEnum");
    }

    @Test
    public void testToString() {
        String expectedString1 = "TestEnum[TestEnum1=100]";
        String expectedString2 = "TestEnum[TestEnum2=200]";
        assertEquals(expectedString1, testEnum1.toString());
        assertEquals(expectedString2, testEnum2.toString());
    }

    // Helper class for testing
    private static class TestEnum extends ValuedEnum {
        protected TestEnum(String name, int value) {
            super(name, value);
        }

        public static List getEnumList() {
            return Enum.getEnumList(TestEnum.class);
        }
    }
}
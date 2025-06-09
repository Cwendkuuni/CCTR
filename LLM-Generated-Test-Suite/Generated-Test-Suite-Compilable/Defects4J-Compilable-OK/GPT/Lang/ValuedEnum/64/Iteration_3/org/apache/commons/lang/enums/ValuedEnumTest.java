package org.apache.commons.lang.enums;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static class TestEnum extends ValuedEnum {
        public static final int VALUE_ONE = 1;
        public static final int VALUE_TWO = 2;
        public static final TestEnum ENUM_ONE = new TestEnum("EnumOne", VALUE_ONE);
        public static final TestEnum ENUM_TWO = new TestEnum("EnumTwo", VALUE_TWO);

        private TestEnum(String name, int value) {
            super(name, value);
        }

        public static TestEnum getEnum(int value) {
            return (TestEnum) ValuedEnum.getEnum(TestEnum.class, value);
        }

        public static List getEnumList() {
            List list = new ArrayList();
            list.add(ENUM_ONE);
            list.add(ENUM_TWO);
            return list;
        }
    }

    @Before
    public void setUp() {
        // Setup if needed
    }

    @Test
    public void testConstructor() {
        TestEnum testEnum = new TestEnum("Test", 10);
        assertEquals("Test", testEnum.getName());
        assertEquals(10, testEnum.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(TestEnum.ENUM_ONE, TestEnum.getEnum(TestEnum.VALUE_ONE));
        assertEquals(TestEnum.ENUM_TWO, TestEnum.getEnum(TestEnum.VALUE_TWO));
        assertNull(TestEnum.getEnum(999)); // Non-existent value
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumWithNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testGetValue() {
        assertEquals(TestEnum.VALUE_ONE, TestEnum.ENUM_ONE.getValue());
        assertEquals(TestEnum.VALUE_TWO, TestEnum.ENUM_TWO.getValue());
    }

    @Test
    public void testCompareTo() {
        assertTrue(TestEnum.ENUM_ONE.compareTo(TestEnum.ENUM_TWO) < 0);
        assertTrue(TestEnum.ENUM_TWO.compareTo(TestEnum.ENUM_ONE) > 0);
        assertEquals(0, TestEnum.ENUM_ONE.compareTo(TestEnum.ENUM_ONE));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithInvalidType() {
        TestEnum.ENUM_ONE.compareTo("InvalidType");
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[EnumOne=1]", TestEnum.ENUM_ONE.toString());
        assertEquals("TestEnum[EnumTwo=2]", TestEnum.ENUM_TWO.toString());
    }
}
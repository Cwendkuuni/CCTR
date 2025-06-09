package org.apache.commons.lang.enums;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.enums.ValuedEnum;

public class ValuedEnumTest {

    private static class TestEnum extends ValuedEnum {
        public static final int VALUE1 = 1;
        public static final int VALUE2 = 2;
        public static final TestEnum ENUM1 = new TestEnum("Enum1", VALUE1);
        public static final TestEnum ENUM2 = new TestEnum("Enum2", VALUE2);

        private TestEnum(String name, int value) {
            super(name, value);
        }

        public static TestEnum getEnum(String name) {
            return (TestEnum) getEnum(TestEnum.class, name);
        }

        public static TestEnum getEnum(int value) {
            return (TestEnum) getEnum(TestEnum.class, value);
        }

        public static List getEnumList() {
            return getEnumList(TestEnum.class);
        }

        public static Iterator iterator() {
            return iterator(TestEnum.class);
        }
    }

    @Before
    public void setUp() {
        // Setup code if needed
    }

    @After
    public void tearDown() {
        // Tear down code if needed
    }

    @Test
    public void testGetValue() {
        assertEquals(TestEnum.VALUE1, TestEnum.ENUM1.getValue());
        assertEquals(TestEnum.VALUE2, TestEnum.ENUM2.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(TestEnum.ENUM1, TestEnum.getEnum(TestEnum.VALUE1));
        assertEquals(TestEnum.ENUM2, TestEnum.getEnum(TestEnum.VALUE2));
        assertNull(TestEnum.getEnum(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testCompareTo() {
        assertTrue(TestEnum.ENUM1.compareTo(TestEnum.ENUM2) < 0);
        assertTrue(TestEnum.ENUM2.compareTo(TestEnum.ENUM1) > 0);
        assertEquals(0, TestEnum.ENUM1.compareTo(TestEnum.ENUM1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToClassCastException() {
        TestEnum.ENUM1.compareTo(new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToNullPointerException() {
        TestEnum.ENUM1.compareTo(null);
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[Enum1=1]", TestEnum.ENUM1.toString());
        assertEquals("TestEnum[Enum2=2]", TestEnum.ENUM2.toString());
    }
}
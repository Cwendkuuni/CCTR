package org.apache.commons.lang.enums;

import org.apache.commons.lang.enums.ValuedEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static class TestEnum extends ValuedEnum {
        public static final TestEnum ENUM1 = new TestEnum("Enum1", 1);
        public static final TestEnum ENUM2 = new TestEnum("Enum2", 2);

        private TestEnum(String name, int value) {
            super(name, value);
        }

        public static TestEnum getEnum(int value) {
            return (TestEnum) getEnum(TestEnum.class, value);
        }

        public static List getEnumList() {
            return getEnumList(TestEnum.class);
        }
    }

    private TestEnum enum1;
    private TestEnum enum2;

    @Before
    public void setUp() {
        enum1 = TestEnum.ENUM1;
        enum2 = TestEnum.ENUM2;
    }

    @Test
    public void testConstructor() {
        assertEquals("Enum1", enum1.getName());
        assertEquals(1, enum1.getValue());

        assertEquals("Enum2", enum2.getName());
        assertEquals(2, enum2.getValue());
    }

    @Test
    public void testGetEnum() {
        assertEquals(enum1, TestEnum.getEnum(1));
        assertEquals(enum2, TestEnum.getEnum(2));
        assertNull(TestEnum.getEnum(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumWithNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testGetValue() {
        assertEquals(1, enum1.getValue());
        assertEquals(2, enum2.getValue());
    }

    @Test
    public void testCompareTo() {
        assertTrue(enum1.compareTo(enum2) < 0);
        assertTrue(enum2.compareTo(enum1) > 0);
        assertEquals(0, enum1.compareTo(enum1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithDifferentType() {
        enum1.compareTo(new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToWithNull() {
        enum1.compareTo(null);
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[Enum1=1]", enum1.toString());
        assertEquals("TestEnum[Enum2=2]", enum2.toString());
    }
}
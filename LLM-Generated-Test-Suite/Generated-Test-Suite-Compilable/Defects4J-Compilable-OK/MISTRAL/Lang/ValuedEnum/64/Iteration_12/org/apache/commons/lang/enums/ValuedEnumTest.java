package org.apache.commons.lang.enums;

import org.apache.commons.lang.enums.ValuedEnum;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    }

    private ValuedEnum enum1;
    private ValuedEnum enum2;

    @Before
    public void setUp() {
        enum1 = TestEnum.ENUM1;
        enum2 = TestEnum.ENUM2;
    }

    @Test
    public void testConstructor() {
        assertEquals("Enum1", enum1.getName());
        assertEquals(TestEnum.VALUE1, enum1.getValue());
        assertEquals("Enum2", enum2.getName());
        assertEquals(TestEnum.VALUE2, enum2.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(enum1, TestEnum.getEnum(TestEnum.VALUE1));
        assertEquals(enum2, TestEnum.getEnum(TestEnum.VALUE2));
        assertNull(TestEnum.getEnum(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testGetValue() {
        assertEquals(TestEnum.VALUE1, enum1.getValue());
        assertEquals(TestEnum.VALUE2, enum2.getValue());
    }

    @Test
    public void testCompareTo() {
        assertTrue(enum1.compareTo(enum2) < 0);
        assertTrue(enum2.compareTo(enum1) > 0);
        assertEquals(0, enum1.compareTo(enum1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToClassCastException() {
        enum1.compareTo(new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToNullPointerException() {
        enum1.compareTo(null);
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[Enum1=1]", enum1.toString());
        assertEquals("TestEnum[Enum2=2]", enum2.toString());
    }
}
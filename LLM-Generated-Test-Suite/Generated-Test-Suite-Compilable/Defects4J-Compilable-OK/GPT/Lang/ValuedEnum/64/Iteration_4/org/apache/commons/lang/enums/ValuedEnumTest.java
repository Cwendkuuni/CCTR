package org.apache.commons.lang.enums;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;
import java.util.List;
import java.util.Iterator;

public class ValuedEnumTest {

    private static final class TestEnum extends ValuedEnum {
        public static final int VALUE_ONE = 1;
        public static final int VALUE_TWO = 2;
        public static final TestEnum ENUM_ONE = new TestEnum("EnumOne", VALUE_ONE);
        public static final TestEnum ENUM_TWO = new TestEnum("EnumTwo", VALUE_TWO);

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

    @Test
    public void testGetValue() {
        assertEquals(1, TestEnum.ENUM_ONE.getValue());
        assertEquals(2, TestEnum.ENUM_TWO.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(TestEnum.ENUM_ONE, TestEnum.getEnum(1));
        assertEquals(TestEnum.ENUM_TWO, TestEnum.getEnum(2));
        assertNull(TestEnum.getEnum(3));
    }

    @Test
    public void testCompareTo() {
        assertTrue(TestEnum.ENUM_ONE.compareTo(TestEnum.ENUM_TWO) < 0);
        assertTrue(TestEnum.ENUM_TWO.compareTo(TestEnum.ENUM_ONE) > 0);
        assertEquals(0, TestEnum.ENUM_ONE.compareTo(TestEnum.ENUM_ONE));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToInvalidType() {
        TestEnum.ENUM_ONE.compareTo("InvalidType");
    }

    @Test
    public void testToString() {
        assertEquals("TestEnum[EnumOne=1]", TestEnum.ENUM_ONE.toString());
        assertEquals("TestEnum[EnumTwo=2]", TestEnum.ENUM_TWO.toString());
    }

    @Test
    public void testGetEnumList() {
        List list = TestEnum.getEnumList();
        assertTrue(list.contains(TestEnum.ENUM_ONE));
        assertTrue(list.contains(TestEnum.ENUM_TWO));
        assertEquals(2, list.size());
    }

    @Test
    public void testIterator() {
        Iterator it = TestEnum.iterator();
        assertTrue(it.hasNext());
        assertEquals(TestEnum.ENUM_ONE, it.next());
        assertTrue(it.hasNext());
        assertEquals(TestEnum.ENUM_TWO, it.next());
        assertFalse(it.hasNext());
    }
}
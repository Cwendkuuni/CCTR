package org.apache.commons.lang.enums;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValuedEnumTest {

    private static class TestEnum extends ValuedEnum {
        public static final TestEnum ENUM1 = new TestEnum("Enum1", 1);
        public static final TestEnum ENUM2 = new TestEnum("Enum2", 2);

        protected TestEnum(String name, int value) {
            super(name, value);
        }

        public static TestEnum getEnum(int value) {
            return (TestEnum) ValuedEnum.getEnum(TestEnum.class, value);
        }

        public static List getEnumList() {
            List list = new ArrayList();
            list.add(ENUM1);
            list.add(ENUM2);
            return list;
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
        assertNotNull("Enum1 should not be null", enum1);
        assertNotNull("Enum2 should not be null", enum2);
    }

    @Test
    public void testGetValue() {
        assertEquals("Enum1 value should be 1", 1, enum1.getValue());
        assertEquals("Enum2 value should be 2", 2, enum2.getValue());
    }

    @Test
    public void testCompareTo() {
        assertTrue("Enum1 should be less than Enum2", enum1.compareTo(enum2) < 0);
        assertTrue("Enum2 should be greater than Enum1", enum2.compareTo(enum1) > 0);
        assertEquals("Enum1 should be equal to itself", 0, enum1.compareTo(enum1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToInvalidType() {
        enum1.compareTo("InvalidType");
    }

    @Test
    public void testGetEnum() {
        assertEquals("Should return ENUM1", TestEnum.ENUM1, TestEnum.getEnum(1));
        assertEquals("Should return ENUM2", TestEnum.ENUM2, TestEnum.getEnum(2));
        assertNull("Should return null for non-existent value", TestEnum.getEnum(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumWithNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testToString() {
        assertEquals("Enum1 toString should match", "TestEnum[Enum1=1]", enum1.toString());
        assertEquals("Enum2 toString should match", "TestEnum[Enum2=2]", enum2.toString());
    }
}
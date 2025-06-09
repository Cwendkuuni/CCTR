package org.apache.commons.lang.enums;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.lang.enums.ValuedEnum;

public class ValuedEnumTest {

    // Mock subclass for testing
    public static class MockValuedEnum extends ValuedEnum {
        public static final MockValuedEnum MOCK_ENUM_1 = new MockValuedEnum("MockEnum1", 1);
        public static final MockValuedEnum MOCK_ENUM_2 = new MockValuedEnum("MockEnum2", 2);

        private MockValuedEnum(String name, int value) {
            super(name, value);
        }

        public static MockValuedEnum getEnum(String name) {
            return (MockValuedEnum) getEnum(MockValuedEnum.class, name);
        }

        public static MockValuedEnum getEnum(int value) {
            return (MockValuedEnum) getEnum(MockValuedEnum.class, value);
        }
    }

    @Test
    public void testGetValue() {
        assertEquals(1, MockValuedEnum.MOCK_ENUM_1.getValue());
        assertEquals(2, MockValuedEnum.MOCK_ENUM_2.getValue());
    }

    @Test
    public void testGetEnumByValue() {
        assertEquals(MockValuedEnum.MOCK_ENUM_1, MockValuedEnum.getEnum(1));
        assertEquals(MockValuedEnum.MOCK_ENUM_2, MockValuedEnum.getEnum(2));
        assertNull(MockValuedEnum.getEnum(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumByValueWithNullClass() {
        ValuedEnum.getEnum(null, 1);
    }

    @Test
    public void testCompareTo() {
        assertTrue(MockValuedEnum.MOCK_ENUM_1.compareTo(MockValuedEnum.MOCK_ENUM_2) < 0);
        assertTrue(MockValuedEnum.MOCK_ENUM_2.compareTo(MockValuedEnum.MOCK_ENUM_1) > 0);
        assertEquals(0, MockValuedEnum.MOCK_ENUM_1.compareTo(MockValuedEnum.MOCK_ENUM_1));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToWithDifferentType() {
        MockValuedEnum.MOCK_ENUM_1.compareTo(new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToWithNull() {
        MockValuedEnum.MOCK_ENUM_1.compareTo(null);
    }

    @Test
    public void testToString() {
        assertEquals("MockValuedEnum[MockEnum1=1]", MockValuedEnum.MOCK_ENUM_1.toString());
        assertEquals("MockValuedEnum[MockEnum2=2]", MockValuedEnum.MOCK_ENUM_2.toString());
    }
}
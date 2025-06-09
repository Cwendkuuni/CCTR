package org.joda.time.field;

import static org.junit.Assert.*;
import org.joda.time.DurationFieldType;
import org.joda.time.field.UnsupportedDurationField;
import org.junit.Before;
import org.junit.Test;

public class UnsupportedDurationFieldTest {

    private UnsupportedDurationField unsupportedField;
    private DurationFieldType type;

    @Before
    public void setUp() {
        type = DurationFieldType.seconds();
        unsupportedField = UnsupportedDurationField.getInstance(type);
    }

    @Test
    public void testGetInstance() {
        UnsupportedDurationField field1 = UnsupportedDurationField.getInstance(type);
        UnsupportedDurationField field2 = UnsupportedDurationField.getInstance(type);
        assertSame(field1, field2);
    }

    @Test
    public void testGetType() {
        assertEquals(type, unsupportedField.getType());
    }

    @Test
    public void testGetName() {
        assertEquals(type.getName(), unsupportedField.getName());
    }

    @Test
    public void testIsSupported() {
        assertFalse(unsupportedField.isSupported());
    }

    @Test
    public void testIsPrecise() {
        assertTrue(unsupportedField.isPrecise());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValue() {
        unsupportedField.getValue(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLong() {
        unsupportedField.getValueAsLong(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueWithInstant() {
        unsupportedField.getValue(1000L, 2000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLongWithInstant() {
        unsupportedField.getValueAsLong(1000L, 2000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisInt() {
        unsupportedField.getMillis(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisLong() {
        unsupportedField.getMillis(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisIntWithInstant() {
        unsupportedField.getMillis(1, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisLongWithInstant() {
        unsupportedField.getMillis(1L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInt() {
        unsupportedField.add(1000L, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddLong() {
        unsupportedField.add(1000L, 1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifference() {
        unsupportedField.getDifference(2000L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifferenceAsLong() {
        unsupportedField.getDifferenceAsLong(2000L, 1000L);
    }

    @Test
    public void testGetUnitMillis() {
        assertEquals(0, unsupportedField.getUnitMillis());
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, unsupportedField.compareTo(unsupportedField));
    }

    @Test
    public void testEquals() {
        UnsupportedDurationField otherField = UnsupportedDurationField.getInstance(type);
        assertTrue(unsupportedField.equals(otherField));
        assertFalse(unsupportedField.equals(null));
        assertFalse(unsupportedField.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertEquals(type.getName().hashCode(), unsupportedField.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("UnsupportedDurationField[" + type.getName() + "]", unsupportedField.toString());
    }
}
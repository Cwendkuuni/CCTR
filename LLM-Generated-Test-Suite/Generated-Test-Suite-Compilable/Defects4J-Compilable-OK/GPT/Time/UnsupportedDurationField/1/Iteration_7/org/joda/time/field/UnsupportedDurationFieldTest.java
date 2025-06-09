package org.joda.time.field;

import static org.junit.Assert.*;
import org.joda.time.DurationFieldType;
import org.joda.time.field.UnsupportedDurationField;
import org.junit.Before;
import org.junit.Test;

public class UnsupportedDurationFieldTest {

    private UnsupportedDurationField unsupportedDurationField;
    private DurationFieldType type;

    @Before
    public void setUp() {
        type = DurationFieldType.days();
        unsupportedDurationField = UnsupportedDurationField.getInstance(type);
    }

    @Test
    public void testGetInstance() {
        UnsupportedDurationField field1 = UnsupportedDurationField.getInstance(type);
        UnsupportedDurationField field2 = UnsupportedDurationField.getInstance(type);
        assertSame(field1, field2);
    }

    @Test
    public void testGetType() {
        assertEquals(type, unsupportedDurationField.getType());
    }

    @Test
    public void testGetName() {
        assertEquals(type.getName(), unsupportedDurationField.getName());
    }

    @Test
    public void testIsSupported() {
        assertFalse(unsupportedDurationField.isSupported());
    }

    @Test
    public void testIsPrecise() {
        assertTrue(unsupportedDurationField.isPrecise());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueLong() {
        unsupportedDurationField.getValue(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLongLong() {
        unsupportedDurationField.getValueAsLong(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueLongLong() {
        unsupportedDurationField.getValue(1000L, 2000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLongLongLong() {
        unsupportedDurationField.getValueAsLong(1000L, 2000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisInt() {
        unsupportedDurationField.getMillis(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisLong() {
        unsupportedDurationField.getMillis(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisIntLong() {
        unsupportedDurationField.getMillis(1, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisLongLong() {
        unsupportedDurationField.getMillis(1L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddLongInt() {
        unsupportedDurationField.add(1000L, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddLongLong() {
        unsupportedDurationField.add(1000L, 1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifferenceLongLong() {
        unsupportedDurationField.getDifference(2000L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifferenceAsLongLongLong() {
        unsupportedDurationField.getDifferenceAsLong(2000L, 1000L);
    }

    @Test
    public void testGetUnitMillis() {
        assertEquals(0, unsupportedDurationField.getUnitMillis());
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, unsupportedDurationField.compareTo(unsupportedDurationField));
    }

    @Test
    public void testEquals() {
        UnsupportedDurationField anotherField = UnsupportedDurationField.getInstance(type);
        assertTrue(unsupportedDurationField.equals(anotherField));
        assertFalse(unsupportedDurationField.equals(null));
        assertFalse(unsupportedDurationField.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertEquals(type.getName().hashCode(), unsupportedDurationField.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("UnsupportedDurationField[" + type.getName() + "]", unsupportedDurationField.toString());
    }
}
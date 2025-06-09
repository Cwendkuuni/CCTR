package org.joda.time.field;

import static org.junit.Assert.*;
import org.joda.time.DurationFieldType;
import org.joda.time.field.UnsupportedDurationField;
import org.junit.Before;
import org.junit.Test;

public class UnsupportedDurationFieldTest {

    private UnsupportedDurationField unsupportedDurationField;
    private DurationFieldType durationFieldType;

    @Before
    public void setUp() {
        durationFieldType = DurationFieldType.seconds();
        unsupportedDurationField = UnsupportedDurationField.getInstance(durationFieldType);
    }

    @Test
    public void testGetInstance() {
        UnsupportedDurationField field1 = UnsupportedDurationField.getInstance(durationFieldType);
        UnsupportedDurationField field2 = UnsupportedDurationField.getInstance(durationFieldType);
        assertSame(field1, field2);
    }

    @Test
    public void testGetType() {
        assertEquals(durationFieldType, unsupportedDurationField.getType());
    }

    @Test
    public void testGetName() {
        assertEquals(durationFieldType.getName(), unsupportedDurationField.getName());
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
    public void testGetValue() {
        unsupportedDurationField.getValue(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLong() {
        unsupportedDurationField.getValueAsLong(1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueWithInstant() {
        unsupportedDurationField.getValue(1000L, 2000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetValueAsLongWithInstant() {
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
    public void testGetMillisIntWithInstant() {
        unsupportedDurationField.getMillis(1, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMillisLongWithInstant() {
        unsupportedDurationField.getMillis(1L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInt() {
        unsupportedDurationField.add(1000L, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddLong() {
        unsupportedDurationField.add(1000L, 1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifference() {
        unsupportedDurationField.getDifference(2000L, 1000L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetDifferenceAsLong() {
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
        UnsupportedDurationField field1 = UnsupportedDurationField.getInstance(durationFieldType);
        UnsupportedDurationField field2 = UnsupportedDurationField.getInstance(durationFieldType);
        assertTrue(field1.equals(field2));
        assertFalse(field1.equals(null));
        assertFalse(field1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertEquals(durationFieldType.getName().hashCode(), unsupportedDurationField.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("UnsupportedDurationField[" + durationFieldType.getName() + "]", unsupportedDurationField.toString());
    }
}
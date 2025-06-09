package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.LenientDateTimeField;
import org.joda.time.field.StrictDateTimeField;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LenientDateTimeFieldTest {

    private Chronology chronology;
    private DateTimeField strictField;
    private DateTimeField lenientField;

    @Before
    public void setUp() {
        chronology = ISOChronology.getInstanceUTC();
        strictField = new StrictDateTimeField(chronology.dayOfMonth());
        lenientField = LenientDateTimeField.getInstance(strictField, chronology);
    }

    @Test
    public void testGetInstance_NullField() {
        assertNull(LenientDateTimeField.getInstance(null, chronology));
    }

    @Test
    public void testGetInstance_StrictField() {
        DateTimeField field = LenientDateTimeField.getInstance(strictField, chronology);
        assertTrue(field instanceof LenientDateTimeField);
    }

    @Test
    public void testGetInstance_AlreadyLenient() {
        DateTimeField field = LenientDateTimeField.getInstance(lenientField, chronology);
        assertSame(lenientField, field);
    }

    @Test
    public void testIsLenient() {
        assertTrue(lenientField.isLenient());
    }

    @Test
    public void testSet_InBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = 15; // Assuming 15 is a valid day of the month
        long result = lenientField.set(instant, value);
        assertEquals(value, lenientField.get(result));
    }

    @Test
    public void testSet_OutOfBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = 35; // Assuming 35 is out of bounds for day of the month
        long result = lenientField.set(instant, value);
        // The result should be the last day of the month plus the excess days
        int expectedDay = 35 % 30; // Assuming 30 days in the month for simplicity
        assertEquals(expectedDay, lenientField.get(result));
    }

    @Test
    public void testSet_NegativeValue() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = -5; // Assuming -5 is out of bounds for day of the month
        long result = lenientField.set(instant, value);
        // The result should be the last day of the previous month minus the excess days
        int expectedDay = 30 + (-5 % 30); // Assuming 30 days in the month for simplicity
        assertEquals(expectedDay, lenientField.get(result));
    }
}
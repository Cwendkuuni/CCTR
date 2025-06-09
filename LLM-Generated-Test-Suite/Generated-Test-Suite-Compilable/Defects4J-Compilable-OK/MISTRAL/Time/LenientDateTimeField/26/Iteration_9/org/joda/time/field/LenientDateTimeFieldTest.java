package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.chrono.ISOChronology;
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
    public void testGetInstance_AlreadyLenient() {
        assertTrue(lenientField.isLenient());
        DateTimeField result = LenientDateTimeField.getInstance(lenientField, chronology);
        assertSame(lenientField, result);
    }

    @Test
    public void testGetInstance_StrictField() {
        assertFalse(strictField.isLenient());
        DateTimeField result = LenientDateTimeField.getInstance(strictField, chronology);
        assertTrue(result.isLenient());
    }

    @Test
    public void testIsLenient() {
        assertTrue(lenientField.isLenient());
    }

    @Test
    public void testSet_InBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = 15; // Assuming 15 is within bounds for dayOfMonth
        long result = lenientField.set(instant, value);
        assertEquals(value, lenientField.get(result));
    }

    @Test
    public void testSet_OutOfBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = 40; // Assuming 40 is out of bounds for dayOfMonth
        long result = lenientField.set(instant, value);
        // The result should be the original instant plus the difference
        int originalValue = lenientField.get(instant);
        int expectedValue = originalValue + (value - originalValue);
        assertEquals(expectedValue, lenientField.get(result));
    }

    @Test
    public void testSet_NegativeValue() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int value = -5; // Assuming -5 is out of bounds for dayOfMonth
        long result = lenientField.set(instant, value);
        // The result should be the original instant minus the difference
        int originalValue = lenientField.get(instant);
        int expectedValue = originalValue - (originalValue - value);
        assertEquals(expectedValue, lenientField.get(result));
    }
}
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
        strictField = chronology.dayOfMonth();
        lenientField = LenientDateTimeField.getInstance(strictField, chronology);
    }

    @Test
    public void testGetInstance_NullField() {
        assertNull(LenientDateTimeField.getInstance(null, chronology));
    }

    @Test
    public void testGetInstance_AlreadyLenient() {
        DateTimeField alreadyLenientField = LenientDateTimeField.getInstance(strictField, chronology);
        assertSame(alreadyLenientField, LenientDateTimeField.getInstance(alreadyLenientField, chronology));
    }

    @Test
    public void testGetInstance_StrictField() {
        assertTrue(LenientDateTimeField.getInstance(strictField, chronology) instanceof LenientDateTimeField);
    }

    @Test
    public void testIsLenient() {
        assertTrue(lenientField.isLenient());
    }

    @Test
    public void testSet_InBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int newValue = 15; // Assuming 15 is within bounds for day of month
        long result = lenientField.set(instant, newValue);
        assertEquals(newValue, lenientField.get(result));
    }

    @Test
    public void testSet_OutOfBounds() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int newValue = 40; // Assuming 40 is out of bounds for day of month
        long result = lenientField.set(instant, newValue);
        // The result should be the original instant plus the difference
        int originalValue = lenientField.get(instant);
        int expectedValue = originalValue + (newValue - originalValue);
        assertEquals(expectedValue, lenientField.get(result));
    }

    @Test
    public void testSet_NegativeValue() {
        long instant = chronology.dayOfMonth().roundFloor(System.currentTimeMillis());
        int newValue = -5; // Assuming -5 is out of bounds for day of month
        long result = lenientField.set(instant, newValue);
        // The result should be the original instant minus the difference
        int originalValue = lenientField.get(instant);
        int expectedValue = originalValue - (originalValue - newValue);
        assertEquals(expectedValue, lenientField.get(result));
    }
}
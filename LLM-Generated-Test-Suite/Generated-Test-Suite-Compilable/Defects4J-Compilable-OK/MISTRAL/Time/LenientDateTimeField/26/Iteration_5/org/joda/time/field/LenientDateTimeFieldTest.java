package org.joda.time.field;

import static org.junit.Assert.*;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.LenientDateTimeField;
import org.joda.time.field.StrictDateTimeField;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue(LenientDateTimeField.getInstance(strictField, chronology) instanceof LenientDateTimeField);
    }

    @Test
    public void testGetInstance_AlreadyLenient() {
        DateTimeField alreadyLenient = LenientDateTimeField.getInstance(strictField, chronology);
        assertSame(alreadyLenient, LenientDateTimeField.getInstance(alreadyLenient, chronology));
    }

    @Test
    public void testIsLenient() {
        assertTrue(lenientField.isLenient());
    }

    @Test
    public void testSet_InBounds() {
        long instant = chronology.getDateTimeMillis(2023, 1, 1, 0, 0, 0, 0);
        int value = 5;
        long expected = chronology.getDateTimeMillis(2023, 1, 5, 0, 0, 0, 0);
        assertEquals(expected, lenientField.set(instant, value));
    }

    @Test
    public void testSet_OutOfBounds() {
        long instant = chronology.getDateTimeMillis(2023, 1, 1, 0, 0, 0, 0);
        int value = 35; // Out of bounds for day of month
        long expected = chronology.getDateTimeMillis(2023, 2, 4, 0, 0, 0, 0); // 31 days in January + 4 days
        assertEquals(expected, lenientField.set(instant, value));
    }

    @Test
    public void testSet_NegativeValue() {
        long instant = chronology.getDateTimeMillis(2023, 1, 1, 0, 0, 0, 0);
        int value = -5; // Negative value
        long expected = chronology.getDateTimeMillis(2022, 12, 27, 0, 0, 0, 0); // 5 days before January 1, 2023
        assertEquals(expected, lenientField.set(instant, value));
    }
}
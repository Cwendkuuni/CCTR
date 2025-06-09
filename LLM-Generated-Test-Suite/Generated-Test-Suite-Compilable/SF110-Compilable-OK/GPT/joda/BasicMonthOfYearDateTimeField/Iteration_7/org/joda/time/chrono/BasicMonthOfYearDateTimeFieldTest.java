package org.joda.time.chrono;

import static org.junit.Assert.*;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;

public class BasicMonthOfYearDateTimeFieldTest {

    private BasicMonthOfYearDateTimeField field;
    private BasicChronology chronology;

    @Before
    public void setUp() {
        chronology = GregorianChronology.getInstance();
        field = new BasicMonthOfYearDateTimeField(chronology, 2); // Assuming February as leap month
    }

    @Test
    public void testIsLenient() {
        assertFalse(field.isLenient());
    }

    @Test
    public void testGet() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        assertEquals(5, field.get(millis));
    }

    @Test
    public void testAddInt() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long result = field.add(millis, 2);
        assertEquals(7, chronology.getMonthOfYear(result));
    }

    @Test
    public void testAddLong() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long result = field.add(millis, 2L);
        assertEquals(7, chronology.getMonthOfYear(result));
    }

    @Test
    public void testAddWrapField() {
        long millis = chronology.getDateTimeMillis(2023, 12, 15, 0);
        long result = field.addWrapField(millis, 1);
        assertEquals(1, chronology.getMonthOfYear(result));
    }

    @Test
    public void testGetDifferenceAsLong() {
        long millis1 = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long millis2 = chronology.getDateTimeMillis(2022, 5, 15, 0);
        assertEquals(12, field.getDifferenceAsLong(millis1, millis2));
    }

    @Test
    public void testSet() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long result = field.set(millis, 8);
        assertEquals(8, chronology.getMonthOfYear(result));
    }

    @Test
    public void testGetRangeDurationField() {
        DurationField rangeDurationField = field.getRangeDurationField();
        assertEquals(chronology.years(), rangeDurationField);
    }

    @Test
    public void testIsLeap() {
        long millis = chronology.getDateTimeMillis(2024, 2, 15, 0); // Leap year
        assertTrue(field.isLeap(millis));
    }

    @Test
    public void testGetLeapAmount() {
        long millis = chronology.getDateTimeMillis(2024, 2, 15, 0); // Leap year
        assertEquals(1, field.getLeapAmount(millis));
    }

    @Test
    public void testGetLeapDurationField() {
        DurationField leapDurationField = field.getLeapDurationField();
        assertEquals(chronology.days(), leapDurationField);
    }

    @Test
    public void testGetMinimumValue() {
        assertEquals(1, field.getMinimumValue());
    }

    @Test
    public void testGetMaximumValue() {
        assertEquals(12, field.getMaximumValue());
    }

    @Test
    public void testRoundFloor() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long result = field.roundFloor(millis);
        assertEquals(chronology.getDateTimeMillis(2023, 5, 1, 0), result);
    }

    @Test
    public void testRemainder() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        long result = field.remainder(millis);
        assertEquals(millis - field.roundFloor(millis), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLongThrowsException() {
        long millis = chronology.getDateTimeMillis(2023, 5, 15, 0);
        field.add(millis, Long.MAX_VALUE);
    }
}
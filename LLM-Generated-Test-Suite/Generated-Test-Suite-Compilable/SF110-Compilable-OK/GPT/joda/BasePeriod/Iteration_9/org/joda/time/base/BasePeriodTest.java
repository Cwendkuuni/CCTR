package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BasePeriod;
import org.joda.time.chrono.ISOChronology;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BasePeriodTest {

    private BasePeriod basePeriod;

    // Concrete subclass for testing
    private static class TestPeriod extends BasePeriod {
        protected TestPeriod(int years, int months, int weeks, int days, int hours, int minutes, int seconds, int millis) {
            super(years, months, weeks, days, hours, minutes, seconds, millis, PeriodType.standard());
        }
    }

    @Before
    public void setUp() {
        basePeriod = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    public void testGetPeriodType() {
        assertEquals(PeriodType.standard(), basePeriod.getPeriodType());
    }

    @Test
    public void testGetValue() {
        assertEquals(1, basePeriod.getValue(0)); // years
        assertEquals(2, basePeriod.getValue(1)); // months
        assertEquals(3, basePeriod.getValue(2)); // weeks
        assertEquals(4, basePeriod.getValue(3)); // days
        assertEquals(5, basePeriod.getValue(4)); // hours
        assertEquals(6, basePeriod.getValue(5)); // minutes
        assertEquals(7, basePeriod.getValue(6)); // seconds
        assertEquals(8, basePeriod.getValue(7)); // millis
    }

    @Test
    public void testToDurationFrom() {
        ReadableInstant instant = new Instant(0L);
        Duration duration = basePeriod.toDurationFrom(instant);
        assertNotNull(duration);
    }

    @Test
    public void testToDurationTo() {
        ReadableInstant instant = new Instant(0L);
        Duration duration = basePeriod.toDurationTo(instant);
        assertNotNull(duration);
    }

    @Test
    public void testSetField() {
        basePeriod.setField(DurationFieldType.years(), 10);
        assertEquals(10, basePeriod.getValue(0));
    }

    @Test
    public void testAddField() {
        basePeriod.addField(DurationFieldType.years(), 5);
        assertEquals(6, basePeriod.getValue(0));
    }

    @Test
    public void testMergePeriod() {
        ReadablePeriod period = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        basePeriod.mergePeriod(period);
        assertEquals(1, basePeriod.getValue(0)); // years
        assertEquals(1, basePeriod.getValue(1)); // months
        assertEquals(1, basePeriod.getValue(2)); // weeks
        assertEquals(1, basePeriod.getValue(3)); // days
        assertEquals(1, basePeriod.getValue(4)); // hours
        assertEquals(1, basePeriod.getValue(5)); // minutes
        assertEquals(1, basePeriod.getValue(6)); // seconds
        assertEquals(1, basePeriod.getValue(7)); // millis
    }

    @Test
    public void testAddPeriod() {
        ReadablePeriod period = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        basePeriod.addPeriod(period);
        assertEquals(2, basePeriod.getValue(0)); // years
        assertEquals(3, basePeriod.getValue(1)); // months
        assertEquals(4, basePeriod.getValue(2)); // weeks
        assertEquals(5, basePeriod.getValue(3)); // days
        assertEquals(6, basePeriod.getValue(4)); // hours
        assertEquals(7, basePeriod.getValue(5)); // minutes
        assertEquals(8, basePeriod.getValue(6)); // seconds
        assertEquals(9, basePeriod.getValue(7)); // millis
    }

    @Test
    public void testSetValue() {
        basePeriod.setValue(0, 5);
        assertEquals(5, basePeriod.getValue(0));
    }

    @Test
    public void testSetValues() {
        int[] newValues = {2, 3, 4, 5, 6, 7, 8, 9};
        basePeriod.setValues(newValues);
        assertArrayEquals(newValues, new int[]{
            basePeriod.getValue(0),
            basePeriod.getValue(1),
            basePeriod.getValue(2),
            basePeriod.getValue(3),
            basePeriod.getValue(4),
            basePeriod.getValue(5),
            basePeriod.getValue(6),
            basePeriod.getValue(7)
        });
    }
}
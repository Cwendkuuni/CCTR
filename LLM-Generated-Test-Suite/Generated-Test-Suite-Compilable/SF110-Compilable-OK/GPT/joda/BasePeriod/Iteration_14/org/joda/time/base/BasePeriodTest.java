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
    private static class TestBasePeriod extends BasePeriod {
        protected TestBasePeriod(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, PeriodType periodType) {
            super(n, n2, n3, n4, n5, n6, n7, n8, periodType);
        }

        protected TestBasePeriod(long n, long n2, PeriodType periodType, Chronology chronology) {
            super(n, n2, periodType, chronology);
        }

        protected TestBasePeriod(ReadableInstant readableInstant, ReadableInstant readableInstant2, PeriodType periodType) {
            super(readableInstant, readableInstant2, periodType);
        }

        protected TestBasePeriod(ReadablePartial readablePartial, ReadablePartial readablePartial2, PeriodType periodType) {
            super(readablePartial, readablePartial2, periodType);
        }

        protected TestBasePeriod(ReadableInstant readableInstant, ReadableDuration readableDuration, PeriodType periodType) {
            super(readableInstant, readableDuration, periodType);
        }

        protected TestBasePeriod(ReadableDuration readableDuration, ReadableInstant readableInstant, PeriodType periodType) {
            super(readableDuration, readableInstant, periodType);
        }

        protected TestBasePeriod(long n) {
            super(n);
        }

        protected TestBasePeriod(long n, PeriodType periodType, Chronology chronology) {
            super(n, periodType, chronology);
        }

        protected TestBasePeriod(Object o, PeriodType periodType, Chronology chronology) {
            super(o, periodType, chronology);
        }

        protected TestBasePeriod(int[] iValues, PeriodType iType) {
            super(iValues, iType);
        }
    }

    @Before
    public void setUp() {
        basePeriod = new TestBasePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.standard());
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
        ReadableInstant instant = new Instant(0);
        Duration duration = basePeriod.toDurationFrom(instant);
        assertNotNull(duration);
    }

    @Test
    public void testToDurationTo() {
        ReadableInstant instant = new Instant(0);
        Duration duration = basePeriod.toDurationTo(instant);
        assertNotNull(duration);
    }

    @Test
    public void testSetPeriod() {
        basePeriod.setPeriod(2, 3, 4, 5, 6, 7, 8, 9);
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
    public void testSetField() {
        basePeriod.setField(DurationFieldType.years(), 10);
        assertEquals(10, basePeriod.getValue(0)); // years
    }

    @Test
    public void testAddField() {
        basePeriod.addField(DurationFieldType.years(), 5);
        assertEquals(6, basePeriod.getValue(0)); // years (1 + 5)
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
        assertEquals(2, basePeriod.getValue(0)); // years (1 + 1)
        assertEquals(3, basePeriod.getValue(1)); // months (2 + 1)
        assertEquals(4, basePeriod.getValue(2)); // weeks (3 + 1)
        assertEquals(5, basePeriod.getValue(3)); // days (4 + 1)
        assertEquals(6, basePeriod.getValue(4)); // hours (5 + 1)
        assertEquals(7, basePeriod.getValue(5)); // minutes (6 + 1)
        assertEquals(8, basePeriod.getValue(6)); // seconds (7 + 1)
        assertEquals(9, basePeriod.getValue(7)); // millis (8 + 1)
    }

    @Test
    public void testSetValue() {
        basePeriod.setValue(0, 5);
        assertEquals(5, basePeriod.getValue(0)); // years
    }

    @Test
    public void testSetValues() {
        int[] values = {10, 20, 30, 40, 50, 60, 70, 80};
        basePeriod.setValues(values);
        assertArrayEquals(values, new int[]{
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
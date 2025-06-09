package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BasePeriod;
import org.joda.time.chrono.ISOChronology;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasePeriodTest {

    private static class TestPeriod extends BasePeriod {
        protected TestPeriod(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, PeriodType type) {
            super(n, n2, n3, n4, n5, n6, n7, n8, type);
        }

        protected TestPeriod(long n, long n2, PeriodType type, Chronology chronology) {
            super(n, n2, type, chronology);
        }

        protected TestPeriod(ReadableInstant start, ReadableInstant end, PeriodType type) {
            super(start, end, type);
        }

        protected TestPeriod(ReadablePartial start, ReadablePartial end, PeriodType type) {
            super(start, end, type);
        }

        protected TestPeriod(ReadableInstant start, ReadableDuration duration, PeriodType type) {
            super(start, duration, type);
        }

        protected TestPeriod(ReadableDuration duration, ReadableInstant end, PeriodType type) {
            super(duration, end, type);
        }

        protected TestPeriod(long duration) {
            super(duration);
        }

        protected TestPeriod(long duration, PeriodType type, Chronology chronology) {
            super(duration, type, chronology);
        }

        protected TestPeriod(Object period, PeriodType type, Chronology chronology) {
            super(period, type, chronology);
        }

        protected TestPeriod(int[] values, PeriodType type) {
            super(values, type);
        }
    }

    private TestPeriod period;

    @Before
    public void setUp() {
        period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.standard());
    }

    @Test
    public void testGetPeriodType() {
        assertEquals(PeriodType.standard(), period.getPeriodType());
    }

    @Test
    public void testGetValue() {
        assertEquals(1, period.getValue(0)); // years
        assertEquals(2, period.getValue(1)); // months
        assertEquals(3, period.getValue(2)); // weeks
        assertEquals(4, period.getValue(3)); // days
        assertEquals(5, period.getValue(4)); // hours
        assertEquals(6, period.getValue(5)); // minutes
        assertEquals(7, period.getValue(6)); // seconds
        assertEquals(8, period.getValue(7)); // millis
    }

    @Test
    public void testToDurationFrom() {
        ReadableInstant instant = new Instant(0);
        Duration duration = period.toDurationFrom(instant);
        assertNotNull(duration);
    }

    @Test
    public void testToDurationTo() {
        ReadableInstant instant = new Instant(0);
        Duration duration = period.toDurationTo(instant);
        assertNotNull(duration);
    }

    @Test
    public void testSetField() {
        period.setField(DurationFieldType.years(), 10);
        assertEquals(10, period.getValue(0));
    }

    @Test
    public void testAddField() {
        period.addField(DurationFieldType.years(), 5);
        assertEquals(6, period.getValue(0)); // 1 + 5 = 6
    }

    @Test
    public void testMergePeriod() {
        ReadablePeriod other = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        period.mergePeriod(other);
        assertEquals(1, period.getValue(0)); // years
        assertEquals(1, period.getValue(1)); // months
        assertEquals(1, period.getValue(2)); // weeks
        assertEquals(1, period.getValue(3)); // days
        assertEquals(1, period.getValue(4)); // hours
        assertEquals(1, period.getValue(5)); // minutes
        assertEquals(1, period.getValue(6)); // seconds
        assertEquals(1, period.getValue(7)); // millis
    }

    @Test
    public void testAddPeriod() {
        ReadablePeriod other = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        period.addPeriod(other);
        assertEquals(2, period.getValue(0)); // years
        assertEquals(3, period.getValue(1)); // months
        assertEquals(4, period.getValue(2)); // weeks
        assertEquals(5, period.getValue(3)); // days
        assertEquals(6, period.getValue(4)); // hours
        assertEquals(7, period.getValue(5)); // minutes
        assertEquals(8, period.getValue(6)); // seconds
        assertEquals(9, period.getValue(7)); // millis
    }

    @Test
    public void testSetValue() {
        period.setValue(0, 10);
        assertEquals(10, period.getValue(0));
    }

    @Test
    public void testSetValues() {
        int[] values = {10, 20, 30, 40, 50, 60, 70, 80};
        period.setValues(values);
        assertArrayEquals(values, new int[]{
                period.getValue(0),
                period.getValue(1),
                period.getValue(2),
                period.getValue(3),
                period.getValue(4),
                period.getValue(5),
                period.getValue(6),
                period.getValue(7)
        });
    }
}
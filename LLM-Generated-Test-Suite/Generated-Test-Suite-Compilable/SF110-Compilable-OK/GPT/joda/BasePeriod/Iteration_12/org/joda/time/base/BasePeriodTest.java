package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BasePeriod;
import org.joda.time.chrono.ISOChronology;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasePeriodTest {

    private static class TestPeriod extends BasePeriod {
        protected TestPeriod(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, PeriodType periodType) {
            super(n, n2, n3, n4, n5, n6, n7, n8, periodType);
        }

        protected TestPeriod(long n, long n2, PeriodType periodType, Chronology chronology) {
            super(n, n2, periodType, chronology);
        }

        protected TestPeriod(ReadableInstant start, ReadableInstant end, PeriodType periodType) {
            super(start, end, periodType);
        }

        protected TestPeriod(ReadablePartial start, ReadablePartial end, PeriodType periodType) {
            super(start, end, periodType);
        }

        protected TestPeriod(ReadableInstant start, ReadableDuration duration, PeriodType periodType) {
            super(start, duration, periodType);
        }

        protected TestPeriod(ReadableDuration duration, ReadableInstant end, PeriodType periodType) {
            super(duration, end, periodType);
        }

        protected TestPeriod(long duration) {
            super(duration);
        }

        protected TestPeriod(long duration, PeriodType periodType, Chronology chronology) {
            super(duration, periodType, chronology);
        }

        protected TestPeriod(Object period, PeriodType periodType, Chronology chronology) {
            super(period, periodType, chronology);
        }

        protected TestPeriod(int[] values, PeriodType periodType) {
            super(values, periodType);
        }
    }

    private PeriodType periodType;
    private Chronology chronology;

    @Before
    public void setUp() {
        periodType = PeriodType.standard();
        chronology = ISOChronology.getInstanceUTC();
    }

    @Test
    public void testConstructorWithInts() {
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
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
    public void testConstructorWithLongs() {
        long startMillis = 0L;
        long endMillis = 1000L;
        TestPeriod period = new TestPeriod(startMillis, endMillis, periodType, chronology);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithInstants() {
        ReadableInstant start = new Instant(0L);
        ReadableInstant end = new Instant(1000L);
        TestPeriod period = new TestPeriod(start, end, periodType);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithPartials() {
        ReadablePartial start = new LocalDate(2023, 1, 1);
        ReadablePartial end = new LocalDate(2023, 1, 2);
        TestPeriod period = new TestPeriod(start, end, periodType);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithInstantAndDuration() {
        ReadableInstant start = new Instant(0L);
        ReadableDuration duration = new Duration(1000L);
        TestPeriod period = new TestPeriod(start, duration, periodType);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithDurationAndInstant() {
        ReadableDuration duration = new Duration(1000L);
        ReadableInstant end = new Instant(1000L);
        TestPeriod period = new TestPeriod(duration, end, periodType);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithDuration() {
        long durationMillis = 1000L;
        TestPeriod period = new TestPeriod(durationMillis);
        assertNotNull(period);
    }

    @Test
    public void testConstructorWithObject() {
        Object periodObject = new Period(1, 2, 3, 4, 5, 6, 7, 8);
        TestPeriod period = new TestPeriod(periodObject, periodType, chronology);
        assertNotNull(period);
    }

    @Test
    public void testGetPeriodType() {
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        assertEquals(periodType, period.getPeriodType());
    }

    @Test
    public void testToDurationFrom() {
        ReadableInstant instant = new Instant(0L);
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        Duration duration = period.toDurationFrom(instant);
        assertNotNull(duration);
    }

    @Test
    public void testToDurationTo() {
        ReadableInstant instant = new Instant(0L);
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        Duration duration = period.toDurationTo(instant);
        assertNotNull(duration);
    }

    @Test
    public void testSetField() {
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        period.setField(DurationFieldType.years(), 10);
        assertEquals(10, period.getValue(0));
    }

    @Test
    public void testAddField() {
        TestPeriod period = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        period.addField(DurationFieldType.years(), 5);
        assertEquals(6, period.getValue(0));
    }

    @Test
    public void testMergePeriod() {
        TestPeriod period1 = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        TestPeriod period2 = new TestPeriod(2, 3, 4, 5, 6, 7, 8, 9, periodType);
        period1.mergePeriod(period2);
        assertEquals(2, period1.getValue(0)); // years
        assertEquals(3, period1.getValue(1)); // months
    }

    @Test
    public void testAddPeriod() {
        TestPeriod period1 = new TestPeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType);
        TestPeriod period2 = new TestPeriod(2, 3, 4, 5, 6, 7, 8, 9, periodType);
        period1.addPeriod(period2);
        assertEquals(3, period1.getValue(0)); // years
        assertEquals(5, period1.getValue(1)); // months
    }
}
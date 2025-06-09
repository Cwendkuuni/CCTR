package org.joda.time.base;

import static org.junit.Assert.*;
import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;

public class BaseSingleFieldPeriodTest {

    private BaseSingleFieldPeriod period;

    // A concrete subclass for testing purposes
    private static class TestSingleFieldPeriod extends BaseSingleFieldPeriod {
        protected TestSingleFieldPeriod(int period) {
            super(period);
        }

        @Override
        public DurationFieldType getFieldType() {
            return DurationFieldType.days();
        }

        @Override
        public PeriodType getPeriodType() {
            return PeriodType.days();
        }
    }

    @Before
    public void setUp() {
        period = new TestSingleFieldPeriod(5);
    }

    @Test
    public void testBetweenReadableInstant() {
        DateTime start = new DateTime(2023, 1, 1, 0, 0, DateTimeZone.UTC);
        DateTime end = new DateTime(2023, 1, 6, 0, 0, DateTimeZone.UTC);
        int daysBetween = BaseSingleFieldPeriod.between(start, end, DurationFieldType.days());
        assertEquals(5, daysBetween);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadableInstantNull() {
        BaseSingleFieldPeriod.between(null, null, DurationFieldType.days());
    }

    @Test
    public void testBetweenReadablePartial() {
        LocalDate start = new LocalDate(2023, 1, 1);
        LocalDate end = new LocalDate(2023, 1, 6);
        int daysBetween = BaseSingleFieldPeriod.between(start, end, Period.days(5));
        assertEquals(5, daysBetween);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadablePartialNull() {
        BaseSingleFieldPeriod.between(null, null, Period.days(5));
    }

    @Test
    public void testStandardPeriodIn() {
        Period period = Period.days(10);
        int standardDays = BaseSingleFieldPeriod.standardPeriodIn(period, DateTimeConstants.MILLIS_PER_DAY);
        assertEquals(10, standardDays);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStandardPeriodInNonPrecise() {
        Period period = Period.months(1);
        BaseSingleFieldPeriod.standardPeriodIn(period, DateTimeConstants.MILLIS_PER_DAY);
    }

    @Test
    public void testGetValue() {
        assertEquals(5, period.getValue());
    }

    @Test
    public void testSetValue() {
        period.setValue(10);
        assertEquals(10, period.getValue());
    }

    @Test
    public void testSize() {
        assertEquals(1, period.size());
    }

    @Test
    public void testGetFieldType() {
        assertEquals(DurationFieldType.days(), period.getFieldType());
    }

    @Test
    public void testGetValueByIndex() {
        assertEquals(5, period.getValue(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValueByIndexOutOfBounds() {
        period.getValue(1);
    }

    @Test
    public void testGet() {
        assertEquals(5, period.get(DurationFieldType.days()));
        assertEquals(0, period.get(DurationFieldType.hours()));
    }

    @Test
    public void testIsSupported() {
        assertTrue(period.isSupported(DurationFieldType.days()));
        assertFalse(period.isSupported(DurationFieldType.hours()));
    }

    @Test
    public void testToPeriod() {
        assertEquals(Period.days(5), period.toPeriod());
    }

    @Test
    public void testToMutablePeriod() {
        MutablePeriod mutablePeriod = period.toMutablePeriod();
        assertEquals(Period.days(5), mutablePeriod.toPeriod());
    }

    @Test
    public void testEquals() {
        BaseSingleFieldPeriod other = new TestSingleFieldPeriod(5);
        assertTrue(period.equals(other));
        assertFalse(period.equals(new TestSingleFieldPeriod(10)));
    }

    @Test
    public void testHashCode() {
        BaseSingleFieldPeriod other = new TestSingleFieldPeriod(5);
        assertEquals(period.hashCode(), other.hashCode());
    }

    @Test
    public void testCompareTo() {
        BaseSingleFieldPeriod other = new TestSingleFieldPeriod(10);
        assertTrue(period.compareTo(other) < 0);
        assertTrue(other.compareTo(period) > 0);
        assertEquals(0, period.compareTo(new TestSingleFieldPeriod(5)));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToDifferentClass() {
        BaseSingleFieldPeriod other = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.hours();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.hours();
            }
        };
        period.compareTo(other);
    }
}
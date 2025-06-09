package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseSingleFieldPeriodTest {

    private static class TestSingleFieldPeriod extends BaseSingleFieldPeriod {
        private static final long serialVersionUID = 1L;

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

    private TestSingleFieldPeriod period;

    @Before
    public void setUp() {
        period = new TestSingleFieldPeriod(5);
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
    public void testGetFieldType() {
        assertEquals(DurationFieldType.days(), period.getFieldType());
    }

    @Test
    public void testGetPeriodType() {
        assertEquals(PeriodType.days(), period.getPeriodType());
    }

    @Test
    public void testSize() {
        assertEquals(1, period.size());
    }

    @Test
    public void testGetFieldTypeByIndex() {
        assertEquals(DurationFieldType.days(), period.getFieldType(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFieldTypeByIndexOutOfBounds() {
        period.getFieldType(1);
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
        TestSingleFieldPeriod period2 = new TestSingleFieldPeriod(5);
        assertTrue(period.equals(period2));
        assertFalse(period.equals(new TestSingleFieldPeriod(6)));
        assertFalse(period.equals(null));
        assertFalse(period.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        TestSingleFieldPeriod period2 = new TestSingleFieldPeriod(5);
        assertEquals(period.hashCode(), period2.hashCode());
    }

    @Test
    public void testCompareTo() {
        TestSingleFieldPeriod period2 = new TestSingleFieldPeriod(5);
        TestSingleFieldPeriod period3 = new TestSingleFieldPeriod(6);
        assertEquals(0, period.compareTo(period2));
        assertEquals(-1, period.compareTo(period3));
        assertEquals(1, period3.compareTo(period));
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToDifferentClass() {
        period.compareTo(new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.hours();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.hours();
            }
        });
    }

    @Test
    public void testBetweenReadableInstant() {
        DateTime start = new DateTime(2023, 1, 1, 0, 0);
        DateTime end = new DateTime(2023, 1, 6, 0, 0);
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
        int daysBetween = BaseSingleFieldPeriod.between(start, end, Period.days(1));
        assertEquals(5, daysBetween);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadablePartialNull() {
        BaseSingleFieldPeriod.between(null, null, Period.days(1));
    }

    @Test
    public void testStandardPeriodIn() {
        Period period = Period.days(5);
        int standardDays = BaseSingleFieldPeriod.standardPeriodIn(period, DateTimeConstants.MILLIS_PER_DAY);
        assertEquals(5, standardDays);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStandardPeriodInNonPrecise() {
        Period period = Period.months(1);
        BaseSingleFieldPeriod.standardPeriodIn(period, DateTimeConstants.MILLIS_PER_DAY);
    }
}
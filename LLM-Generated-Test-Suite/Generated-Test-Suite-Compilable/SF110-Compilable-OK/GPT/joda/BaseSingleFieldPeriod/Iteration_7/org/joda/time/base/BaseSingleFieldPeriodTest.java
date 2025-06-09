package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseSingleFieldPeriodTest {

    private BaseSingleFieldPeriod period;

    @Before
    public void setUp() {
        period = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
    }

    @Test
    public void testBetweenReadableInstant() {
        ReadableInstant start = new Instant(0);
        ReadableInstant end = new Instant(86400000L); // 1 day later
        int result = BaseSingleFieldPeriod.between(start, end, DurationFieldType.days());
        assertEquals(1, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadableInstantNull() {
        BaseSingleFieldPeriod.between(null, new Instant(0), DurationFieldType.days());
    }

    @Test
    public void testBetweenReadablePartial() {
        ReadablePartial start = new LocalDate(1972, 1, 1);
        ReadablePartial end = new LocalDate(1972, 1, 2);
        int result = BaseSingleFieldPeriod.between(start, end, Period.days(1));
        assertEquals(1, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadablePartialNull() {
        BaseSingleFieldPeriod.between(null, new LocalDate(1972, 1, 1), Period.days(1));
    }

    @Test
    public void testStandardPeriodIn() {
        ReadablePeriod period = Period.days(2);
        int result = BaseSingleFieldPeriod.standardPeriodIn(period, 86400000L); // 1 day in milliseconds
        assertEquals(2, result);
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
        assertEquals(DurationFieldType.days(), period.getFieldType(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFieldTypeInvalidIndex() {
        period.getFieldType(1);
    }

    @Test
    public void testGetValueIndex() {
        assertEquals(5, period.getValue(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValueInvalidIndex() {
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
        BaseSingleFieldPeriod other = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
        assertTrue(period.equals(other));
        assertFalse(period.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        int expectedHashCode = 27 * (27 * 17 + 5) + DurationFieldType.days().hashCode();
        assertEquals(expectedHashCode, period.hashCode());
    }

    @Test
    public void testCompareTo() {
        BaseSingleFieldPeriod other = new BaseSingleFieldPeriod(3) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
        assertTrue(period.compareTo(other) > 0);
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToDifferentClass() {
        BaseSingleFieldPeriod other = new BaseSingleFieldPeriod(3) {
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
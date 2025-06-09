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
        BaseSingleFieldPeriod.between(null, null, DurationFieldType.days());
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
        BaseSingleFieldPeriod.between(null, null, Period.days(1));
    }

    @Test
    public void testStandardPeriodIn() {
        ReadablePeriod period = Period.days(2);
        int result = BaseSingleFieldPeriod.standardPeriodIn(period, 86400000L);
        assertEquals(2, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStandardPeriodInNonPrecise() {
        ReadablePeriod period = Period.months(1);
        BaseSingleFieldPeriod.standardPeriodIn(period, 86400000L);
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
    public void testGetValueAtIndex() {
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
        BaseSingleFieldPeriod anotherPeriod = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
        assertTrue(period.equals(anotherPeriod));
        assertFalse(period.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        int expectedHashCode = 27 * (27 * 17 + 5) + DurationFieldType.days().hashCode();
        assertEquals(expectedHashCode, period.hashCode());
    }

    @Test
    public void testCompareTo() {
        BaseSingleFieldPeriod smallerPeriod = new BaseSingleFieldPeriod(3) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
        assertTrue(period.compareTo(smallerPeriod) > 0);
        assertTrue(smallerPeriod.compareTo(period) < 0);
        assertTrue(period.compareTo(period) == 0);
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToDifferentClass() {
        BaseSingleFieldPeriod differentPeriod = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.hours();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.hours();
            }
        };
        period.compareTo(differentPeriod);
    }
}
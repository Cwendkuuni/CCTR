package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

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
        Period period = Period.days(10);
        int standardDays = BaseSingleFieldPeriod.standardPeriodIn(period, DateTimeConstants.MILLIS_PER_DAY);
        assertEquals(10, standardDays);
    }

    @Test
    public void testStandardPeriodInNull() {
        int standardDays = BaseSingleFieldPeriod.standardPeriodIn(null, DateTimeConstants.MILLIS_PER_DAY);
        assertEquals(0, standardDays);
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
    public void testGetValueByIndex() {
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
        assertFalse(period.equals(null));
        assertFalse(period.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        int expectedHashCode = 27 * (27 * 17 + 5) + DurationFieldType.days().hashCode();
        assertEquals(expectedHashCode, period.hashCode());
    }

    @Test
    public void testCompareTo() {
        BaseSingleFieldPeriod other = new BaseSingleFieldPeriod(10) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.days();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.days();
            }
        };
        assertTrue(period.compareTo(other) < 0);
        assertTrue(other.compareTo(period) > 0);
        assertEquals(0, period.compareTo(period));
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
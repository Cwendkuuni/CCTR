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
        private final DurationFieldType fieldType;
        private final PeriodType periodType;

        protected TestSingleFieldPeriod(int period, DurationFieldType fieldType, PeriodType periodType) {
            super(period);
            this.fieldType = fieldType;
            this.periodType = periodType;
        }

        @Override
        public DurationFieldType getFieldType() {
            return fieldType;
        }

        @Override
        public PeriodType getPeriodType() {
            return periodType;
        }
    }

    private TestSingleFieldPeriod period;

    @Before
    public void setUp() {
        period = new TestSingleFieldPeriod(5, DurationFieldType.days(), PeriodType.days());
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
        TestSingleFieldPeriod samePeriod = new TestSingleFieldPeriod(5, DurationFieldType.days(), PeriodType.days());
        TestSingleFieldPeriod differentPeriod = new TestSingleFieldPeriod(10, DurationFieldType.days(), PeriodType.days());
        assertTrue(period.equals(samePeriod));
        assertFalse(period.equals(differentPeriod));
        assertFalse(period.equals(null));
        assertFalse(period.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        TestSingleFieldPeriod samePeriod = new TestSingleFieldPeriod(5, DurationFieldType.days(), PeriodType.days());
        assertEquals(period.hashCode(), samePeriod.hashCode());
    }

    @Test
    public void testCompareTo() {
        TestSingleFieldPeriod smallerPeriod = new TestSingleFieldPeriod(3, DurationFieldType.days(), PeriodType.days());
        TestSingleFieldPeriod largerPeriod = new TestSingleFieldPeriod(10, DurationFieldType.days(), PeriodType.days());
        assertTrue(period.compareTo(smallerPeriod) > 0);
        assertTrue(period.compareTo(largerPeriod) < 0);
        assertTrue(period.compareTo(period) == 0);
    }

    @Test(expected = ClassCastException.class)
    public void testCompareToDifferentClass() {
        BaseSingleFieldPeriod differentClassPeriod = new BaseSingleFieldPeriod(5) {
            @Override
            public DurationFieldType getFieldType() {
                return DurationFieldType.hours();
            }

            @Override
            public PeriodType getPeriodType() {
                return PeriodType.hours();
            }
        };
        period.compareTo(differentClassPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadableInstantNull() {
        BaseSingleFieldPeriod.between(null, new Instant(), DurationFieldType.days());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBetweenReadablePartialNull() {
        BaseSingleFieldPeriod.between(null, new LocalDate(), Period.days(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStandardPeriodInNull() {
        BaseSingleFieldPeriod.standardPeriodIn(null, 1000L);
    }
}
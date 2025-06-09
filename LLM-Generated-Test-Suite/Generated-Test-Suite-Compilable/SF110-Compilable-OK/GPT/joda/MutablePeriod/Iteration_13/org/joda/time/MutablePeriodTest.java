package org.joda.time;

import static org.junit.Assert.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.junit.Test;

public class MutablePeriodTest {

    @Test
    public void testParseString() {
        MutablePeriod period = MutablePeriod.parse("P1Y2M3W4DT5H6M7.008S");
        assertEquals(1, period.getYears());
        assertEquals(2, period.getMonths());
        assertEquals(3, period.getWeeks());
        assertEquals(4, period.getDays());
        assertEquals(5, period.getHours());
        assertEquals(6, period.getMinutes());
        assertEquals(7, period.getSeconds());
        assertEquals(8, period.getMillis());
    }

    @Test
    public void testParseStringWithFormatter() {
        PeriodFormatter formatter = ISOPeriodFormat.standard();
        MutablePeriod period = MutablePeriod.parse("P1Y2M3W4DT5H6M7.008S", formatter);
        assertEquals(1, period.getYears());
        assertEquals(2, period.getMonths());
        assertEquals(3, period.getWeeks());
        assertEquals(4, period.getDays());
        assertEquals(5, period.getHours());
        assertEquals(6, period.getMinutes());
        assertEquals(7, period.getSeconds());
        assertEquals(8, period.getMillis());
    }

    @Test
    public void testDefaultConstructor() {
        MutablePeriod period = new MutablePeriod();
        assertEquals(0, period.getYears());
        assertEquals(0, period.getMonths());
        assertEquals(0, period.getWeeks());
        assertEquals(0, period.getDays());
        assertEquals(0, period.getHours());
        assertEquals(0, period.getMinutes());
        assertEquals(0, period.getSeconds());
        assertEquals(0, period.getMillis());
    }

    @Test
    public void testConstructorWithPeriodType() {
        PeriodType periodType = PeriodType.yearMonthDayTime();
        MutablePeriod period = new MutablePeriod(periodType);
        assertEquals(0, period.getYears());
        assertEquals(0, period.getMonths());
        assertEquals(0, period.getDays());
        assertEquals(0, period.getHours());
        assertEquals(0, period.getMinutes());
        assertEquals(0, period.getSeconds());
        assertEquals(0, period.getMillis());
    }

    @Test
    public void testConstructorWithInts() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4);
        assertEquals(0, period.getYears());
        assertEquals(0, period.getMonths());
        assertEquals(0, period.getWeeks());
        assertEquals(0, period.getDays());
        assertEquals(1, period.getHours());
        assertEquals(2, period.getMinutes());
        assertEquals(3, period.getSeconds());
        assertEquals(4, period.getMillis());
    }

    @Test
    public void testConstructorWithAllInts() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(1, period.getYears());
        assertEquals(2, period.getMonths());
        assertEquals(3, period.getWeeks());
        assertEquals(4, period.getDays());
        assertEquals(5, period.getHours());
        assertEquals(6, period.getMinutes());
        assertEquals(7, period.getSeconds());
        assertEquals(8, period.getMillis());
    }

    @Test
    public void testConstructorWithLong() {
        MutablePeriod period = new MutablePeriod(1000L);
        assertEquals(0, period.getYears());
        assertEquals(0, period.getMonths());
        assertEquals(0, period.getWeeks());
        assertEquals(0, period.getDays());
        assertEquals(0, period.getHours());
        assertEquals(0, period.getMinutes());
        assertEquals(1, period.getSeconds());
        assertEquals(0, period.getMillis());
    }

    @Test
    public void testClear() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        period.clear();
        assertEquals(0, period.getYears());
        assertEquals(0, period.getMonths());
        assertEquals(0, period.getWeeks());
        assertEquals(0, period.getDays());
        assertEquals(0, period.getHours());
        assertEquals(0, period.getMinutes());
        assertEquals(0, period.getSeconds());
        assertEquals(0, period.getMillis());
    }

    @Test
    public void testSetValue() {
        MutablePeriod period = new MutablePeriod();
        period.setValue(0, 5);
        assertEquals(5, period.getYears());
    }

    @Test
    public void testSetField() {
        MutablePeriod period = new MutablePeriod();
        period.set(DurationFieldType.years(), 5);
        assertEquals(5, period.getYears());
    }

    @Test
    public void testSetPeriodWithReadablePeriod() {
        MutablePeriod period = new MutablePeriod();
        period.setPeriod(new Period(1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(1, period.getYears());
        assertEquals(2, period.getMonths());
        assertEquals(3, period.getWeeks());
        assertEquals(4, period.getDays());
        assertEquals(5, period.getHours());
        assertEquals(6, period.getMinutes());
        assertEquals(7, period.getSeconds());
        assertEquals(8, period.getMillis());
    }

    @Test
    public void testAddField() {
        MutablePeriod period = new MutablePeriod();
        period.add(DurationFieldType.years(), 5);
        assertEquals(5, period.getYears());
    }

    @Test
    public void testAddPeriod() {
        MutablePeriod period = new MutablePeriod();
        period.add(new Period(1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(1, period.getYears());
        assertEquals(2, period.getMonths());
        assertEquals(3, period.getWeeks());
        assertEquals(4, period.getDays());
        assertEquals(5, period.getHours());
        assertEquals(6, period.getMinutes());
        assertEquals(7, period.getSeconds());
        assertEquals(8, period.getMillis());
    }

    @Test
    public void testCopy() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        MutablePeriod copy = period.copy();
        assertEquals(period, copy);
    }

    @Test
    public void testClone() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        MutablePeriod clone = (MutablePeriod) period.clone();
        assertEquals(period, clone);
    }
}
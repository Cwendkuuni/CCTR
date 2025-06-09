package org.joda.time;

import static org.junit.Assert.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.junit.Before;
import org.junit.Test;

public class MutablePeriodTest {

    private MutablePeriod mutablePeriod;

    @Before
    public void setUp() {
        mutablePeriod = new MutablePeriod();
    }

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
    public void testConstructorWithPeriodType() {
        PeriodType periodType = PeriodType.yearMonthDayTime();
        MutablePeriod period = new MutablePeriod(periodType);
        assertEquals(periodType, period.getPeriodType());
    }

    @Test
    public void testConstructorWithInts() {
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
        mutablePeriod.setYears(1);
        mutablePeriod.setMonths(2);
        mutablePeriod.clear();
        assertEquals(0, mutablePeriod.getYears());
        assertEquals(0, mutablePeriod.getMonths());
    }

    @Test
    public void testSetValue() {
        mutablePeriod.setValue(0, 5);
        assertEquals(5, mutablePeriod.getYears());
    }

    @Test
    public void testSetField() {
        mutablePeriod.set(DurationFieldType.years(), 3);
        assertEquals(3, mutablePeriod.getYears());
    }

    @Test
    public void testSetPeriodReadablePeriod() {
        MutablePeriod period = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        mutablePeriod.setPeriod(period);
        assertEquals(1, mutablePeriod.getYears());
        assertEquals(2, mutablePeriod.getMonths());
    }

    @Test
    public void testSetPeriodInts() {
        mutablePeriod.setPeriod(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(1, mutablePeriod.getYears());
        assertEquals(2, mutablePeriod.getMonths());
    }

    @Test
    public void testAddField() {
        mutablePeriod.add(DurationFieldType.years(), 2);
        assertEquals(2, mutablePeriod.getYears());
    }

    @Test
    public void testAddPeriod() {
        MutablePeriod period = new MutablePeriod(1, 1, 1, 1, 1, 1, 1, 1);
        mutablePeriod.add(period);
        assertEquals(1, mutablePeriod.getYears());
        assertEquals(1, mutablePeriod.getMonths());
    }

    @Test
    public void testAddInts() {
        mutablePeriod.add(1, 1, 1, 1, 1, 1, 1, 1);
        assertEquals(1, mutablePeriod.getYears());
        assertEquals(1, mutablePeriod.getMonths());
    }

    @Test
    public void testGetYears() {
        mutablePeriod.setYears(5);
        assertEquals(5, mutablePeriod.getYears());
    }

    @Test
    public void testGetMonths() {
        mutablePeriod.setMonths(5);
        assertEquals(5, mutablePeriod.getMonths());
    }

    @Test
    public void testGetWeeks() {
        mutablePeriod.setWeeks(5);
        assertEquals(5, mutablePeriod.getWeeks());
    }

    @Test
    public void testGetDays() {
        mutablePeriod.setDays(5);
        assertEquals(5, mutablePeriod.getDays());
    }

    @Test
    public void testGetHours() {
        mutablePeriod.setHours(5);
        assertEquals(5, mutablePeriod.getHours());
    }

    @Test
    public void testGetMinutes() {
        mutablePeriod.setMinutes(5);
        assertEquals(5, mutablePeriod.getMinutes());
    }

    @Test
    public void testGetSeconds() {
        mutablePeriod.setSeconds(5);
        assertEquals(5, mutablePeriod.getSeconds());
    }

    @Test
    public void testGetMillis() {
        mutablePeriod.setMillis(5);
        assertEquals(5, mutablePeriod.getMillis());
    }

    @Test
    public void testSetYears() {
        mutablePeriod.setYears(10);
        assertEquals(10, mutablePeriod.getYears());
    }

    @Test
    public void testAddYears() {
        mutablePeriod.addYears(10);
        assertEquals(10, mutablePeriod.getYears());
    }

    @Test
    public void testSetMonths() {
        mutablePeriod.setMonths(10);
        assertEquals(10, mutablePeriod.getMonths());
    }

    @Test
    public void testAddMonths() {
        mutablePeriod.addMonths(10);
        assertEquals(10, mutablePeriod.getMonths());
    }

    @Test
    public void testSetWeeks() {
        mutablePeriod.setWeeks(10);
        assertEquals(10, mutablePeriod.getWeeks());
    }

    @Test
    public void testAddWeeks() {
        mutablePeriod.addWeeks(10);
        assertEquals(10, mutablePeriod.getWeeks());
    }

    @Test
    public void testSetDays() {
        mutablePeriod.setDays(10);
        assertEquals(10, mutablePeriod.getDays());
    }

    @Test
    public void testAddDays() {
        mutablePeriod.addDays(10);
        assertEquals(10, mutablePeriod.getDays());
    }

    @Test
    public void testSetHours() {
        mutablePeriod.setHours(10);
        assertEquals(10, mutablePeriod.getHours());
    }

    @Test
    public void testAddHours() {
        mutablePeriod.addHours(10);
        assertEquals(10, mutablePeriod.getHours());
    }

    @Test
    public void testSetMinutes() {
        mutablePeriod.setMinutes(10);
        assertEquals(10, mutablePeriod.getMinutes());
    }

    @Test
    public void testAddMinutes() {
        mutablePeriod.addMinutes(10);
        assertEquals(10, mutablePeriod.getMinutes());
    }

    @Test
    public void testSetSeconds() {
        mutablePeriod.setSeconds(10);
        assertEquals(10, mutablePeriod.getSeconds());
    }

    @Test
    public void testAddSeconds() {
        mutablePeriod.addSeconds(10);
        assertEquals(10, mutablePeriod.getSeconds());
    }

    @Test
    public void testSetMillis() {
        mutablePeriod.setMillis(10);
        assertEquals(10, mutablePeriod.getMillis());
    }

    @Test
    public void testAddMillis() {
        mutablePeriod.addMillis(10);
        assertEquals(10, mutablePeriod.getMillis());
    }

    @Test
    public void testCopy() {
        MutablePeriod copy = mutablePeriod.copy();
        assertEquals(mutablePeriod, copy);
    }

    @Test
    public void testClone() {
        MutablePeriod clone = (MutablePeriod) mutablePeriod.clone();
        assertEquals(mutablePeriod, clone);
    }
}
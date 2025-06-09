package org.apache.commons.lang3.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TimeZone;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;

public class DurationFormatUtilsTest {

    @Test
    public void testFormatDurationHMS() {
        assertEquals("0:00:00.000", DurationFormatUtils.formatDurationHMS(0));
        assertEquals("0:00:01.000", DurationFormatUtils.formatDurationHMS(1000));
        assertEquals("1:00:00.000", DurationFormatUtils.formatDurationHMS(3600000));
        assertEquals("1:01:01.001", DurationFormatUtils.formatDurationHMS(3661001));
    }

    @Test
    public void testFormatDurationISO() {
        assertEquals("P0Y0M0DT0H0M0.000S", DurationFormatUtils.formatDurationISO(0));
        assertEquals("P0Y0M0DT0H0M1.000S", DurationFormatUtils.formatDurationISO(1000));
        assertEquals("P0Y0M0DT1H0M0.000S", DurationFormatUtils.formatDurationISO(3600000));
        assertEquals("P0Y0M0DT1H1M1.001S", DurationFormatUtils.formatDurationISO(3661001));
    }

    @Test
    public void testFormatDuration() {
        assertEquals("0:00:00.000", DurationFormatUtils.formatDuration(0, "H:mm:ss.SSS"));
        assertEquals("00:00:01", DurationFormatUtils.formatDuration(1000, "HH:mm:ss"));
        assertEquals("01:00:00", DurationFormatUtils.formatDuration(3600000, "HH:mm:ss"));
        assertEquals("01:01:01", DurationFormatUtils.formatDuration(3661001, "HH:mm:ss"));
    }

    @Test
    public void testFormatDurationWithPadding() {
        assertEquals("000:00:00.000", DurationFormatUtils.formatDuration(0, "HHH:mm:ss.SSS", true));
        assertEquals("000:00:01", DurationFormatUtils.formatDuration(1000, "HHH:mm:ss", true));
        assertEquals("001:00:00", DurationFormatUtils.formatDuration(3600000, "HHH:mm:ss", true));
        assertEquals("001:01:01", DurationFormatUtils.formatDuration(3661001, "HHH:mm:ss", true));
    }

    @Test
    public void testFormatDurationWords() {
        assertEquals("0 days 0 hours 0 minutes 0 seconds", DurationFormatUtils.formatDurationWords(0, false, false));
        assertEquals("1 second", DurationFormatUtils.formatDurationWords(1000, true, true));
        assertEquals("1 hour 1 minute 1 second", DurationFormatUtils.formatDurationWords(3661000, true, true));
        assertEquals("1 day 1 hour 1 minute 1 second", DurationFormatUtils.formatDurationWords(90061000, true, true));
    }

    @Test
    public void testFormatPeriodISO() {
        long startMillis = 0;
        long endMillis = 3661001;
        assertEquals("P0Y0M0DT1H1M1.001S", DurationFormatUtils.formatPeriodISO(startMillis, endMillis));
    }

    @Test
    public void testFormatPeriod() {
        long startMillis = 0;
        long endMillis = 3661001;
        assertEquals("01:01:01", DurationFormatUtils.formatPeriod(startMillis, endMillis, "HH:mm:ss"));
        assertEquals("1 hour 1 minute 1 second", DurationFormatUtils.formatPeriod(startMillis, endMillis, "H' hour 'm' minute 's' second'"));
    }

    @Test
    public void testFormatPeriodWithPadding() {
        long startMillis = 0;
        long endMillis = 3661001;
        assertEquals("001:01:01", DurationFormatUtils.formatPeriod(startMillis, endMillis, "HHH:mm:ss", true, TimeZone.getDefault()));
    }
}
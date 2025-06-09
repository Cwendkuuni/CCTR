package com.google.gson.internal.bind.util;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.junit.Test;
import org.junit.Before;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class ISO8601UtilsTest {

    private Date testDate;
    private TimeZone defaultTimeZone;

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.OCTOBER, 10, 15, 30, 45);
        calendar.set(Calendar.MILLISECOND, 123);
        testDate = calendar.getTime();
        defaultTimeZone = TimeZone.getDefault();
    }

    @Test
    public void testFormatWithoutMillis() {
        String formatted = ISO8601Utils.format(testDate);
        assertEquals("2023-10-10T15:30:45Z", formatted);
    }

    @Test
    public void testFormatWithMillis() {
        String formatted = ISO8601Utils.format(testDate, true);
        assertEquals("2023-10-10T15:30:45.123Z", formatted);
    }

    @Test
    public void testFormatWithTimeZone() {
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formatted = ISO8601Utils.format(testDate, true, tz);
        assertEquals("2023-10-10T17:30:45.123+02:00", formatted);
    }

    @Test
    public void testParseBasicFormat() throws ParseException {
        String dateStr = "20231010T153045Z";
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = ISO8601Utils.parse(dateStr, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testParseExtendedFormat() throws ParseException {
        String dateStr = "2023-10-10T15:30:45Z";
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = ISO8601Utils.parse(dateStr, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testParseWithMillis() throws ParseException {
        String dateStr = "2023-10-10T15:30:45.123Z";
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = ISO8601Utils.parse(dateStr, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testParseWithTimeZone() throws ParseException {
        String dateStr = "2023-10-10T17:30:45.123+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = ISO8601Utils.parse(dateStr, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidFormat() throws ParseException {
        String dateStr = "2023-10-10 15:30:45";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidTimeZone() throws ParseException {
        String dateStr = "2023-10-10T15:30:45.123+25:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test
    public void testParseLeapSecond() throws ParseException {
        String dateStr = "2023-10-10T15:30:60Z";
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = ISO8601Utils.parse(dateStr, pos);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.OCTOBER, 10, 15, 31, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(calendar.getTime(), parsedDate);
    }
}
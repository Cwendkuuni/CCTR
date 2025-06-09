package com.google.gson.internal.bind.util;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.junit.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class ISO8601UtilsTest {

    @Test
    public void testFormatWithoutMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.OCTOBER, 10, 15, 30, 45);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

        String formatted = ISO8601Utils.format(date);
        assertEquals("2023-10-10T15:30:45Z", formatted);
    }

    @Test
    public void testFormatWithMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.OCTOBER, 10, 15, 30, 45);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();

        String formatted = ISO8601Utils.format(date, true);
        assertEquals("2023-10-10T15:30:45.123Z", formatted);
    }

    @Test
    public void testFormatWithTimeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        calendar.set(2023, Calendar.OCTOBER, 10, 15, 30, 45);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

        String formatted = ISO8601Utils.format(date, false, TimeZone.getTimeZone("GMT+02:00"));
        assertEquals("2023-10-10T15:30:45+02:00", formatted);
    }

    @Test
    public void testParseBasicFormat() throws ParseException {
        String dateStr = "20231010T153045Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);

        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(45, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testParseExtendedFormat() throws ParseException {
        String dateStr = "2023-10-10T15:30:45Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);

        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(45, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testParseWithMillis() throws ParseException {
        String dateStr = "2023-10-10T15:30:45.123Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);

        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(45, calendar.get(Calendar.SECOND));
        assertEquals(123, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testParseWithTimeZone() throws ParseException {
        String dateStr = "2023-10-10T15:30:45+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        calendar.setTime(date);

        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(45, calendar.get(Calendar.SECOND));
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidFormat() throws ParseException {
        String dateStr = "2023-10-10T15:30:45";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidTimeZone() throws ParseException {
        String dateStr = "2023-10-10T15:30:45+25:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidDate() throws ParseException {
        String dateStr = "2023-02-30T15:30:45Z";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }
}
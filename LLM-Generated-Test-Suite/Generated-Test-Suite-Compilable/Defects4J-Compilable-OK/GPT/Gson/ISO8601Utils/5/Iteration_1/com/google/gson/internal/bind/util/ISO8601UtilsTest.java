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
        calendar.set(2023, Calendar.OCTOBER, 5, 14, 30, 0);
        Date date = calendar.getTime();
        String formattedDate = ISO8601Utils.format(date);
        assertEquals("2023-10-05T14:30:00Z", formattedDate);
    }

    @Test
    public void testFormatWithMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.OCTOBER, 5, 14, 30, 0);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();
        String formattedDate = ISO8601Utils.format(date, true);
        assertEquals("2023-10-05T14:30:00.123Z", formattedDate);
    }

    @Test
    public void testFormatWithTimeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        calendar.set(2023, Calendar.OCTOBER, 5, 14, 30, 0);
        Date date = calendar.getTime();
        String formattedDate = ISO8601Utils.format(date, false, TimeZone.getTimeZone("GMT+02:00"));
        assertEquals("2023-10-05T14:30:00+02:00", formattedDate);
    }

    @Test
    public void testParseBasicFormat() throws ParseException {
        String dateString = "20231005T143000Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateString, pos);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(5, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(14, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testParseExtendedFormat() throws ParseException {
        String dateString = "2023-10-05T14:30:00Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateString, pos);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(5, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(14, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testParseWithMillis() throws ParseException {
        String dateString = "2023-10-05T14:30:00.123Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateString, pos);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(5, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(14, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(123, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testParseWithTimeZone() throws ParseException {
        String dateString = "2023-10-05T14:30:00+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateString, pos);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        calendar.setTime(date);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(5, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(14, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidFormat() throws ParseException {
        String dateString = "2023-10-05 14:30:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateString, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidDate() throws ParseException {
        String dateString = "2023-02-30T14:30:00Z";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateString, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidTimeZone() throws ParseException {
        String dateString = "2023-10-05T14:30:00+25:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateString, pos);
    }
}
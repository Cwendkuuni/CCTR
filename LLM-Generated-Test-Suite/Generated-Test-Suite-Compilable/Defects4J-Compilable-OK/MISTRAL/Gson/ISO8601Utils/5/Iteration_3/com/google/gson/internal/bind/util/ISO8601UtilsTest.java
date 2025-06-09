package com.google.gson.internal.bind.util;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601UtilsTest {

    @Test
    public void testFormatDate() {
        Date date = new Date(1609459200000L); // 2021-01-01T00:00:00Z
        String formattedDate = ISO8601Utils.format(date);
        assertEquals("2021-01-01T00:00:00Z", formattedDate);
    }

    @Test
    public void testFormatDateWithMillis() {
        Date date = new Date(1609459200123L); // 2021-01-01T00:00:00.123Z
        String formattedDate = ISO8601Utils.format(date, true);
        assertEquals("2021-01-01T00:00:00.123Z", formattedDate);
    }

    @Test
    public void testFormatDateWithTimeZone() {
        Date date = new Date(1609459200000L); // 2021-01-01T00:00:00Z
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formattedDate = ISO8601Utils.format(date, false, tz);
        assertEquals("2021-01-01T02:00:00+02:00", formattedDate);
    }

    @Test
    public void testFormatDateWithMillisAndTimeZone() {
        Date date = new Date(1609459200123L); // 2021-01-01T00:00:00.123Z
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formattedDate = ISO8601Utils.format(date, true, tz);
        assertEquals("2021-01-01T02:00:00.123+02:00", formattedDate);
    }

    @Test
    public void testParseDate() throws ParseException {
        String dateStr = "2021-01-01T00:00:00Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(1609459200000L, date.getTime());
    }

    @Test
    public void testParseDateWithMillis() throws ParseException {
        String dateStr = "2021-01-01T00:00:00.123Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(1609459200123L, date.getTime());
    }

    @Test
    public void testParseDateWithTimeZone() throws ParseException {
        String dateStr = "2021-01-01T02:00:00+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(1609459200000L, date.getTime());
    }

    @Test
    public void testParseDateWithMillisAndTimeZone() throws ParseException {
        String dateStr = "2021-01-01T02:00:00.123+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(1609459200123L, date.getTime());
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidDate() throws ParseException {
        String dateStr = "invalid-date";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseDateWithoutTimeZone() throws ParseException {
        String dateStr = "2021-01-01T00:00:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseDateWithInvalidTimeZone() throws ParseException {
        String dateStr = "2021-01-01T00:00:00X";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }
}
package com.google.gson.internal.bind.util;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.junit.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class ISO8601UtilsTest {

    @Test
    public void testFormatWithoutMillisUTC() {
        Date date = new Date(0); // Epoch time
        String formatted = ISO8601Utils.format(date);
        assertEquals("1970-01-01T00:00:00Z", formatted);
    }

    @Test
    public void testFormatWithMillisUTC() {
        Date date = new Date(0); // Epoch time
        String formatted = ISO8601Utils.format(date, true);
        assertEquals("1970-01-01T00:00:00.000Z", formatted);
    }

    @Test
    public void testFormatWithMillisAndTimeZone() {
        Date date = new Date(0); // Epoch time
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formatted = ISO8601Utils.format(date, true, tz);
        assertEquals("1970-01-01T02:00:00.000+02:00", formatted);
    }

    @Test
    public void testFormatWithoutMillisAndTimeZone() {
        Date date = new Date(0); // Epoch time
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formatted = ISO8601Utils.format(date, false, tz);
        assertEquals("1970-01-01T02:00:00+02:00", formatted);
    }

    @Test
    public void testParseBasicDate() throws ParseException {
        String dateStr = "1970-01-01";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(0), date);
    }

    @Test
    public void testParseDateTimeUTC() throws ParseException {
        String dateStr = "1970-01-01T00:00:00Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(0), date);
    }

    @Test
    public void testParseDateTimeWithMillisUTC() throws ParseException {
        String dateStr = "1970-01-01T00:00:00.000Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(0), date);
    }

    @Test
    public void testParseDateTimeWithTimeZone() throws ParseException {
        String dateStr = "1970-01-01T02:00:00+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(0), date);
    }

    @Test
    public void testParseDateTimeWithMillisAndTimeZone() throws ParseException {
        String dateStr = "1970-01-01T02:00:00.000+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(0), date);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidDate() throws ParseException {
        String dateStr = "invalid-date";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidTimeZone() throws ParseException {
        String dateStr = "1970-01-01T00:00:00X";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test(expected = ParseException.class)
    public void testParseIncompleteDate() throws ParseException {
        String dateStr = "1970-01";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test
    public void testParseLeapSecond() throws ParseException {
        String dateStr = "1970-01-01T00:00:60Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(new Date(59 * 1000), date);
    }
}
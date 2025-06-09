package com.google.gson.internal.bind.util;

import static org.junit.Assert.*;
import org.junit.Test;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;
import com.google.gson.internal.bind.util.ISO8601Utils;

public class ISO8601UtilsTest {

    @Test
    public void testFormatWithoutMillis() {
        Date date = new Date(0); // Epoch time
        String formatted = ISO8601Utils.format(date);
        assertEquals("1970-01-01T00:00:00Z", formatted);
    }

    @Test
    public void testFormatWithMillis() {
        Date date = new Date(0); // Epoch time
        String formatted = ISO8601Utils.format(date, true);
        assertEquals("1970-01-01T00:00:00.000Z", formatted);
    }

    @Test
    public void testFormatWithTimeZone() {
        Date date = new Date(0); // Epoch time
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String formatted = ISO8601Utils.format(date, true, tz);
        assertEquals("1970-01-01T02:00:00.000+02:00", formatted);
    }

    @Test
    public void testParseBasicFormat() throws ParseException {
        String dateStr = "1970-01-01T00:00:00Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(0, date.getTime());
    }

    @Test
    public void testParseWithMillis() throws ParseException {
        String dateStr = "1970-01-01T00:00:00.000Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(0, date.getTime());
    }

    @Test
    public void testParseWithTimeZone() throws ParseException {
        String dateStr = "1970-01-01T02:00:00.000+02:00";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(0, date.getTime());
    }

    @Test(expected = ParseException.class)
    public void testParseInvalidFormat() throws ParseException {
        String dateStr = "InvalidDate";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }

    @Test
    public void testParseWithoutTimeComponent() throws ParseException {
        String dateStr = "1970-01-01";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(0, date.getTime());
    }

    @Test
    public void testParseWithLeapSecond() throws ParseException {
        String dateStr = "1970-01-01T00:00:60Z";
        ParsePosition pos = new ParsePosition(0);
        Date date = ISO8601Utils.parse(dateStr, pos);
        assertEquals(59000, date.getTime());
    }

    @Test(expected = ParseException.class)
    public void testParseWithInvalidTimeZone() throws ParseException {
        String dateStr = "1970-01-01T00:00:00.000+25:00";
        ParsePosition pos = new ParsePosition(0);
        ISO8601Utils.parse(dateStr, pos);
    }
}
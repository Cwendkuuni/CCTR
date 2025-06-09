package com.fasterxml.jackson.databind.util;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.util.StdDateFormat;

public class StdDateFormatTest {

    private StdDateFormat stdDateFormat;

    @Before
    public void setUp() {
        stdDateFormat = new StdDateFormat();
    }

    @Test
    public void testDefaultTimeZone() {
        assertEquals(TimeZone.getTimeZone("GMT"), StdDateFormat.getDefaultTimeZone());
    }

    @Test
    public void testWithTimeZone() {
        TimeZone tz = TimeZone.getTimeZone("PST");
        StdDateFormat newFormat = stdDateFormat.withTimeZone(tz);
        assertNotSame(stdDateFormat, newFormat);
        assertEquals(tz, newFormat._timezone);
    }

    @Test
    public void testWithLocale() {
        Locale locale = Locale.FRANCE;
        StdDateFormat newFormat = stdDateFormat.withLocale(locale);
        assertNotSame(stdDateFormat, newFormat);
        assertEquals(locale, newFormat._locale);
    }

    @Test
    public void testClone() {
        StdDateFormat clonedFormat = stdDateFormat.clone();
        assertNotSame(stdDateFormat, clonedFormat);
        assertEquals(stdDateFormat._locale, clonedFormat._locale);
        assertEquals(stdDateFormat._timezone, clonedFormat._timezone);
    }

    @Test
    public void testParseISO8601() throws ParseException {
        String dateStr = "2023-10-01T12:45:30.000Z";
        Date date = stdDateFormat.parse(dateStr);
        assertNotNull(date);
    }

    @Test
    public void testParseRFC1123() throws ParseException {
        String dateStr = "Sun, 01 Oct 2023 12:45:30 GMT";
        Date date = stdDateFormat.parse(dateStr);
        assertNotNull(date);
    }

    @Test
    public void testParsePlainDate() throws ParseException {
        String dateStr = "2023-10-01";
        Date date = stdDateFormat.parse(dateStr);
        assertNotNull(date);
    }

    @Test
    public void testParseInvalidDate() {
        String dateStr = "Invalid Date String";
        try {
            stdDateFormat.parse(dateStr);
            fail("Expected ParseException");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Can not parse date"));
        }
    }

    @Test
    public void testFormat() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        stdDateFormat.format(date, buffer, new java.text.FieldPosition(0));
        assertTrue(buffer.length() > 0);
    }

    @Test
    public void testToString() {
        String str = stdDateFormat.toString();
        assertTrue(str.contains("DateFormat"));
        assertTrue(str.contains("locale"));
    }
}
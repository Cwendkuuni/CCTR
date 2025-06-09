package org.apache.commons.lang3.time;

import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class FastDateFormatTest {

    private FastDateFormat fastDateFormat;
    private final String pattern = "yyyy-MM-dd";
    private final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private final Locale locale = Locale.US;

    @Before
    public void setUp() {
        fastDateFormat = FastDateFormat.getInstance(pattern, timeZone, locale);
    }

    @Test
    public void testGetInstanceDefault() {
        assertNotNull(FastDateFormat.getInstance());
    }

    @Test
    public void testGetInstanceWithPattern() {
        FastDateFormat format = FastDateFormat.getInstance(pattern);
        assertNotNull(format);
        assertEquals(pattern, format.getPattern());
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        FastDateFormat format = FastDateFormat.getInstance(pattern, timeZone);
        assertNotNull(format);
        assertEquals(timeZone, format.getTimeZone());
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        FastDateFormat format = FastDateFormat.getInstance(pattern, locale);
        assertNotNull(format);
        assertEquals(locale, format.getLocale());
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        FastDateFormat format = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertNotNull(format);
        assertEquals(pattern, format.getPattern());
        assertEquals(timeZone, format.getTimeZone());
        assertEquals(locale, format.getLocale());
    }

    @Test
    public void testFormatLong() {
        long millis = System.currentTimeMillis();
        String formatted = fastDateFormat.format(millis);
        assertNotNull(formatted);
    }

    @Test
    public void testFormatDate() {
        Date date = new Date();
        String formatted = fastDateFormat.format(date);
        assertNotNull(formatted);
    }

    @Test
    public void testFormatCalendar() {
        Calendar calendar = Calendar.getInstance();
        String formatted = fastDateFormat.format(calendar);
        assertNotNull(formatted);
    }

    @Test
    public void testFormatLongWithBuffer() {
        long millis = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        StringBuffer result = fastDateFormat.format(millis, buffer);
        assertNotNull(result);
        assertEquals(buffer, result);
    }

    @Test
    public void testFormatDateWithBuffer() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        StringBuffer result = fastDateFormat.format(date, buffer);
        assertNotNull(result);
        assertEquals(buffer, result);
    }

    @Test
    public void testFormatCalendarWithBuffer() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        StringBuffer result = fastDateFormat.format(calendar, buffer);
        assertNotNull(result);
        assertEquals(buffer, result);
    }

    @Test
    public void testParse() throws ParseException {
        String dateStr = "2023-10-01";
        Date date = fastDateFormat.parse(dateStr);
        assertNotNull(date);
    }

    @Test
    public void testParseWithPosition() {
        String dateStr = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        Date date = fastDateFormat.parse(dateStr, pos);
        assertNotNull(date);
        assertEquals(dateStr.length(), pos.getIndex());
    }

    @Test
    public void testParseObject() {
        String dateStr = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        Object date = fastDateFormat.parseObject(dateStr, pos);
        assertNotNull(date);
        assertTrue(date instanceof Date);
    }

    @Test
    public void testGetPattern() {
        assertEquals(pattern, fastDateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(timeZone, fastDateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(locale, fastDateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        int estimate = fastDateFormat.getMaxLengthEstimate();
        assertTrue(estimate > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat other = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertTrue(fastDateFormat.equals(other));
    }

    @Test
    public void testHashCode() {
        FastDateFormat other = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertEquals(fastDateFormat.hashCode(), other.hashCode());
    }

    @Test
    public void testToString() {
        String toString = fastDateFormat.toString();
        assertNotNull(toString);
        assertTrue(toString.contains(pattern));
    }
}
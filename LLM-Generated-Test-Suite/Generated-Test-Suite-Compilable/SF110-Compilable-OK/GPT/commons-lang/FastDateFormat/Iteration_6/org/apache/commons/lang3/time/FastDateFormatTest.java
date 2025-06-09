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
        FastDateFormat instance = FastDateFormat.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testGetInstanceWithPattern() {
        FastDateFormat instance = FastDateFormat.getInstance(pattern);
        assertNotNull(instance);
        assertEquals(pattern, instance.getPattern());
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        FastDateFormat instance = FastDateFormat.getInstance(pattern, timeZone);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        FastDateFormat instance = FastDateFormat.getInstance(pattern, locale);
        assertNotNull(instance);
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertNotNull(instance);
        assertEquals(pattern, instance.getPattern());
        assertEquals(timeZone, instance.getTimeZone());
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetDateInstance() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, locale);
        assertNotNull(instance);
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, timeZone);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, timeZone, locale);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetTimeInstance() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, locale);
        assertNotNull(instance);
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, timeZone);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, timeZone, locale);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetDateTimeInstance() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, locale);
        assertNotNull(instance);
        assertEquals(locale, instance.getLocale());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, timeZone);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, timeZone, locale);
        assertNotNull(instance);
        assertEquals(timeZone, instance.getTimeZone());
        assertEquals(locale, instance.getLocale());
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
    }

    @Test
    public void testFormatDateWithBuffer() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        StringBuffer result = fastDateFormat.format(date, buffer);
        assertNotNull(result);
    }

    @Test
    public void testFormatCalendarWithBuffer() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        StringBuffer result = fastDateFormat.format(calendar, buffer);
        assertNotNull(result);
    }

    @Test
    public void testParse() throws ParseException {
        String dateStr = "2023-10-10";
        Date date = fastDateFormat.parse(dateStr);
        assertNotNull(date);
    }

    @Test
    public void testParseWithPosition() {
        String dateStr = "2023-10-10";
        ParsePosition pos = new ParsePosition(0);
        Date date = fastDateFormat.parse(dateStr, pos);
        assertNotNull(date);
    }

    @Test
    public void testParseObject() {
        String dateStr = "2023-10-10";
        ParsePosition pos = new ParsePosition(0);
        Object date = fastDateFormat.parseObject(dateStr, pos);
        assertNotNull(date);
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
        FastDateFormat anotherInstance = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertTrue(fastDateFormat.equals(anotherInstance));
    }

    @Test
    public void testHashCode() {
        FastDateFormat anotherInstance = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertEquals(fastDateFormat.hashCode(), anotherInstance.hashCode());
    }

    @Test
    public void testToString() {
        String str = fastDateFormat.toString();
        assertNotNull(str);
        assertTrue(str.contains(pattern));
    }
}
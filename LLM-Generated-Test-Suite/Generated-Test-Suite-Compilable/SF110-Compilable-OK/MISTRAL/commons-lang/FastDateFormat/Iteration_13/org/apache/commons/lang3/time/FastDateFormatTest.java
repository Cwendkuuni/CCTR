package org.apache.commons.lang3.time;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.*;
import java.text.*;

public class FastDateFormatTest {

    private FastDateFormat fastDateFormat;
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    private static final Locale LOCALE = Locale.US;

    @Before
    public void setUp() {
        fastDateFormat = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
    }

    @After
    public void tearDown() {
        fastDateFormat = null;
    }

    @Test
    public void testGetInstance() {
        assertNotNull(FastDateFormat.getInstance());
    }

    @Test
    public void testGetInstanceWithPattern() {
        assertNotNull(FastDateFormat.getInstance(PATTERN));
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(FastDateFormat.getInstance(PATTERN, TIME_ZONE));
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(FastDateFormat.getInstance(PATTERN, LOCALE));
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL));
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testFormatObject() {
        Date date = new Date();
        StringBuffer buf = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        assertNotNull(fastDateFormat.format(date, buf, pos));
    }

    @Test
    public void testFormatMillis() {
        long millis = System.currentTimeMillis();
        assertNotNull(fastDateFormat.format(millis));
    }

    @Test
    public void testFormatDate() {
        Date date = new Date();
        assertNotNull(fastDateFormat.format(date));
    }

    @Test
    public void testFormatCalendar() {
        Calendar calendar = Calendar.getInstance();
        assertNotNull(fastDateFormat.format(calendar));
    }

    @Test
    public void testFormatMillisStringBuffer() {
        long millis = System.currentTimeMillis();
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(millis, buf));
    }

    @Test
    public void testFormatDateStringBuffer() {
        Date date = new Date();
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(date, buf));
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(calendar, buf));
    }

    @Test
    public void testParse() throws ParseException {
        String dateString = "2023-10-01 12:00:00";
        assertNotNull(fastDateFormat.parse(dateString));
    }

    @Test
    public void testParseWithParsePosition() {
        String dateString = "2023-10-01 12:00:00";
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(fastDateFormat.parse(dateString, pos));
    }

    @Test
    public void testParseObject() {
        String dateString = "2023-10-01 12:00:00";
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(fastDateFormat.parseObject(dateString, pos));
    }

    @Test
    public void testGetPattern() {
        assertEquals(PATTERN, fastDateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(TIME_ZONE, fastDateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(LOCALE, fastDateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(fastDateFormat.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat other = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
        assertTrue(fastDateFormat.equals(other));
    }

    @Test
    public void testHashCode() {
        FastDateFormat other = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
        assertEquals(fastDateFormat.hashCode(), other.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "FastDateFormat[" + PATTERN + "," + LOCALE + "," + TIME_ZONE.getID() + "]";
        assertEquals(expected, fastDateFormat.toString());
    }

    @Test
    public void testApplyRules() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.applyRules(calendar, buf));
    }
}
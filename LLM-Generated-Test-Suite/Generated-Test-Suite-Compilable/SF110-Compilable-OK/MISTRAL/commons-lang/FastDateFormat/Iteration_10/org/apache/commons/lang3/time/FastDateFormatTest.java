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
    private static final Date DATE = new Date();
    private static final Calendar CALENDAR = Calendar.getInstance();

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
    public void testGetDateInstanceWithStyle() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetDateInstanceWithStyleAndLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetDateInstanceWithStyleAndTimeZone() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetDateInstanceWithStyleTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetTimeInstanceWithStyle() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetTimeInstanceWithStyleAndLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetTimeInstanceWithStyleAndTimeZone() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetTimeInstanceWithStyleTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetDateTimeInstanceWithDateStyleAndTimeStyle() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL));
    }

    @Test
    public void testGetDateTimeInstanceWithDateStyleTimeStyleAndLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, LOCALE));
    }

    @Test
    public void testGetDateTimeInstanceWithDateStyleTimeStyleAndTimeZone() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TIME_ZONE));
    }

    @Test
    public void testGetDateTimeInstanceWithDateStyleTimeStyleTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TIME_ZONE, LOCALE));
    }

    @Test
    public void testFormatObject() {
        StringBuffer buf = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        assertNotNull(fastDateFormat.format(DATE, buf, pos));
    }

    @Test
    public void testFormatMillis() {
        assertNotNull(fastDateFormat.format(DATE.getTime()));
    }

    @Test
    public void testFormatDate() {
        assertNotNull(fastDateFormat.format(DATE));
    }

    @Test
    public void testFormatCalendar() {
        assertNotNull(fastDateFormat.format(CALENDAR));
    }

    @Test
    public void testFormatMillisStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(DATE.getTime(), buf));
    }

    @Test
    public void testFormatDateStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(DATE, buf));
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(CALENDAR, buf));
    }

    @Test
    public void testParseString() throws ParseException {
        String dateString = fastDateFormat.format(DATE);
        assertNotNull(fastDateFormat.parse(dateString));
    }

    @Test
    public void testParseStringParsePosition() {
        String dateString = fastDateFormat.format(DATE);
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(fastDateFormat.parse(dateString, pos));
    }

    @Test
    public void testParseObjectStringParsePosition() {
        String dateString = fastDateFormat.format(DATE);
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
        FastDateFormat anotherFormat = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
        assertTrue(fastDateFormat.equals(anotherFormat));
    }

    @Test
    public void testHashCode() {
        FastDateFormat anotherFormat = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
        assertEquals(fastDateFormat.hashCode(), anotherFormat.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "FastDateFormat[" + PATTERN + "," + LOCALE + "," + TIME_ZONE.getID() + "]";
        assertEquals(expected, fastDateFormat.toString());
    }

    @Test
    public void testApplyRules() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.applyRules(CALENDAR, buf));
    }
}
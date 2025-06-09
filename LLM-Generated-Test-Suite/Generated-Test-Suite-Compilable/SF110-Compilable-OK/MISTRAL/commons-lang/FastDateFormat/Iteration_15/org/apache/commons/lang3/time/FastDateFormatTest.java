package org.apache.commons.lang3.time;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.*;
import java.text.*;

public class FastDateFormatTest {

    private FastDateFormat fastDateFormat;

    @Before
    public void setUp() {
        fastDateFormat = FastDateFormat.getInstance();
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
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd"));
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", Locale.US));
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US));
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.US));
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("UTC"), Locale.US));
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, Locale.US));
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("UTC"), Locale.US));
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL));
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.US));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("UTC"), Locale.US));
    }

    @Test
    public void testFormatObject() {
        StringBuffer buf = new StringBuffer();
        FieldPosition pos = new FieldPosition(DateFormat.YEAR_FIELD);
        assertNotNull(fastDateFormat.format(new Date(), buf, pos));
    }

    @Test
    public void testFormatMillis() {
        assertNotNull(fastDateFormat.format(System.currentTimeMillis()));
    }

    @Test
    public void testFormatDate() {
        assertNotNull(fastDateFormat.format(new Date()));
    }

    @Test
    public void testFormatCalendar() {
        assertNotNull(fastDateFormat.format(Calendar.getInstance()));
    }

    @Test
    public void testFormatMillisStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(System.currentTimeMillis(), buf));
    }

    @Test
    public void testFormatDateStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(new Date(), buf));
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.format(Calendar.getInstance(), buf));
    }

    @Test
    public void testParse() throws ParseException {
        Date date = fastDateFormat.parse("2023-10-01");
        assertNotNull(date);
    }

    @Test
    public void testParseWithParsePosition() {
        ParsePosition pos = new ParsePosition(0);
        Date date = fastDateFormat.parse("2023-10-01", pos);
        assertNotNull(date);
    }

    @Test
    public void testParseObject() {
        ParsePosition pos = new ParsePosition(0);
        Object obj = fastDateFormat.parseObject("2023-10-01", pos);
        assertNotNull(obj);
    }

    @Test
    public void testGetPattern() {
        assertNotNull(fastDateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertNotNull(fastDateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertNotNull(fastDateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(fastDateFormat.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat other = FastDateFormat.getInstance();
        assertTrue(fastDateFormat.equals(other));
    }

    @Test
    public void testHashCode() {
        FastDateFormat other = FastDateFormat.getInstance();
        assertEquals(fastDateFormat.hashCode(), other.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(fastDateFormat.toString());
    }

    @Test
    public void testApplyRules() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(fastDateFormat.applyRules(Calendar.getInstance(), buf));
    }
}
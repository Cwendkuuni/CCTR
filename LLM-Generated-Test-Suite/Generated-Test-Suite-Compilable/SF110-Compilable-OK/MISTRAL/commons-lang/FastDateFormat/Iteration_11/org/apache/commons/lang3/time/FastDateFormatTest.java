package org.apache.commons.lang3.time;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.*;
import java.text.*;

public class FastDateFormatTest {

    private FastDateFormat dateFormat;
    private Date testDate;
    private Calendar testCalendar;

    @Before
    public void setUp() {
        dateFormat = FastDateFormat.getInstance();
        testDate = new Date();
        testCalendar = Calendar.getInstance();
    }

    @After
    public void tearDown() {
        dateFormat = null;
        testDate = null;
        testCalendar = null;
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
        FieldPosition pos = new FieldPosition(0);
        assertNotNull(dateFormat.format(testDate, buf, pos));
    }

    @Test
    public void testFormatMillis() {
        assertNotNull(dateFormat.format(testDate.getTime()));
    }

    @Test
    public void testFormatDate() {
        assertNotNull(dateFormat.format(testDate));
    }

    @Test
    public void testFormatCalendar() {
        assertNotNull(dateFormat.format(testCalendar));
    }

    @Test
    public void testFormatMillisWithBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(dateFormat.format(testDate.getTime(), buf));
    }

    @Test
    public void testFormatDateWithBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(dateFormat.format(testDate, buf));
    }

    @Test
    public void testFormatCalendarWithBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(dateFormat.format(testCalendar, buf));
    }

    @Test
    public void testParse() throws ParseException {
        String dateStr = dateFormat.format(testDate);
        assertNotNull(dateFormat.parse(dateStr));
    }

    @Test
    public void testParseWithPosition() {
        String dateStr = dateFormat.format(testDate);
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(dateFormat.parse(dateStr, pos));
    }

    @Test
    public void testParseObject() {
        String dateStr = dateFormat.format(testDate);
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(dateFormat.parseObject(dateStr, pos));
    }

    @Test
    public void testGetPattern() {
        assertNotNull(dateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertNotNull(dateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertNotNull(dateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(dateFormat.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat anotherFormat = FastDateFormat.getInstance();
        assertTrue(dateFormat.equals(anotherFormat));
    }

    @Test
    public void testHashCode() {
        FastDateFormat anotherFormat = FastDateFormat.getInstance();
        assertEquals(dateFormat.hashCode(), anotherFormat.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(dateFormat.toString());
    }

    @Test
    public void testApplyRules() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(dateFormat.applyRules(testCalendar, buf));
    }
}
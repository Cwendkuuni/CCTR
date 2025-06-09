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
    private String testPattern;
    private TimeZone testTimeZone;
    private Locale testLocale;

    @Before
    public void setUp() {
        testDate = new Date();
        testCalendar = Calendar.getInstance();
        testPattern = "yyyy-MM-dd HH:mm:ss";
        testTimeZone = TimeZone.getTimeZone("GMT");
        testLocale = Locale.US;
        dateFormat = FastDateFormat.getInstance(testPattern, testTimeZone, testLocale);
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
        FastDateFormat instance = FastDateFormat.getInstance(testPattern);
        assertEquals(testPattern, instance.getPattern());
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        FastDateFormat instance = FastDateFormat.getInstance(testPattern, testTimeZone);
        assertEquals(testPattern, instance.getPattern());
        assertEquals(testTimeZone, instance.getTimeZone());
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        FastDateFormat instance = FastDateFormat.getInstance(testPattern, testLocale);
        assertEquals(testPattern, instance.getPattern());
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getInstance(testPattern, testTimeZone, testLocale);
        assertEquals(testPattern, instance.getPattern());
        assertEquals(testTimeZone, instance.getTimeZone());
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetDateInstance() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, testLocale);
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, testTimeZone);
        assertEquals(testTimeZone, instance.getTimeZone());
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getDateInstance(FastDateFormat.FULL, testTimeZone, testLocale);
        assertEquals(testTimeZone, instance.getTimeZone());
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetTimeInstance() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, testLocale);
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, testTimeZone);
        assertEquals(testTimeZone, instance.getTimeZone());
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getTimeInstance(FastDateFormat.FULL, testTimeZone, testLocale);
        assertEquals(testTimeZone, instance.getTimeZone());
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetDateTimeInstance() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
        assertNotNull(instance);
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, testLocale);
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, testTimeZone);
        assertEquals(testTimeZone, instance.getTimeZone());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        FastDateFormat instance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, testTimeZone, testLocale);
        assertEquals(testTimeZone, instance.getTimeZone());
        assertEquals(testLocale, instance.getLocale());
    }

    @Test
    public void testFormatObject() {
        StringBuffer buf = new StringBuffer();
        FieldPosition pos = new FieldPosition(DateFormat.YEAR_FIELD);
        String formatted = dateFormat.format(testDate, buf, pos).toString();
        assertNotNull(formatted);
    }

    @Test
    public void testFormatMillis() {
        String formatted = dateFormat.format(testDate.getTime());
        assertNotNull(formatted);
    }

    @Test
    public void testFormatDate() {
        String formatted = dateFormat.format(testDate);
        assertNotNull(formatted);
    }

    @Test
    public void testFormatCalendar() {
        String formatted = dateFormat.format(testCalendar);
        assertNotNull(formatted);
    }

    @Test
    public void testFormatMillisStringBuffer() {
        StringBuffer buf = new StringBuffer();
        String formatted = dateFormat.format(testDate.getTime(), buf).toString();
        assertNotNull(formatted);
    }

    @Test
    public void testFormatDateStringBuffer() {
        StringBuffer buf = new StringBuffer();
        String formatted = dateFormat.format(testDate, buf).toString();
        assertNotNull(formatted);
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        StringBuffer buf = new StringBuffer();
        String formatted = dateFormat.format(testCalendar, buf).toString();
        assertNotNull(formatted);
    }

    @Test
    public void testParse() throws ParseException {
        String dateString = dateFormat.format(testDate);
        Date parsedDate = dateFormat.parse(dateString);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testParseWithPosition() {
        String dateString = dateFormat.format(testDate);
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = dateFormat.parse(dateString, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testParseObject() {
        String dateString = dateFormat.format(testDate);
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = (Date) dateFormat.parseObject(dateString, pos);
        assertEquals(testDate, parsedDate);
    }

    @Test
    public void testGetPattern() {
        assertEquals(testPattern, dateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(testTimeZone, dateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(testLocale, dateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        int maxLength = dateFormat.getMaxLengthEstimate();
        assertTrue(maxLength > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat anotherInstance = FastDateFormat.getInstance(testPattern, testTimeZone, testLocale);
        assertTrue(dateFormat.equals(anotherInstance));
    }

    @Test
    public void testHashCode() {
        FastDateFormat anotherInstance = FastDateFormat.getInstance(testPattern, testTimeZone, testLocale);
        assertEquals(dateFormat.hashCode(), anotherInstance.hashCode());
    }

    @Test
    public void testToString() {
        String toString = dateFormat.toString();
        assertTrue(toString.contains(testPattern));
        assertTrue(toString.contains(testLocale.toString()));
        assertTrue(toString.contains(testTimeZone.getID()));
    }

    @Test
    public void testApplyRules() {
        StringBuffer buf = new StringBuffer();
        String formatted = dateFormat.applyRules(testCalendar, buf).toString();
        assertNotNull(formatted);
    }
}
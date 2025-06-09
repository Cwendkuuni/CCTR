package org.apache.commons.lang3.time;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.*;

public class FastDateFormatTest {

    private FastDateFormat dateFormat;
    private FastDateFormat dateFormatWithPattern;
    private FastDateFormat dateFormatWithPatternAndTimeZone;
    private FastDateFormat dateFormatWithPatternAndLocale;
    private FastDateFormat dateFormatWithPatternTimeZoneAndLocale;
    private FastDateFormat dateInstance;
    private FastDateFormat dateInstanceWithLocale;
    private FastDateFormat dateInstanceWithTimeZone;
    private FastDateFormat dateInstanceWithTimeZoneAndLocale;
    private FastDateFormat timeInstance;
    private FastDateFormat timeInstanceWithLocale;
    private FastDateFormat timeInstanceWithTimeZone;
    private FastDateFormat timeInstanceWithTimeZoneAndLocale;
    private FastDateFormat dateTimeInstance;
    private FastDateFormat dateTimeInstanceWithLocale;
    private FastDateFormat dateTimeInstanceWithTimeZone;
    private FastDateFormat dateTimeInstanceWithTimeZoneAndLocale;

    @Before
    public void setUp() {
        dateFormat = FastDateFormat.getInstance();
        dateFormatWithPattern = FastDateFormat.getInstance("yyyy-MM-dd");
        dateFormatWithPatternAndTimeZone = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"));
        dateFormatWithPatternAndLocale = FastDateFormat.getInstance("yyyy-MM-dd", Locale.FRANCE);
        dateFormatWithPatternTimeZoneAndLocale = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        dateInstance = FastDateFormat.getDateInstance(FastDateFormat.FULL);
        dateInstanceWithLocale = FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.FRANCE);
        dateInstanceWithTimeZone = FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        dateInstanceWithTimeZoneAndLocale = FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        timeInstance = FastDateFormat.getTimeInstance(FastDateFormat.FULL);
        timeInstanceWithLocale = FastDateFormat.getTimeInstance(FastDateFormat.FULL, Locale.FRANCE);
        timeInstanceWithTimeZone = FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        timeInstanceWithTimeZoneAndLocale = FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        dateTimeInstance = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
        dateTimeInstanceWithLocale = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.FRANCE);
        dateTimeInstanceWithTimeZone = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        dateTimeInstanceWithTimeZoneAndLocale = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(dateFormat);
    }

    @Test
    public void testGetInstanceWithPattern() {
        assertNotNull(dateFormatWithPattern);
        assertEquals("yyyy-MM-dd", dateFormatWithPattern.getPattern());
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(dateFormatWithPatternAndTimeZone);
        assertEquals("yyyy-MM-dd", dateFormatWithPatternAndTimeZone.getPattern());
        assertEquals(TimeZone.getTimeZone("GMT"), dateFormatWithPatternAndTimeZone.getTimeZone());
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(dateFormatWithPatternAndLocale);
        assertEquals("yyyy-MM-dd", dateFormatWithPatternAndLocale.getPattern());
        assertEquals(Locale.FRANCE, dateFormatWithPatternAndLocale.getLocale());
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(dateFormatWithPatternTimeZoneAndLocale);
        assertEquals("yyyy-MM-dd", dateFormatWithPatternTimeZoneAndLocale.getPattern());
        assertEquals(TimeZone.getTimeZone("GMT"), dateFormatWithPatternTimeZoneAndLocale.getTimeZone());
        assertEquals(Locale.FRANCE, dateFormatWithPatternTimeZoneAndLocale.getLocale());
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(dateInstance);
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        assertNotNull(dateInstanceWithLocale);
        assertEquals(Locale.FRANCE, dateInstanceWithLocale.getLocale());
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        assertNotNull(dateInstanceWithTimeZone);
        assertEquals(TimeZone.getTimeZone("GMT"), dateInstanceWithTimeZone.getTimeZone());
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        assertNotNull(dateInstanceWithTimeZoneAndLocale);
        assertEquals(TimeZone.getTimeZone("GMT"), dateInstanceWithTimeZoneAndLocale.getTimeZone());
        assertEquals(Locale.FRANCE, dateInstanceWithTimeZoneAndLocale.getLocale());
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(timeInstance);
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        assertNotNull(timeInstanceWithLocale);
        assertEquals(Locale.FRANCE, timeInstanceWithLocale.getLocale());
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        assertNotNull(timeInstanceWithTimeZone);
        assertEquals(TimeZone.getTimeZone("GMT"), timeInstanceWithTimeZone.getTimeZone());
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(timeInstanceWithTimeZoneAndLocale);
        assertEquals(TimeZone.getTimeZone("GMT"), timeInstanceWithTimeZoneAndLocale.getTimeZone());
        assertEquals(Locale.FRANCE, timeInstanceWithTimeZoneAndLocale.getLocale());
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(dateTimeInstance);
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        assertNotNull(dateTimeInstanceWithLocale);
        assertEquals(Locale.FRANCE, dateTimeInstanceWithLocale.getLocale());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        assertNotNull(dateTimeInstanceWithTimeZone);
        assertEquals(TimeZone.getTimeZone("GMT"), dateTimeInstanceWithTimeZone.getTimeZone());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(dateTimeInstanceWithTimeZoneAndLocale);
        assertEquals(TimeZone.getTimeZone("GMT"), dateTimeInstanceWithTimeZoneAndLocale.getTimeZone());
        assertEquals(Locale.FRANCE, dateTimeInstanceWithTimeZoneAndLocale.getLocale());
    }

    @Test
    public void testFormatObject() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        assertNotNull(dateFormat.format(date, buffer, pos));
    }

    @Test
    public void testFormatMillis() {
        long millis = System.currentTimeMillis();
        assertNotNull(dateFormat.format(millis));
    }

    @Test
    public void testFormatDate() {
        Date date = new Date();
        assertNotNull(dateFormat.format(date));
    }

    @Test
    public void testFormatCalendar() {
        Calendar calendar = Calendar.getInstance();
        assertNotNull(dateFormat.format(calendar));
    }

    @Test
    public void testFormatMillisStringBuffer() {
        long millis = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormat.format(millis, buffer));
    }

    @Test
    public void testFormatDateStringBuffer() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormat.format(date, buffer));
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormat.format(calendar, buffer));
    }

    @Test
    public void testParse() throws ParseException {
        String dateString = "2023-10-01";
        Date date = dateFormatWithPattern.parse(dateString);
        assertNotNull(date);
    }

    @Test
    public void testParseWithPosition() {
        String dateString = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        Date date = dateFormatWithPattern.parse(dateString, pos);
        assertNotNull(date);
    }

    @Test
    public void testParseObject() {
        String dateString = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        Object obj = dateFormatWithPattern.parseObject(dateString, pos);
        assertNotNull(obj);
    }

    @Test
    public void testGetPattern() {
        assertEquals("yyyy-MM-dd", dateFormatWithPattern.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(TimeZone.getTimeZone("GMT"), dateFormatWithPatternAndTimeZone.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(Locale.FRANCE, dateFormatWithPatternAndLocale.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(dateFormatWithPattern.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat anotherDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
        assertTrue(dateFormatWithPattern.equals(anotherDateFormat));
    }

    @Test
    public void testHashCode() {
        FastDateFormat anotherDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
        assertEquals(dateFormatWithPattern.hashCode(), anotherDateFormat.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(dateFormatWithPattern.toString());
    }

    @Test
    public void testApplyRules() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormatWithPattern.applyRules(calendar, buffer));
    }
}
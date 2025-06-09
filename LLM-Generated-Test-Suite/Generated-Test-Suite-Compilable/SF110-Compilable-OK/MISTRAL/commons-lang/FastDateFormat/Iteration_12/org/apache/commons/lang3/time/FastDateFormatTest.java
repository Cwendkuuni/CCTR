package org.apache.commons.lang3.time;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.*;

public class FastDateFormatTest {

    private FastDateFormat defaultFormat;
    private FastDateFormat patternFormat;
    private FastDateFormat patternTimeZoneFormat;
    private FastDateFormat patternLocaleFormat;
    private FastDateFormat patternTimeZoneLocaleFormat;
    private FastDateFormat dateStyleFormat;
    private FastDateFormat dateStyleLocaleFormat;
    private FastDateFormat dateStyleTimeZoneFormat;
    private FastDateFormat dateStyleTimeZoneLocaleFormat;
    private FastDateFormat timeStyleFormat;
    private FastDateFormat timeStyleLocaleFormat;
    private FastDateFormat timeStyleTimeZoneFormat;
    private FastDateFormat timeStyleTimeZoneLocaleFormat;
    private FastDateFormat dateTimeStyleFormat;
    private FastDateFormat dateTimeStyleLocaleFormat;
    private FastDateFormat dateTimeStyleTimeZoneFormat;
    private FastDateFormat dateTimeStyleTimeZoneLocaleFormat;

    @Before
    public void setUp() {
        defaultFormat = FastDateFormat.getInstance();
        patternFormat = FastDateFormat.getInstance("yyyy-MM-dd");
        patternTimeZoneFormat = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"));
        patternLocaleFormat = FastDateFormat.getInstance("yyyy-MM-dd", Locale.FRANCE);
        patternTimeZoneLocaleFormat = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        dateStyleFormat = FastDateFormat.getDateInstance(FastDateFormat.FULL);
        dateStyleLocaleFormat = FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.FRANCE);
        dateStyleTimeZoneFormat = FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        dateStyleTimeZoneLocaleFormat = FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        timeStyleFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL);
        timeStyleLocaleFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL, Locale.FRANCE);
        timeStyleTimeZoneFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        timeStyleTimeZoneLocaleFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
        dateTimeStyleFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
        dateTimeStyleLocaleFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.FRANCE);
        dateTimeStyleTimeZoneFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT"));
        dateTimeStyleTimeZoneLocaleFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(defaultFormat);
    }

    @Test
    public void testGetInstanceWithPattern() {
        assertNotNull(patternFormat);
        assertEquals("yyyy-MM-dd", patternFormat.getPattern());
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(patternTimeZoneFormat);
        assertEquals("yyyy-MM-dd", patternTimeZoneFormat.getPattern());
        assertEquals(TimeZone.getTimeZone("GMT"), patternTimeZoneFormat.getTimeZone());
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(patternLocaleFormat);
        assertEquals("yyyy-MM-dd", patternLocaleFormat.getPattern());
        assertEquals(Locale.FRANCE, patternLocaleFormat.getLocale());
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(patternTimeZoneLocaleFormat);
        assertEquals("yyyy-MM-dd", patternTimeZoneLocaleFormat.getPattern());
        assertEquals(TimeZone.getTimeZone("GMT"), patternTimeZoneLocaleFormat.getTimeZone());
        assertEquals(Locale.FRANCE, patternTimeZoneLocaleFormat.getLocale());
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(dateStyleFormat);
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        assertNotNull(dateStyleLocaleFormat);
        assertEquals(Locale.FRANCE, dateStyleLocaleFormat.getLocale());
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        assertNotNull(dateStyleTimeZoneFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), dateStyleTimeZoneFormat.getTimeZone());
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        assertNotNull(dateStyleTimeZoneLocaleFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), dateStyleTimeZoneLocaleFormat.getTimeZone());
        assertEquals(Locale.FRANCE, dateStyleTimeZoneLocaleFormat.getLocale());
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(timeStyleFormat);
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        assertNotNull(timeStyleLocaleFormat);
        assertEquals(Locale.FRANCE, timeStyleLocaleFormat.getLocale());
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        assertNotNull(timeStyleTimeZoneFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), timeStyleTimeZoneFormat.getTimeZone());
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(timeStyleTimeZoneLocaleFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), timeStyleTimeZoneLocaleFormat.getTimeZone());
        assertEquals(Locale.FRANCE, timeStyleTimeZoneLocaleFormat.getLocale());
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(dateTimeStyleFormat);
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        assertNotNull(dateTimeStyleLocaleFormat);
        assertEquals(Locale.FRANCE, dateTimeStyleLocaleFormat.getLocale());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        assertNotNull(dateTimeStyleTimeZoneFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), dateTimeStyleTimeZoneFormat.getTimeZone());
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(dateTimeStyleTimeZoneLocaleFormat);
        assertEquals(TimeZone.getTimeZone("GMT"), dateTimeStyleTimeZoneLocaleFormat.getTimeZone());
        assertEquals(Locale.FRANCE, dateTimeStyleTimeZoneLocaleFormat.getLocale());
    }

    @Test
    public void testFormatObject() {
        StringBuffer buf = new StringBuffer();
        FieldPosition pos = new FieldPosition(DateFormat.YEAR_FIELD);
        assertNotNull(patternFormat.format(new Date(), buf, pos));
    }

    @Test
    public void testFormatMillis() {
        assertNotNull(patternFormat.format(System.currentTimeMillis()));
    }

    @Test
    public void testFormatDate() {
        assertNotNull(patternFormat.format(new Date()));
    }

    @Test
    public void testFormatCalendar() {
        assertNotNull(patternFormat.format(Calendar.getInstance()));
    }

    @Test
    public void testFormatMillisStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(patternFormat.format(System.currentTimeMillis(), buf));
    }

    @Test
    public void testFormatDateStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(patternFormat.format(new Date(), buf));
    }

    @Test
    public void testFormatCalendarStringBuffer() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(patternFormat.format(Calendar.getInstance(), buf));
    }

    @Test
    public void testParse() throws ParseException {
        String dateStr = patternFormat.format(new Date());
        assertNotNull(patternFormat.parse(dateStr));
    }

    @Test
    public void testParseWithParsePosition() {
        String dateStr = patternFormat.format(new Date());
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(patternFormat.parse(dateStr, pos));
    }

    @Test
    public void testParseObject() {
        String dateStr = patternFormat.format(new Date());
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(patternFormat.parseObject(dateStr, pos));
    }

    @Test
    public void testGetPattern() {
        assertEquals("yyyy-MM-dd", patternFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(TimeZone.getTimeZone("GMT"), patternTimeZoneFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(Locale.FRANCE, patternLocaleFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(patternFormat.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEquals() {
        FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd");
        FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd");
        assertTrue(format1.equals(format2));
    }

    @Test
    public void testHashCode() {
        FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd");
        FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd");
        assertEquals(format1.hashCode(), format2.hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(patternFormat.toString());
    }

    @Test
    public void testApplyRules() {
        StringBuffer buf = new StringBuffer();
        assertNotNull(patternFormat.applyRules(Calendar.getInstance(), buf));
    }
}
package org.apache.commons.lang3.time;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FastDateFormatTest {

    private FastDateFormat dateFormat;
    private FastDateFormat dateFormatWithPattern;
    private FastDateFormat dateFormatWithPatternAndTimeZone;
    private FastDateFormat dateFormatWithPatternAndLocale;
    private FastDateFormat dateFormatWithPatternTimeZoneAndLocale;

    @Before
    public void setUp() {
        dateFormat = FastDateFormat.getInstance();
        dateFormatWithPattern = FastDateFormat.getInstance("yyyy-MM-dd");
        dateFormatWithPatternAndTimeZone = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"));
        dateFormatWithPatternAndLocale = FastDateFormat.getInstance("yyyy-MM-dd", Locale.FRANCE);
        dateFormatWithPatternTimeZoneAndLocale = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"), Locale.FRANCE);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(dateFormat);
    }

    @Test
    public void testGetInstanceWithPattern() {
        assertNotNull(dateFormatWithPattern);
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(dateFormatWithPatternAndTimeZone);
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(dateFormatWithPatternAndLocale);
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(dateFormatWithPatternTimeZoneAndLocale);
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetDateInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.FRANCE));
    }

    @Test
    public void testGetDateInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT")));
    }

    @Test
    public void testGetDateInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE));
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
    }

    @Test
    public void testGetTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, Locale.FRANCE));
    }

    @Test
    public void testGetTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT")));
    }

    @Test
    public void testGetTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE));
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL));
    }

    @Test
    public void testGetDateTimeInstanceWithLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.FRANCE));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZone() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT")));
    }

    @Test
    public void testGetDateTimeInstanceWithTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, TimeZone.getTimeZone("GMT"), Locale.FRANCE));
    }

    @Test
    public void testFormatObject() {
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormat.format(date, buffer, null));
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
        assertNotNull(dateFormatWithPattern.parse(dateString));
    }

    @Test
    public void testParseWithParsePosition() {
        String dateString = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(dateFormatWithPattern.parse(dateString, pos));
    }

    @Test
    public void testParseObject() {
        String dateString = "2023-10-01";
        ParsePosition pos = new ParsePosition(0);
        assertNotNull(dateFormatWithPattern.parseObject(dateString, pos));
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
        String expected = "FastDateFormat[yyyy-MM-dd," + Locale.FRANCE + "," + TimeZone.getTimeZone("GMT").getID() + "]";
        assertEquals(expected, dateFormatWithPatternTimeZoneAndLocale.toString());
    }

    @Test
    public void testApplyRules() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(dateFormatWithPattern.applyRules(calendar, buffer));
    }
}
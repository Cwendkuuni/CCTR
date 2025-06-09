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
        assertNotNull(FastDateFormat.getInstance());
    }

    @Test
    public void testGetInstanceWithPattern() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd"));
    }

    @Test
    public void testGetInstanceWithPatternAndTimeZone() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT")));
    }

    @Test
    public void testGetInstanceWithPatternAndLocale() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", Locale.FRANCE));
    }

    @Test
    public void testGetInstanceWithPatternTimeZoneAndLocale() {
        assertNotNull(FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"), Locale.FRANCE));
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
        StringBuffer buffer = new StringBuffer();
        dateFormat.format(new Date(), buffer, null);
        assertNotNull(buffer.toString());
    }

    @Test
    public void testFormatMillis() {
        String formattedDate = dateFormat.format(System.currentTimeMillis());
        assertNotNull(formattedDate);
    }

    @Test
    public void testFormatDate() {
        String formattedDate = dateFormat.format(new Date());
        assertNotNull(formattedDate);
    }

    @Test
    public void testFormatCalendar() {
        String formattedDate = dateFormat.format(Calendar.getInstance());
        assertNotNull(formattedDate);
    }

    @Test
    public void testFormatMillisWithBuffer() {
        StringBuffer buffer = new StringBuffer();
        dateFormat.format(System.currentTimeMillis(), buffer);
        assertNotNull(buffer.toString());
    }

    @Test
    public void testFormatDateWithBuffer() {
        StringBuffer buffer = new StringBuffer();
        dateFormat.format(new Date(), buffer);
        assertNotNull(buffer.toString());
    }

    @Test
    public void testFormatCalendarWithBuffer() {
        StringBuffer buffer = new StringBuffer();
        dateFormat.format(Calendar.getInstance(), buffer);
        assertNotNull(buffer.toString());
    }

    @Test
    public void testParse() throws ParseException {
        Date date = dateFormat.parse("2023-10-01");
        assertNotNull(date);
    }

    @Test
    public void testParseWithPosition() {
        ParsePosition pos = new ParsePosition(0);
        Date date = dateFormat.parse("2023-10-01", pos);
        assertNotNull(date);
    }

    @Test
    public void testParseObject() {
        ParsePosition pos = new ParsePosition(0);
        Object obj = dateFormat.parseObject("2023-10-01", pos);
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
        assertTrue(dateFormat.getMaxLengthEstimate() > 0);
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
        StringBuffer buffer = new StringBuffer();
        dateFormat.applyRules(Calendar.getInstance(), buffer);
        assertNotNull(buffer.toString());
    }
}
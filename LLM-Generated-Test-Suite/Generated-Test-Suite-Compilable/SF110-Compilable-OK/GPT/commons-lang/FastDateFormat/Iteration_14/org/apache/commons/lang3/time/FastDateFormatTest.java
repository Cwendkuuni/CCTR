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
    private static final String PATTERN = "yyyy-MM-dd";
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");
    private static final Locale LOCALE = Locale.US;

    @Before
    public void setUp() {
        fastDateFormat = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(FastDateFormat.getInstance());
        assertNotNull(FastDateFormat.getInstance(PATTERN));
        assertNotNull(FastDateFormat.getInstance(PATTERN, TIME_ZONE));
        assertNotNull(FastDateFormat.getInstance(PATTERN, LOCALE));
        assertNotNull(FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.LONG, LOCALE));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.MEDIUM, TIME_ZONE));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.SHORT, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.LONG, LOCALE));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.MEDIUM, TIME_ZONE));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.SHORT, TIME_ZONE, LOCALE));
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.LONG, LOCALE));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.MEDIUM, FastDateFormat.MEDIUM, TIME_ZONE));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.SHORT, TIME_ZONE, LOCALE));
    }

    @Test
    public void testFormatMethods() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuffer buffer = new StringBuffer();

        assertNotNull(fastDateFormat.format(date));
        assertNotNull(fastDateFormat.format(calendar));
        assertNotNull(fastDateFormat.format(date.getTime()));
        assertNotNull(fastDateFormat.format(date, buffer));
        assertNotNull(fastDateFormat.format(calendar, buffer));
        assertNotNull(fastDateFormat.format(date.getTime(), buffer));
    }

    @Test
    public void testParseMethods() throws ParseException {
        String dateStr = "2023-10-01";
        Date date = fastDateFormat.parse(dateStr);
        assertNotNull(date);

        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = fastDateFormat.parse(dateStr, pos);
        assertNotNull(parsedDate);
        assertEquals(date, parsedDate);

        pos.setIndex(0);
        Object parsedObject = fastDateFormat.parseObject(dateStr, pos);
        assertNotNull(parsedObject);
        assertEquals(date, parsedObject);
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
    public void testEqualsAndHashCode() {
        FastDateFormat anotherFormat = FastDateFormat.getInstance(PATTERN, TIME_ZONE, LOCALE);
        assertTrue(fastDateFormat.equals(anotherFormat));
        assertEquals(fastDateFormat.hashCode(), anotherFormat.hashCode());

        FastDateFormat differentFormat = FastDateFormat.getInstance("yyyy/MM/dd", TIME_ZONE, LOCALE);
        assertFalse(fastDateFormat.equals(differentFormat));
    }

    @Test
    public void testToString() {
        String expected = "FastDateFormat[" + PATTERN + "," + LOCALE + "," + TIME_ZONE.getID() + "]";
        assertEquals(expected, fastDateFormat.toString());
    }
}
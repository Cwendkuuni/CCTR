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
    private final String pattern = "yyyy-MM-dd";
    private final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private final Locale locale = Locale.US;

    @Before
    public void setUp() {
        fastDateFormat = FastDateFormat.getInstance(pattern, timeZone, locale);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(FastDateFormat.getInstance());
        assertNotNull(FastDateFormat.getInstance(pattern));
        assertNotNull(FastDateFormat.getInstance(pattern, timeZone));
        assertNotNull(FastDateFormat.getInstance(pattern, locale));
        assertNotNull(FastDateFormat.getInstance(pattern, timeZone, locale));
    }

    @Test
    public void testGetDateInstance() {
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.FULL));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.LONG, locale));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.MEDIUM, timeZone));
        assertNotNull(FastDateFormat.getDateInstance(FastDateFormat.SHORT, timeZone, locale));
    }

    @Test
    public void testGetTimeInstance() {
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.FULL));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.LONG, locale));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.MEDIUM, timeZone));
        assertNotNull(FastDateFormat.getTimeInstance(FastDateFormat.SHORT, timeZone, locale));
    }

    @Test
    public void testGetDateTimeInstance() {
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.LONG));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, locale));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.MEDIUM, FastDateFormat.SHORT, timeZone));
        assertNotNull(FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.FULL, timeZone, locale));
    }

    @Test
    public void testFormatMethods() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        long millis = System.currentTimeMillis();

        assertNotNull(fastDateFormat.format(date));
        assertNotNull(fastDateFormat.format(calendar));
        assertNotNull(fastDateFormat.format(millis));

        StringBuffer buffer = new StringBuffer();
        assertNotNull(fastDateFormat.format(date, buffer));
        assertNotNull(fastDateFormat.format(calendar, buffer));
        assertNotNull(fastDateFormat.format(millis, buffer));
    }

    @Test
    public void testParseMethods() throws ParseException {
        String dateStr = "2023-10-10";
        Date date = fastDateFormat.parse(dateStr);
        assertNotNull(date);

        ParsePosition pos = new ParsePosition(0);
        Date dateWithPos = fastDateFormat.parse(dateStr, pos);
        assertNotNull(dateWithPos);

        pos.setIndex(0);
        Object parsedObject = fastDateFormat.parseObject(dateStr, pos);
        assertNotNull(parsedObject);
    }

    @Test
    public void testGetPattern() {
        assertEquals(pattern, fastDateFormat.getPattern());
    }

    @Test
    public void testGetTimeZone() {
        assertEquals(timeZone, fastDateFormat.getTimeZone());
    }

    @Test
    public void testGetLocale() {
        assertEquals(locale, fastDateFormat.getLocale());
    }

    @Test
    public void testGetMaxLengthEstimate() {
        assertTrue(fastDateFormat.getMaxLengthEstimate() > 0);
    }

    @Test
    public void testEqualsAndHashCode() {
        FastDateFormat anotherInstance = FastDateFormat.getInstance(pattern, timeZone, locale);
        assertTrue(fastDateFormat.equals(anotherInstance));
        assertEquals(fastDateFormat.hashCode(), anotherInstance.hashCode());

        FastDateFormat differentInstance = FastDateFormat.getInstance("yyyy/MM/dd", timeZone, locale);
        assertFalse(fastDateFormat.equals(differentInstance));
    }

    @Test
    public void testToString() {
        String expected = "FastDateFormat[" + pattern + "," + locale + "," + timeZone.getID() + "]";
        assertEquals(expected, fastDateFormat.toString());
    }
}
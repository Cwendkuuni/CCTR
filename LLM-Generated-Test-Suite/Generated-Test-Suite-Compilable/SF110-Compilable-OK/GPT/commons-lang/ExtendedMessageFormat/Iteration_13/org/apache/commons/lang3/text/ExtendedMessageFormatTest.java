package org.apache.commons.lang3.text;

import org.apache.commons.lang3.text.ExtendedMessageFormat;
import org.apache.commons.lang3.text.FormatFactory;
import org.junit.Test;

import java.text.Format;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

public class ExtendedMessageFormatTest {

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.US);
        assertEquals("Hello {0}", emf.toPattern());
        assertEquals(Locale.US, emf.getLocale());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        Map<String, FormatFactory> registry = new HashMap<>();
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", registry);
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        Map<String, FormatFactory> registry = new HashMap<>();
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.US, registry);
        assertEquals("Hello {0}", emf.toPattern());
        assertEquals(Locale.US, emf.getLocale());
    }

    @Test
    public void testApplyPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.applyPattern("Hi {0}");
        assertEquals("Hi {0}", emf.toPattern());
    }

    @Test
    public void testToPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatUnsupported() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormat(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatByArgumentIndexUnsupported() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormatByArgumentIndex(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsUnsupported() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormats(new Format[0]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsByArgumentIndexUnsupported() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormatsByArgumentIndex(new Format[0]);
    }

    @Test
    public void testEqualsAndHashCode() {
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("Hello {0}");
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Hello {0}");
        ExtendedMessageFormat emf3 = new ExtendedMessageFormat("Hi {0}");

        assertTrue(emf1.equals(emf2));
        assertFalse(emf1.equals(emf3));
        assertEquals(emf1.hashCode(), emf2.hashCode());
        assertNotEquals(emf1.hashCode(), emf3.hashCode());
    }

    @Test
    public void testEqualsWithDifferentObject() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertFalse(emf.equals(new Object()));
    }

    @Test
    public void testEqualsWithNull() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertFalse(emf.equals(null));
    }

    @Test
    public void testFormat() throws ParseException {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        String result = emf.format(new Object[]{"World"});
        assertEquals("Hello World", result);
    }
}
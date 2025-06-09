package org.apache.commons.lang3.text;

import org.apache.commons.lang3.text.ExtendedMessageFormat;
import org.apache.commons.lang3.text.FormatFactory;
import org.junit.Before;
import org.junit.Test;

import java.text.Format;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

public class ExtendedMessageFormatTest {

    private ExtendedMessageFormat emf;
    private Map<String, FormatFactory> registry;

    @Before
    public void setUp() {
        registry = new HashMap<>();
        emf = new ExtendedMessageFormat("{0}", Locale.US, registry);
    }

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("{0}");
        assertEquals("{0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("{0}", Locale.US);
        assertEquals("{0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("{0}", registry);
        assertEquals("{0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("{0}", Locale.US, registry);
        assertEquals("{0}", emf.toPattern());
    }

    @Test
    public void testApplyPattern() {
        emf.applyPattern("{0,number,#.##}");
        assertEquals("{0,number,#.##}", emf.toPattern());
    }

    @Test
    public void testToPattern() {
        emf.applyPattern("{0,number,#.##}");
        assertEquals("{0,number,#.##}", emf.toPattern());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatThrowsException() {
        emf.setFormat(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatByArgumentIndexThrowsException() {
        emf.setFormatByArgumentIndex(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsThrowsException() {
        emf.setFormats(new Format[0]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsByArgumentIndexThrowsException() {
        emf.setFormatsByArgumentIndex(new Format[0]);
    }

    @Test
    public void testEquals() {
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("{0}", Locale.US, registry);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("{0}", Locale.US, registry);
        assertTrue(emf1.equals(emf2));
        assertTrue(emf1.equals(emf1));
        assertFalse(emf1.equals(null));
        assertFalse(emf1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("{0}", Locale.US, registry);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("{0}", Locale.US, registry);
        assertEquals(emf1.hashCode(), emf2.hashCode());
    }
}
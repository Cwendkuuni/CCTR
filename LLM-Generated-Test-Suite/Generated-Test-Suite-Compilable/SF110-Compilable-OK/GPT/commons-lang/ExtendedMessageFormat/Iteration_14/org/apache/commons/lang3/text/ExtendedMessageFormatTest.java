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
        emf = new ExtendedMessageFormat("Pattern {0}", Locale.US, registry);
    }

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern {0}");
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern {0}", Locale.US);
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern {0}", registry);
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testApplyPattern() {
        emf.applyPattern("New pattern {1}");
        assertEquals("New pattern {1}", emf.toPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidPattern() {
        emf.applyPattern("Invalid pattern {");
    }

    @Test
    public void testToPattern() {
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testEquals() {
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Pattern {0}", Locale.US, registry);
        assertTrue(emf.equals(emf2));
        assertFalse(emf.equals(null));
        assertFalse(emf.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Pattern {0}", Locale.US, registry);
        assertEquals(emf.hashCode(), emf2.hashCode());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormat() {
        emf.setFormat(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatByArgumentIndex() {
        emf.setFormatByArgumentIndex(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormats() {
        emf.setFormats(new Format[0]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsByArgumentIndex() {
        emf.setFormatsByArgumentIndex(new Format[0]);
    }
}
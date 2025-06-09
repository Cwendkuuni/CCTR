package org.apache.commons.lang3.text;

import org.apache.commons.lang3.text.ExtendedMessageFormat;
import org.apache.commons.lang3.text.FormatFactory;
import org.junit.Before;
import org.junit.Test;

import java.text.Format;
import java.text.ParseException;
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
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern {0}", Locale.US, registry);
        assertEquals("Pattern {0}", emf.toPattern());
    }

    @Test
    public void testApplyPattern() {
        emf.applyPattern("New pattern {1}");
        assertEquals("New pattern {1}", emf.toPattern());
    }

    @Test
    public void testToPattern() {
        assertEquals("Pattern {0}", emf.toPattern());
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

    @Test
    public void testApplyPatternWithCustomFormat() {
        registry.put("custom", (name, args, locale) -> new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
                return toAppendTo.append("customFormat");
            }

            @Override
            public Object parseObject(String source, java.text.ParsePosition pos) {
                return null;
            }
        });

        emf = new ExtendedMessageFormat("Pattern {0,custom}", Locale.US, registry);
        assertEquals("Pattern {0,custom}", emf.toPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidFormat() {
        emf.applyPattern("Pattern {0,invalid}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithUnterminatedFormatElement() {
        emf.applyPattern("Pattern {0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidArgumentIndex() {
        emf.applyPattern("Pattern {a}");
    }
}
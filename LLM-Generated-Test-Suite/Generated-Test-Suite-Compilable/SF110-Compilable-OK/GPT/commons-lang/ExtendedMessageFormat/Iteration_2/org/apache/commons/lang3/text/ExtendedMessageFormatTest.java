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
        emf = new ExtendedMessageFormat("", Locale.US, registry);
    }

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern");
        assertEquals("Pattern", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern", Locale.FRANCE);
        assertEquals("Pattern", emf.toPattern());
        assertEquals(Locale.FRANCE, emf.getLocale());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern", registry);
        assertEquals("Pattern", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Pattern", Locale.GERMANY, registry);
        assertEquals("Pattern", emf.toPattern());
        assertEquals(Locale.GERMANY, emf.getLocale());
    }

    @Test
    public void testApplyPattern() {
        emf.applyPattern("Test {0}");
        assertEquals("Test {0}", emf.toPattern());
    }

    @Test
    public void testToPattern() {
        emf.applyPattern("Test {0}");
        assertEquals("Test {0}", emf.toPattern());
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
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        assertTrue(emf1.equals(emf2));
        assertTrue(emf1.equals(emf1));
        assertFalse(emf1.equals(null));
        assertFalse(emf1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        assertEquals(emf1.hashCode(), emf2.hashCode());
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

        emf = new ExtendedMessageFormat("Test {0,custom}", Locale.US, registry);
        emf.applyPattern("Test {0,custom}");
        assertEquals("Test {0,custom}", emf.toPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidFormat() {
        emf.applyPattern("Test {0,invalid}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithUnterminatedFormat() {
        emf.applyPattern("Test {0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidArgumentIndex() {
        emf.applyPattern("Test {a}");
    }
}
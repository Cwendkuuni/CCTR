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
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.FRANCE);
        assertEquals("Hello {0}", emf.toPattern());
        assertEquals(Locale.FRANCE, emf.getLocale());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", registry);
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.GERMANY, registry);
        assertEquals("Hello {0}", emf.toPattern());
        assertEquals(Locale.GERMANY, emf.getLocale());
    }

    @Test
    public void testToPattern() {
        emf.applyPattern("Test {0}");
        assertEquals("Test {0}", emf.toPattern());
    }

    @Test
    public void testApplyPatternWithRegistry() {
        registry.put("upper", (name, args, locale) -> new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
                return toAppendTo.append(obj.toString().toUpperCase());
            }

            @Override
            public Object parseObject(String source, java.text.ParsePosition pos) {
                return source.toLowerCase();
            }
        });

        emf.applyPattern("Test {0,upper}");
        assertEquals("Test {0,upper}", emf.toPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidFormat() {
        emf.applyPattern("Test {0,invalid}");
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
    public void testEqualsAndHashCode() {
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat("Test {0}", Locale.US, registry);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat("Test {0}", Locale.US, registry);
        assertEquals(emf1, emf2);
        assertEquals(emf1.hashCode(), emf2.hashCode());

        ExtendedMessageFormat emf3 = new ExtendedMessageFormat("Test {1}", Locale.US, registry);
        assertNotEquals(emf1, emf3);
        assertNotEquals(emf1.hashCode(), emf3.hashCode());
    }

    @Test
    public void testEqualsWithDifferentObject() {
        assertNotEquals(emf, new Object());
    }

    @Test
    public void testEqualsWithNull() {
        assertNotEquals(emf, null);
    }
}
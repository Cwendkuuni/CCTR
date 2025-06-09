package org.apache.commons.lang.text;

import static org.junit.Assert.*;

import java.text.Format;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.junit.Before;
import org.junit.Test;

public class ExtendedMessageFormatTest {

    private Map<String, FormatFactory> registry;

    @Before
    public void setUp() {
        registry = new HashMap<>();
        registry.put("upper", new UpperCaseFormatFactory());
    }

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.US);
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0,upper}", registry);
        assertEquals("Hello {0,upper}", emf.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0,upper}", Locale.US, registry);
        assertEquals("Hello {0,upper}", emf.toPattern());
    }

    @Test
    public void testApplyPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}", Locale.US, registry);
        emf.applyPattern("Hi {0,upper}");
        assertEquals("Hi {0,upper}", emf.toPattern());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormat() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormat(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatByArgumentIndex() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormatByArgumentIndex(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormats() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormats(new Format[0]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatsByArgumentIndex() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0}");
        emf.setFormatsByArgumentIndex(new Format[0]);
    }

    @Test
    public void testFormatWithCustomFormat() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0,upper}", Locale.US, registry);
        String result = emf.format(new Object[]{"world"});
        assertEquals("Hello WORLD", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPattern() {
        new ExtendedMessageFormat("Hello {0,unknown}", Locale.US, registry);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnterminatedFormatElement() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0", Locale.US, registry);
        emf.applyPattern("Hello {0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArgumentIndex() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {a}", Locale.US, registry);
        emf.applyPattern("Hello {a}");
    }

    // Custom FormatFactory for testing
    private static class UpperCaseFormatFactory implements FormatFactory {
        @Override
        public Format getFormat(String name, String args, Locale locale) {
            return new UpperCaseFormat();
        }
    }

    // Custom Format for testing
    private static class UpperCaseFormat extends Format {
        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
            if (obj instanceof String) {
                toAppendTo.append(((String) obj).toUpperCase());
            }
            return toAppendTo;
        }

        @Override
        public Object parseObject(String source, java.text.ParsePosition pos) {
            return source.substring(pos.getIndex());
        }
    }
}
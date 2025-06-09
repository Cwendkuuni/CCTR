package org.apache.commons.lang.text;

import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.junit.Before;
import org.junit.Test;

import java.text.Format;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

public class ExtendedMessageFormatTest {

    private static final String PATTERN = "Hello {0}, you have {1} new messages.";
    private static final String INVALID_PATTERN = "Hello {0, you have {1} new messages.";
    private static final Locale LOCALE = Locale.US;
    private static final Map<String, FormatFactory> REGISTRY = new HashMap<>();

    private ExtendedMessageFormat emf;

    @Before
    public void setUp() {
        emf = new ExtendedMessageFormat(PATTERN, LOCALE, REGISTRY);
    }

    @Test
    public void testConstructorWithPattern() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat(PATTERN);
        assertNotNull(emf);
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat(PATTERN, LOCALE);
        assertNotNull(emf);
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat(PATTERN, REGISTRY);
        assertNotNull(emf);
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat(PATTERN, LOCALE, REGISTRY);
        assertNotNull(emf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidPattern() {
        new ExtendedMessageFormat(INVALID_PATTERN);
    }

    @Test
    public void testToPattern() {
        assertEquals(PATTERN, emf.toPattern());
    }

    @Test
    public void testApplyPattern() {
        String newPattern = "Goodbye {0}, see you later.";
        emf.applyPattern(newPattern);
        assertEquals(newPattern, emf.toPattern());
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
    public void testFormat() {
        Object[] arguments = {"John", 5};
        String result = emf.format(arguments);
        assertEquals("Hello John, you have 5 new messages.", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidPattern() {
        emf.applyPattern(INVALID_PATTERN);
    }

    @Test
    public void testFormatWithCustomRegistry() {
        // Assuming a custom FormatFactory is added to the registry
        REGISTRY.put("custom", new CustomFormatFactory());
        ExtendedMessageFormat customEmf = new ExtendedMessageFormat("{0,custom}", LOCALE, REGISTRY);
        Object[] arguments = {"test"};
        String result = customEmf.format(arguments);
        assertEquals("Custom formatted: test", result);
    }

    // Custom FormatFactory for testing purposes
    private static class CustomFormatFactory implements FormatFactory {
        @Override
        public Format getFormat(String name, String args, Locale locale) {
            return new CustomFormat();
        }
    }

    // Custom Format for testing purposes
    private static class CustomFormat extends Format {
        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
            return toAppendTo.append("Custom formatted: ").append(obj);
        }

        @Override
        public Object parseObject(String source, java.text.ParsePosition pos) {
            return null;
        }
    }
}
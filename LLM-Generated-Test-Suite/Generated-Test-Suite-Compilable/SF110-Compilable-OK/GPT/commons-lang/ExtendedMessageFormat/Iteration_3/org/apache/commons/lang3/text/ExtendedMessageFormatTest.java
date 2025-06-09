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
        ExtendedMessageFormat format = new ExtendedMessageFormat("Pattern");
        assertEquals("Pattern", format.toPattern());
    }

    @Test
    public void testConstructorWithPatternAndLocale() {
        ExtendedMessageFormat format = new ExtendedMessageFormat("Pattern", Locale.FRANCE);
        assertEquals("Pattern", format.toPattern());
        assertEquals(Locale.FRANCE, format.getLocale());
    }

    @Test
    public void testConstructorWithPatternAndRegistry() {
        ExtendedMessageFormat format = new ExtendedMessageFormat("Pattern", registry);
        assertEquals("Pattern", format.toPattern());
    }

    @Test
    public void testConstructorWithPatternLocaleAndRegistry() {
        ExtendedMessageFormat format = new ExtendedMessageFormat("Pattern", Locale.GERMANY, registry);
        assertEquals("Pattern", format.toPattern());
        assertEquals(Locale.GERMANY, format.getLocale());
    }

    @Test
    public void testApplyPattern() {
        emf.applyPattern("Hello {0}");
        assertEquals("Hello {0}", emf.toPattern());
    }

    @Test
    public void testApplyPatternWithCustomFormat() {
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

        emf.applyPattern("Hello {0,upper}");
        assertEquals("Hello {0,upper}", emf.toPattern());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPatternWithInvalidFormat() {
        emf.applyPattern("Hello {0,invalid}");
    }

    @Test
    public void testEqualsAndHashCode() {
        ExtendedMessageFormat format1 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        ExtendedMessageFormat format2 = new ExtendedMessageFormat("Pattern", Locale.US, registry);
        ExtendedMessageFormat format3 = new ExtendedMessageFormat("Different", Locale.US, registry);

        assertTrue(format1.equals(format2));
        assertFalse(format1.equals(format3));
        assertEquals(format1.hashCode(), format2.hashCode());
        assertNotEquals(format1.hashCode(), format3.hashCode());
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
package org.apache.commons.lang3;

import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LocaleUtilsTest {

    @Before
    public void setUp() {
        // Setup if needed before each test
    }

    @After
    public void tearDown() {
        // Cleanup if needed after each test
    }

    @Test
    public void testToLocale_NullInput() {
        assertNull(LocaleUtils.toLocale(null));
    }

    @Test
    public void testToLocale_EmptyString() {
        Locale locale = LocaleUtils.toLocale("");
        assertEquals("", locale.getLanguage());
        assertEquals("", locale.getCountry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidFormatWithHash() {
        LocaleUtils.toLocale("en#US");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidFormatTooShort() {
        LocaleUtils.toLocale("e");
    }

    @Test
    public void testToLocale_ValidLanguageOnly() {
        Locale locale = LocaleUtils.toLocale("en");
        assertEquals("en", locale.getLanguage());
        assertEquals("", locale.getCountry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLanguageFormat() {
        LocaleUtils.toLocale("EN");
    }

    @Test
    public void testToLocale_ValidLanguageAndCountry() {
        Locale locale = LocaleUtils.toLocale("en_US");
        assertEquals("en", locale.getLanguage());
        assertEquals("US", locale.getCountry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidCountryFormat() {
        LocaleUtils.toLocale("en_us");
    }

    @Test
    public void testToLocale_ValidLanguageCountryVariant() {
        Locale locale = LocaleUtils.toLocale("en_US_WIN");
        assertEquals("en", locale.getLanguage());
        assertEquals("US", locale.getCountry());
        assertEquals("WIN", locale.getVariant());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidVariantFormat() {
        LocaleUtils.toLocale("en_US_win");
    }

    @Test
    public void testToLocale_ValidCountryOnly() {
        Locale locale = LocaleUtils.toLocale("_US");
        assertEquals("", locale.getLanguage());
        assertEquals("US", locale.getCountry());
    }

    @Test
    public void testToLocale_ValidCountryAndVariant() {
        Locale locale = LocaleUtils.toLocale("_US_WIN");
        assertEquals("", locale.getLanguage());
        assertEquals("US", locale.getCountry());
        assertEquals("WIN", locale.getVariant());
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        Locale locale = new Locale("en", "US");
        List<Locale> list = LocaleUtils.localeLookupList(locale);
        assertEquals(3, list.size());
        assertEquals(locale, list.get(0));
        assertEquals(new Locale("en", ""), list.get(2));
    }

    @Test
    public void testLocaleLookupList_WithDefaultLocale() {
        Locale locale = new Locale("en", "US", "WIN");
        Locale defaultLocale = new Locale("en", "US");
        List<Locale> list = LocaleUtils.localeLookupList(locale, defaultLocale);
        assertEquals(4, list.size());
        assertEquals(locale, list.get(0));
        assertEquals(defaultLocale, list.get(1));
    }

    @Test
    public void testAvailableLocaleList() {
        List<Locale> locales = LocaleUtils.availableLocaleList();
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testAvailableLocaleSet() {
        Set<Locale> locales = LocaleUtils.availableLocaleSet();
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testIsAvailableLocale() {
        Locale locale = Locale.US;
        assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testIsAvailableLocale_NotAvailable() {
        Locale locale = new Locale("xx", "YY");
        assertFalse(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> locales = LocaleUtils.languagesByCountry("US");
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testLanguagesByCountry_NullInput() {
        List<Locale> locales = LocaleUtils.languagesByCountry(null);
        assertNotNull(locales);
        assertEquals(0, locales.size());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> locales = LocaleUtils.countriesByLanguage("en");
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testCountriesByLanguage_NullInput() {
        List<Locale> locales = LocaleUtils.countriesByLanguage(null);
        assertNotNull(locales);
        assertEquals(0, locales.size());
    }
}
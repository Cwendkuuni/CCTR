package org.apache.commons.lang;

import org.apache.commons.lang.LocaleUtils;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

public class LocaleUtilsTest {

    private static Locale testLocale;
    private static Locale defaultLocale;

    @BeforeClass
    public static void setUp() {
        testLocale = new Locale("fr", "CA", "xxx");
        defaultLocale = new Locale("en");
    }

    @Test
    public void testToLocale_ValidLanguage() {
        Locale locale = LocaleUtils.toLocale("en");
        assertNotNull(locale);
        assertEquals("en", locale.getLanguage());
        assertEquals("", locale.getCountry());
    }

    @Test
    public void testToLocale_ValidLanguageCountry() {
        Locale locale = LocaleUtils.toLocale("en_GB");
        assertNotNull(locale);
        assertEquals("en", locale.getLanguage());
        assertEquals("GB", locale.getCountry());
    }

    @Test
    public void testToLocale_ValidLanguageCountryVariant() {
        Locale locale = LocaleUtils.toLocale("en_GB_xxx");
        assertNotNull(locale);
        assertEquals("en", locale.getLanguage());
        assertEquals("GB", locale.getCountry());
        assertEquals("xxx", locale.getVariant());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidFormat() {
        LocaleUtils.toLocale("invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLanguage() {
        LocaleUtils.toLocale("EN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidCountry() {
        LocaleUtils.toLocale("en_gb");
    }

    @Test
    public void testToLocale_NullInput() {
        assertNull(LocaleUtils.toLocale(null));
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        List<Locale> locales = LocaleUtils.localeLookupList(testLocale);
        assertEquals(3, locales.size());
        assertEquals(testLocale, locales.get(0));
        assertEquals(new Locale("fr", "CA"), locales.get(1));
        assertEquals(new Locale("fr"), locales.get(2));
    }

    @Test
    public void testLocaleLookupList_LocaleWithDefault() {
        List<Locale> locales = LocaleUtils.localeLookupList(testLocale, defaultLocale);
        assertEquals(4, locales.size());
        assertEquals(testLocale, locales.get(0));
        assertEquals(new Locale("fr", "CA"), locales.get(1));
        assertEquals(new Locale("fr"), locales.get(2));
        assertEquals(defaultLocale, locales.get(3));
    }

    @Test
    public void testLocaleLookupList_NullLocale() {
        List<Locale> locales = LocaleUtils.localeLookupList(null, defaultLocale);
        assertEquals(1, locales.size());
        assertEquals(defaultLocale, locales.get(0));
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
        assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
        assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx", "YY")));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        assertNotNull(languages);
        assertTrue(languages.size() > 0);
    }

    @Test
    public void testLanguagesByCountry_NullCountry() {
        List<Locale> languages = LocaleUtils.languagesByCountry(null);
        assertNotNull(languages);
        assertEquals(0, languages.size());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        assertNotNull(countries);
        assertTrue(countries.size() > 0);
    }

    @Test
    public void testCountriesByLanguage_NullLanguage() {
        List<Locale> countries = LocaleUtils.countriesByLanguage(null);
        assertNotNull(countries);
        assertEquals(0, countries.size());
    }
}
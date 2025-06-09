package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LocaleUtilsTest {

    @Before
    public void setUp() {
        // Any setup required before each test
    }

    @Test
    public void testToLocale_ValidLocaleString() {
        assertEquals(new Locale("en", "GB"), LocaleUtils.toLocale("en_GB"));
        assertEquals(new Locale("en", "", "xxx"), LocaleUtils.toLocale("en_GB_xxx"));
        assertEquals(new Locale("en"), LocaleUtils.toLocale("en"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLocaleString() {
        LocaleUtils.toLocale("en_gb"); // Invalid country code case
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLocaleStringLength() {
        LocaleUtils.toLocale("e"); // Invalid length
    }

    @Test
    public void testToLocale_NullInput() {
        assertNull(LocaleUtils.toLocale(null));
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        List<Locale> lookupList = LocaleUtils.localeLookupList(new Locale("fr", "CA", "xxx"));
        assertEquals(3, lookupList.size());
        assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        assertEquals(new Locale("fr"), lookupList.get(2));
    }

    @Test
    public void testLocaleLookupList_WithDefaultLocale() {
        List<Locale> lookupList = LocaleUtils.localeLookupList(new Locale("fr", "CA", "xxx"), new Locale("en"));
        assertEquals(4, lookupList.size());
        assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        assertEquals(new Locale("fr"), lookupList.get(2));
        assertEquals(new Locale("en"), lookupList.get(3));
    }

    @Test
    public void testLocaleLookupList_NullLocale() {
        List<Locale> lookupList = LocaleUtils.localeLookupList(null, new Locale("en"));
        assertEquals(1, lookupList.size());
        assertEquals(new Locale("en"), lookupList.get(0));
    }

    @Test
    public void testAvailableLocaleList() {
        List<Locale> availableLocales = LocaleUtils.availableLocaleList();
        assertNotNull(availableLocales);
        assertTrue(availableLocales.size() > 0);
    }

    @Test
    public void testAvailableLocaleSet() {
        Set<Locale> availableLocales = LocaleUtils.availableLocaleSet();
        assertNotNull(availableLocales);
        assertTrue(availableLocales.size() > 0);
    }

    @Test
    public void testIsAvailableLocale() {
        assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
        assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx", "YY")));
    }

    @Test
    public void testLanguagesByCountry_ValidCountryCode() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        assertNotNull(languages);
        assertTrue(languages.size() > 0);
    }

    @Test
    public void testLanguagesByCountry_NullCountryCode() {
        List<Locale> languages = LocaleUtils.languagesByCountry(null);
        assertNotNull(languages);
        assertTrue(languages.isEmpty());
    }

    @Test
    public void testCountriesByLanguage_ValidLanguageCode() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        assertNotNull(countries);
        assertTrue(countries.size() > 0);
    }

    @Test
    public void testCountriesByLanguage_NullLanguageCode() {
        List<Locale> countries = LocaleUtils.countriesByLanguage(null);
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }
}
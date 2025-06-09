package org.apache.commons.lang3;

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
        // Initialization code if needed
    }

    @After
    public void tearDown() {
        // Cleanup code if needed
    }

    @Test
    public void testToLocale() {
        assertEquals(new Locale("en"), LocaleUtils.toLocale("en"));
        assertEquals(new Locale("en", "GB"), LocaleUtils.toLocale("en_GB"));
        assertEquals(new Locale("en", "GB", "xxx"), LocaleUtils.toLocale("en_GB_xxx"));

        try {
            LocaleUtils.toLocale("EN");
            fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_gb");
            fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_GB_");
            fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_GB_xx");
            fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testLocaleLookupList() {
        Locale locale = new Locale("fr", "CA", "xxx");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale);
        assertEquals(3, lookupList.size());
        assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        assertEquals(new Locale("fr"), lookupList.get(2));

        locale = new Locale("fr", "CA");
        lookupList = LocaleUtils.localeLookupList(locale);
        assertEquals(2, lookupList.size());
        assertEquals(new Locale("fr", "CA"), lookupList.get(0));
        assertEquals(new Locale("fr"), lookupList.get(1));

        locale = new Locale("fr");
        lookupList = LocaleUtils.localeLookupList(locale);
        assertEquals(1, lookupList.size());
        assertEquals(new Locale("fr"), lookupList.get(0));
    }

    @Test
    public void testLocaleLookupListWithDefault() {
        Locale locale = new Locale("fr", "CA", "xxx");
        Locale defaultLocale = new Locale("en");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        assertEquals(4, lookupList.size());
        assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        assertEquals(new Locale("fr"), lookupList.get(2));
        assertEquals(new Locale("en"), lookupList.get(3));

        locale = new Locale("fr", "CA");
        lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        assertEquals(3, lookupList.size());
        assertEquals(new Locale("fr", "CA"), lookupList.get(0));
        assertEquals(new Locale("fr"), lookupList.get(1));
        assertEquals(new Locale("en"), lookupList.get(2));

        locale = new Locale("fr");
        lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        assertEquals(2, lookupList.size());
        assertEquals(new Locale("fr"), lookupList.get(0));
        assertEquals(new Locale("en"), lookupList.get(1));
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
        assertTrue(LocaleUtils.isAvailableLocale(Locale.UK));
        assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx", "YY")));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        assertNotNull(languages);
        assertTrue(languages.size() > 0);

        languages = LocaleUtils.languagesByCountry(null);
        assertTrue(languages.isEmpty());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        assertNotNull(countries);
        assertTrue(countries.size() > 0);

        countries = LocaleUtils.countriesByLanguage(null);
        assertTrue(countries.isEmpty());
    }
}
package org.apache.commons.lang3;

import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LocaleUtilsTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup before all tests
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Cleanup after all tests
    }

    @Before
    public void setUp() throws Exception {
        // Setup before each test
    }

    @After
    public void tearDown() throws Exception {
        // Cleanup after each test
    }

    @Test
    public void testToLocale_Null() {
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

    @Test
    public void testToLocale_ValidLanguageAndCountry() {
        Locale locale = LocaleUtils.toLocale("en_US");
        assertEquals("en", locale.getLanguage());
        assertEquals("US", locale.getCountry());
    }

    @Test
    public void testToLocale_ValidLanguageCountryVariant() {
        Locale locale = LocaleUtils.toLocale("en_US_WIN");
        assertEquals("en", locale.getLanguage());
        assertEquals("US", locale.getCountry());
        assertEquals("WIN", locale.getVariant());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidFormatLanguageCountry() {
        LocaleUtils.toLocale("en_us");
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        Locale locale = new Locale("en", "US");
        List<Locale> list = LocaleUtils.localeLookupList(locale);
        assertEquals(3, list.size());
        assertEquals(locale, list.get(0));
    }

    @Test
    public void testLocaleLookupList_LocaleWithVariant() {
        Locale locale = new Locale("en", "US", "WIN");
        List<Locale> list = LocaleUtils.localeLookupList(locale);
        assertEquals(4, list.size());
        assertEquals(locale, list.get(0));
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
        Locale locale = new Locale("en", "US");
        assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> locales = LocaleUtils.languagesByCountry("US");
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> locales = LocaleUtils.countriesByLanguage("en");
        assertNotNull(locales);
        assertTrue(locales.size() > 0);
    }

    @Test
    public void testLanguagesByCountry_Null() {
        List<Locale> locales = LocaleUtils.languagesByCountry(null);
        assertNotNull(locales);
        assertTrue(locales.isEmpty());
    }

    @Test
    public void testCountriesByLanguage_Null() {
        List<Locale> locales = LocaleUtils.countriesByLanguage(null);
        assertNotNull(locales);
        assertTrue(locales.isEmpty());
    }
}
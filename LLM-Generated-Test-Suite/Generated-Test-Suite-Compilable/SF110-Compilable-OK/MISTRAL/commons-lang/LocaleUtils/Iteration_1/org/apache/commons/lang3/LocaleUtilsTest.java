package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LocaleUtilsTest {

    @Before
    public void setUp() {
        // Any setup code if needed
    }

    @After
    public void tearDown() {
        // Any teardown code if needed
    }

    @Test
    public void testToLocale_NullInput() {
        Assert.assertNull(LocaleUtils.toLocale(null));
    }

    @Test
    public void testToLocale_EmptyInput() {
        Locale locale = LocaleUtils.toLocale("");
        Assert.assertEquals("", locale.getLanguage());
        Assert.assertEquals("", locale.getCountry());
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
    public void testToLocale_LanguageOnly() {
        Locale locale = LocaleUtils.toLocale("en");
        Assert.assertEquals("en", locale.getLanguage());
        Assert.assertEquals("", locale.getCountry());
    }

    @Test
    public void testToLocale_LanguageAndCountry() {
        Locale locale = LocaleUtils.toLocale("en_US");
        Assert.assertEquals("en", locale.getLanguage());
        Assert.assertEquals("US", locale.getCountry());
    }

    @Test
    public void testToLocale_LanguageCountryAndVariant() {
        Locale locale = LocaleUtils.toLocale("en_US_WIN");
        Assert.assertEquals("en", locale.getLanguage());
        Assert.assertEquals("US", locale.getCountry());
        Assert.assertEquals("WIN", locale.getVariant());
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        Locale locale = new Locale("en", "US");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(2, lookupList.size());
        Assert.assertEquals(locale, lookupList.get(0));
        Assert.assertEquals(new Locale("en", ""), lookupList.get(1));
    }

    @Test
    public void testLocaleLookupList_WithDefaultLocale() {
        Locale locale = new Locale("en", "US");
        Locale defaultLocale = new Locale("fr", "FR");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        Assert.assertEquals(3, lookupList.size());
        Assert.assertEquals(locale, lookupList.get(0));
        Assert.assertEquals(new Locale("en", ""), lookupList.get(1));
        Assert.assertEquals(defaultLocale, lookupList.get(2));
    }

    @Test
    public void testAvailableLocaleList() {
        List<Locale> availableLocales = LocaleUtils.availableLocaleList();
        Assert.assertNotNull(availableLocales);
        Assert.assertFalse(availableLocales.isEmpty());
    }

    @Test
    public void testAvailableLocaleSet() {
        Set<Locale> availableLocales = LocaleUtils.availableLocaleSet();
        Assert.assertNotNull(availableLocales);
        Assert.assertFalse(availableLocales.isEmpty());
    }

    @Test
    public void testIsAvailableLocale() {
        Locale locale = new Locale("en", "US");
        Assert.assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        Assert.assertNotNull(languages);
        Assert.assertFalse(languages.isEmpty());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }
}
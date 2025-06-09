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
    public void testToLocale_ValidLocaleString() {
        Locale locale = LocaleUtils.toLocale("en_GB");
        Assert.assertEquals(new Locale("en", "GB"), locale);
    }

    @Test
    public void testToLocale_ValidLocaleStringWithVariant() {
        Locale locale = LocaleUtils.toLocale("en_GB_xxx");
        Assert.assertEquals(new Locale("en", "GB", "xxx"), locale);
    }

    @Test
    public void testToLocale_ValidLocaleStringWithLanguageOnly() {
        Locale locale = LocaleUtils.toLocale("en");
        Assert.assertEquals(new Locale("en"), locale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLocaleString() {
        LocaleUtils.toLocale("EN_gb");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLocaleStringLength() {
        LocaleUtils.toLocale("e");
    }

    @Test
    public void testLocaleLookupList_WithLocale() {
        Locale locale = new Locale("fr", "CA", "xxx");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(3, lookupList.size());
        Assert.assertEquals(locale, lookupList.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        Assert.assertEquals(new Locale("fr"), lookupList.get(2));
    }

    @Test
    public void testLocaleLookupList_WithLocaleAndDefaultLocale() {
        Locale locale = new Locale("fr", "CA", "xxx");
        Locale defaultLocale = new Locale("en");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        Assert.assertEquals(4, lookupList.size());
        Assert.assertEquals(locale, lookupList.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        Assert.assertEquals(new Locale("fr"), lookupList.get(2));
        Assert.assertEquals(defaultLocale, lookupList.get(3));
    }

    @Test
    public void testLocaleLookupList_WithNullLocale() {
        Locale defaultLocale = new Locale("en");
        List<Locale> lookupList = LocaleUtils.localeLookupList(null, defaultLocale);
        Assert.assertEquals(1, lookupList.size());
        Assert.assertEquals(defaultLocale, lookupList.get(0));
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
    public void testIsAvailableLocale_AvailableLocale() {
        Locale locale = Locale.US;
        Assert.assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testIsAvailableLocale_UnavailableLocale() {
        Locale locale = new Locale("xx", "YY");
        Assert.assertFalse(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testLanguagesByCountry_ValidCountryCode() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        Assert.assertNotNull(languages);
        Assert.assertFalse(languages.isEmpty());
    }

    @Test
    public void testLanguagesByCountry_InvalidCountryCode() {
        List<Locale> languages = LocaleUtils.languagesByCountry("XX");
        Assert.assertTrue(languages.isEmpty());
    }

    @Test
    public void testCountriesByLanguage_ValidLanguageCode() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }

    @Test
    public void testCountriesByLanguage_InvalidLanguageCode() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("xx");
        Assert.assertTrue(countries.isEmpty());
    }
}
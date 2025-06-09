package org.apache.commons.lang3;

import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

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
    public void testToLocale_NullInput() {
        Assert.assertNull(LocaleUtils.toLocale(null));
    }

    @Test
    public void testToLocale_EmptyString() {
        Locale locale = LocaleUtils.toLocale("");
        Assert.assertEquals(new Locale("", ""), locale);
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
        Assert.assertEquals(new Locale("en"), locale);
    }

    @Test
    public void testToLocale_ValidLanguageAndCountry() {
        Locale locale = LocaleUtils.toLocale("en_US");
        Assert.assertEquals(new Locale("en", "US"), locale);
    }

    @Test
    public void testToLocale_ValidLanguageCountryVariant() {
        Locale locale = LocaleUtils.toLocale("en_US_WIN");
        Assert.assertEquals(new Locale("en", "US", "WIN"), locale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToLocale_InvalidLanguageCountryVariant() {
        LocaleUtils.toLocale("en_us_win");
    }

    @Test
    public void testToLocale_ValidCountryOnly() {
        Locale locale = LocaleUtils.toLocale("_US");
        Assert.assertEquals(new Locale("", "US"), locale);
    }

    @Test
    public void testToLocale_ValidCountryAndVariant() {
        Locale locale = LocaleUtils.toLocale("_US_WIN");
        Assert.assertEquals(new Locale("", "US", "WIN"), locale);
    }

    @Test
    public void testLocaleLookupList_SingleLocale() {
        Locale locale = new Locale("en", "US");
        List<Locale> list = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(locale, list.get(0));
    }

    @Test
    public void testLocaleLookupList_LocaleWithVariant() {
        Locale locale = new Locale("en", "US", "WIN");
        List<Locale> list = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(locale, list.get(0));
    }

    @Test
    public void testAvailableLocaleList() {
        List<Locale> locales = LocaleUtils.availableLocaleList();
        Assert.assertNotNull(locales);
        Assert.assertTrue(locales.size() > 0);
    }

    @Test
    public void testAvailableLocaleSet() {
        Set<Locale> locales = LocaleUtils.availableLocaleSet();
        Assert.assertNotNull(locales);
        Assert.assertTrue(locales.size() > 0);
    }

    @Test
    public void testIsAvailableLocale() {
        Locale locale = Locale.US;
        Assert.assertTrue(LocaleUtils.isAvailableLocale(locale));
    }

    @Test
    public void testLanguagesByCountry_ValidCountry() {
        List<Locale> locales = LocaleUtils.languagesByCountry("US");
        Assert.assertNotNull(locales);
    }

    @Test
    public void testLanguagesByCountry_InvalidCountry() {
        List<Locale> locales = LocaleUtils.languagesByCountry("XX");
        Assert.assertNotNull(locales);
        Assert.assertTrue(locales.isEmpty());
    }

    @Test
    public void testCountriesByLanguage_ValidLanguage() {
        List<Locale> locales = LocaleUtils.countriesByLanguage("en");
        Assert.assertNotNull(locales);
    }

    @Test
    public void testCountriesByLanguage_InvalidLanguage() {
        List<Locale> locales = LocaleUtils.countriesByLanguage("xx");
        Assert.assertNotNull(locales);
        Assert.assertTrue(locales.isEmpty());
    }
}
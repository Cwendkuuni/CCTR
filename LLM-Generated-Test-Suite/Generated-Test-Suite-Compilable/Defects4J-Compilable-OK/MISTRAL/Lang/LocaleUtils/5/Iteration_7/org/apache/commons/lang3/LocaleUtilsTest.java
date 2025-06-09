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
        // Set up any necessary preconditions for the tests
    }

    @After
    public void tearDown() {
        // Clean up after tests if necessary
    }

    @Test
    public void testToLocale() {
        Assert.assertEquals(new Locale("en"), LocaleUtils.toLocale("en"));
        Assert.assertEquals(new Locale("en", "GB"), LocaleUtils.toLocale("en_GB"));
        Assert.assertEquals(new Locale("en", "GB", "xxx"), LocaleUtils.toLocale("en_GB_xxx"));

        try {
            LocaleUtils.toLocale("EN");
            Assert.fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_gb");
            Assert.fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_GB_");
            Assert.fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            LocaleUtils.toLocale("en_GB_x");
            Assert.fail("Expected IllegalArgumentException for invalid locale format");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testLocaleLookupList() {
        Locale locale = new Locale("fr", "CA", "xxx");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(3, lookupList.size());
        Assert.assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        Assert.assertEquals(new Locale("fr"), lookupList.get(2));

        locale = new Locale("fr", "CA");
        lookupList = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(2, lookupList.size());
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(0));
        Assert.assertEquals(new Locale("fr"), lookupList.get(1));

        locale = new Locale("fr");
        lookupList = LocaleUtils.localeLookupList(locale);
        Assert.assertEquals(1, lookupList.size());
        Assert.assertEquals(new Locale("fr"), lookupList.get(0));
    }

    @Test
    public void testLocaleLookupListWithDefault() {
        Locale locale = new Locale("fr", "CA", "xxx");
        Locale defaultLocale = new Locale("en");
        List<Locale> lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        Assert.assertEquals(4, lookupList.size());
        Assert.assertEquals(new Locale("fr", "CA", "xxx"), lookupList.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(1));
        Assert.assertEquals(new Locale("fr"), lookupList.get(2));
        Assert.assertEquals(new Locale("en"), lookupList.get(3));

        locale = new Locale("fr", "CA");
        lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        Assert.assertEquals(3, lookupList.size());
        Assert.assertEquals(new Locale("fr", "CA"), lookupList.get(0));
        Assert.assertEquals(new Locale("fr"), lookupList.get(1));
        Assert.assertEquals(new Locale("en"), lookupList.get(2));

        locale = new Locale("fr");
        lookupList = LocaleUtils.localeLookupList(locale, defaultLocale);
        Assert.assertEquals(2, lookupList.size());
        Assert.assertEquals(new Locale("fr"), lookupList.get(0));
        Assert.assertEquals(new Locale("en"), lookupList.get(1));
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
        Assert.assertTrue(LocaleUtils.isAvailableLocale(Locale.ENGLISH));
        Assert.assertTrue(LocaleUtils.isAvailableLocale(Locale.FRENCH));
        Assert.assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx", "YY")));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> languages = LocaleUtils.languagesByCountry("US");
        Assert.assertNotNull(languages);
        Assert.assertFalse(languages.isEmpty());

        languages = LocaleUtils.languagesByCountry("XX");
        Assert.assertTrue(languages.isEmpty());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> countries = LocaleUtils.countriesByLanguage("en");
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());

        countries = LocaleUtils.countriesByLanguage("xx");
        Assert.assertTrue(countries.isEmpty());
    }
}
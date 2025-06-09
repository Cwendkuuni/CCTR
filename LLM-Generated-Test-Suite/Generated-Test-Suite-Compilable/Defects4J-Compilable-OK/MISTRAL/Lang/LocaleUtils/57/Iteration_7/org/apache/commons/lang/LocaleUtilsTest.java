package org.apache.commons.lang;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LocaleUtilsTest {

    private LocaleUtils localeUtils;

    @Before
    public void setUp() {
        localeUtils = new LocaleUtils();
    }

    @Test
    public void testToLocale() {
        Assert.assertNull(LocaleUtils.toLocale(null));

        Locale locale = LocaleUtils.toLocale("en");
        Assert.assertEquals(new Locale("en", ""), locale);

        locale = LocaleUtils.toLocale("en_GB");
        Assert.assertEquals(new Locale("en", "GB"), locale);

        locale = LocaleUtils.toLocale("en_GB_xxx");
        Assert.assertEquals(new Locale("en", "GB", "xxx"), locale);

        try {
            LocaleUtils.toLocale("EN");
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            LocaleUtils.toLocale("en_gb");
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            LocaleUtils.toLocale("en_GB_");
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testLocaleLookupList() {
        List<Locale> list = LocaleUtils.localeLookupList(new Locale("fr", "CA", "xxx"));
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(new Locale("fr", "CA", "xxx"), list.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), list.get(1));
        Assert.assertEquals(new Locale("fr"), list.get(2));

        list = LocaleUtils.localeLookupList(new Locale("fr", "CA", "xxx"), new Locale("en"));
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(new Locale("fr", "CA", "xxx"), list.get(0));
        Assert.assertEquals(new Locale("fr", "CA"), list.get(1));
        Assert.assertEquals(new Locale("fr"), list.get(2));
        Assert.assertEquals(new Locale("en"), list.get(3));

        list = LocaleUtils.localeLookupList(null, new Locale("en"));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(new Locale("en"), list.get(0));
    }

    @Test
    public void testAvailableLocaleList() {
        List<Locale> list = LocaleUtils.availableLocaleList();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testAvailableLocaleSet() {
        Set<Locale> set = LocaleUtils.availableLocaleSet();
        Assert.assertNotNull(set);
        Assert.assertFalse(set.isEmpty());
    }

    @Test
    public void testIsAvailableLocale() {
        Assert.assertTrue(LocaleUtils.isAvailableLocale(Locale.US));
        Assert.assertFalse(LocaleUtils.isAvailableLocale(new Locale("xx", "YY")));
    }

    @Test
    public void testLanguagesByCountry() {
        List<Locale> list = LocaleUtils.languagesByCountry("US");
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        list = LocaleUtils.languagesByCountry(null);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testCountriesByLanguage() {
        List<Locale> list = LocaleUtils.countriesByLanguage("en");
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        list = LocaleUtils.countriesByLanguage(null);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }
}
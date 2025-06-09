package org.apache.commons.lang3.text.translate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.EnumSet;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
    }

    @Test
    public void testConstructorWithOptions() {
        NumericEntityUnescaper customUnescaper = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        assertTrue(customUnescaper.isSet(NumericEntityUnescaper.OPTION.semiColonOptional));
        assertFalse(customUnescaper.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
    }

    @Test
    public void testConstructorWithoutOptions() {
        assertTrue(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
    }

    @Test
    public void testIsSet() {
        assertTrue(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
        assertFalse(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonOptional));
    }

    @Test
    public void testTranslateDecimalEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65;", 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithoutSemiColon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithoutSemiColonOptional() throws IOException {
        NumericEntityUnescaper customUnescaper = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        StringWriter out = new StringWriter();
        int result = customUnescaper.translate("&#65", 0, out);
        assertEquals(4, result);
        assertEquals("A", out.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateEntityWithoutSemiColonError() throws IOException {
        NumericEntityUnescaper customUnescaper = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        StringWriter out = new StringWriter();
        customUnescaper.translate("&#65", 0, out);
    }

    @Test
    public void testTranslateEntityWithInvalidNumber() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xyz;", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithLargeValue() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#119558;", 0, out);
        assertEquals(9, result);
        assertEquals("\uD80C\uDC3E", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
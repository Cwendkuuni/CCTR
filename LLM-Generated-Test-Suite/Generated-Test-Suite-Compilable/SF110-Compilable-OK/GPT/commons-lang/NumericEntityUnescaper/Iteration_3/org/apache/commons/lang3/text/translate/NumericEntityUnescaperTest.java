package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaperSemiColonRequired;
    private NumericEntityUnescaper unescaperSemiColonOptional;
    private NumericEntityUnescaper unescaperErrorIfNoSemiColon;

    @Before
    public void setUp() {
        unescaperSemiColonRequired = new NumericEntityUnescaper(OPTION.semiColonRequired);
        unescaperSemiColonOptional = new NumericEntityUnescaper(OPTION.semiColonOptional);
        unescaperErrorIfNoSemiColon = new NumericEntityUnescaper(OPTION.errorIfNoSemiColon);
    }

    @Test
    public void testIsSet() {
        assertTrue(unescaperSemiColonRequired.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperSemiColonRequired.isSet(OPTION.semiColonOptional));
        assertFalse(unescaperSemiColonRequired.isSet(OPTION.errorIfNoSemiColon));

        assertTrue(unescaperSemiColonOptional.isSet(OPTION.semiColonOptional));
        assertFalse(unescaperSemiColonOptional.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperSemiColonOptional.isSet(OPTION.errorIfNoSemiColon));

        assertTrue(unescaperErrorIfNoSemiColon.isSet(OPTION.errorIfNoSemiColon));
        assertFalse(unescaperErrorIfNoSemiColon.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperErrorIfNoSemiColon.isSet(OPTION.semiColonOptional));
    }

    @Test
    public void testTranslateDecimalEntityWithSemiColon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("&#65;", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateDecimalEntityWithoutSemiColon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonOptional.translate("&#65", 0, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntityWithSemiColon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("&#x41;", 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntityWithoutSemiColon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonOptional.translate("&#x41", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateEntityWithoutSemiColonError() throws IOException {
        StringWriter writer = new StringWriter();
        unescaperErrorIfNoSemiColon.translate("&#65", 0, writer);
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("&#xyz;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityOutOfRange() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("&#1114112;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityWithHighCodePoint() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("&#128512;", 0, writer);
        assertEquals(8, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperSemiColonRequired.translate("A", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }
}
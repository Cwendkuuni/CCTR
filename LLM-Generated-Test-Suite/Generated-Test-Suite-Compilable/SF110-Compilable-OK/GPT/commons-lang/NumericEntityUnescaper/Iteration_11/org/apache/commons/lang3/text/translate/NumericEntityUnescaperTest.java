package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaperWithSemiColonRequired;
    private NumericEntityUnescaper unescaperWithSemiColonOptional;
    private NumericEntityUnescaper unescaperWithErrorIfNoSemiColon;

    @Before
    public void setUp() {
        unescaperWithSemiColonRequired = new NumericEntityUnescaper(OPTION.semiColonRequired);
        unescaperWithSemiColonOptional = new NumericEntityUnescaper(OPTION.semiColonOptional);
        unescaperWithErrorIfNoSemiColon = new NumericEntityUnescaper(OPTION.errorIfNoSemiColon);
    }

    @Test
    public void testIsSet() {
        assertTrue(unescaperWithSemiColonRequired.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperWithSemiColonRequired.isSet(OPTION.semiColonOptional));
        assertFalse(unescaperWithSemiColonRequired.isSet(OPTION.errorIfNoSemiColon));

        assertTrue(unescaperWithSemiColonOptional.isSet(OPTION.semiColonOptional));
        assertFalse(unescaperWithSemiColonOptional.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperWithSemiColonOptional.isSet(OPTION.errorIfNoSemiColon));

        assertTrue(unescaperWithErrorIfNoSemiColon.isSet(OPTION.errorIfNoSemiColon));
        assertFalse(unescaperWithErrorIfNoSemiColon.isSet(OPTION.semiColonRequired));
        assertFalse(unescaperWithErrorIfNoSemiColon.isSet(OPTION.semiColonOptional));
    }

    @Test
    public void testTranslateWithSemiColonRequired() throws Exception {
        Writer writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#65;", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());

        writer = new StringWriter();
        result = unescaperWithSemiColonRequired.translate("&#x41;", 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());

        writer = new StringWriter();
        result = unescaperWithSemiColonRequired.translate("&#65", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateWithSemiColonOptional() throws Exception {
        Writer writer = new StringWriter();
        int result = unescaperWithSemiColonOptional.translate("&#65;", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());

        writer = new StringWriter();
        result = unescaperWithSemiColonOptional.translate("&#x41;", 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());

        writer = new StringWriter();
        result = unescaperWithSemiColonOptional.translate("&#65", 0, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateWithErrorIfNoSemiColon() throws Exception {
        Writer writer = new StringWriter();
        unescaperWithErrorIfNoSemiColon.translate("&#65", 0, writer);
    }

    @Test
    public void testTranslateInvalidNumericEntity() throws Exception {
        Writer writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#xyz;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateOutOfRangeEntity() throws Exception {
        Writer writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#1114112;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateSurrogatePair() throws Exception {
        Writer writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#128512;", 0, writer);
        assertEquals(8, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }
}
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

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
    public void testTranslateWithSemiColonRequired() throws IOException {
        StringWriter writer = new StringWriter();
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
    public void testTranslateWithSemiColonOptional() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperWithSemiColonOptional.translate("&#65;", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());

        writer = new StringWriter();
        result = unescaperWithSemiColonOptional.translate("&#65", 0, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateWithErrorIfNoSemiColon() throws IOException {
        StringWriter writer = new StringWriter();
        unescaperWithErrorIfNoSemiColon.translate("&#65", 0, writer);
    }

    @Test
    public void testTranslateInvalidNumericEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#xyz;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateOutOfRangeEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("&#65536;", 0, writer);
        assertEquals(8, result);
        assertEquals("\uD800\uDC00", writer.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaperWithSemiColonRequired.translate("A", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }
}
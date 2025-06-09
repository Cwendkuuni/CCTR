package org.apache.commons.lang3.text.translate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
    }

    @Test
    public void testConstructorWithOptions() {
        NumericEntityUnescaper unescaperWithOptions = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        assertTrue(unescaperWithOptions.isSet(NumericEntityUnescaper.OPTION.semiColonOptional));
    }

    @Test
    public void testConstructorWithoutOptions() {
        NumericEntityUnescaper unescaperWithoutOptions = new NumericEntityUnescaper();
        assertTrue(unescaperWithoutOptions.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
    }

    @Test
    public void testIsSet() {
        assertTrue(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
        assertFalse(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonOptional));
    }

    @Test
    public void testTranslateDecimal() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65;", 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHex() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateWithoutSemiColon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateWithSemiColonOptional() throws IOException {
        NumericEntityUnescaper unescaperWithOptions = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        StringWriter out = new StringWriter();
        int result = unescaperWithOptions.translate("&#65", 0, out);
        assertEquals(4, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateWithErrorIfNoSemiColon() {
        NumericEntityUnescaper unescaperWithOptions = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        StringWriter out = new StringWriter();
        try {
            unescaperWithOptions.translate("&#65", 0, out);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Semi-colon required at end of numeric entity", e.getMessage());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xyz;", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityGreaterThan65535() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#1114111;", 0, out);
        assertEquals(10, result);
        assertEquals("\uDBFF\uDCFF", out.toString());
    }
}
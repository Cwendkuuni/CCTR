package org.apache.commons.lang3.text.translate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;
    private StringWriter out;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
        out = new StringWriter();
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
        assertFalse(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonOptional));
        assertTrue(unescaper.isSet(NumericEntityUnescaper.OPTION.semiColonRequired));
    }

    @Test
    public void testTranslateDecimal() throws IOException {
        int result = unescaper.translate("&#65;", 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexadecimal() throws IOException {
        int result = unescaper.translate("&#x41;", 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateWithoutSemiColon() throws IOException {
        unescaper = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        int result = unescaper.translate("&#65", 0, out);
        assertEquals(4, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateWithErrorIfNoSemiColon() {
        unescaper = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        try {
            unescaper.translate("&#65", 0, out);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Semi-colon required at end of numeric entity", e.getMessage());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        int result = unescaper.translate("&#xyz;", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityGreaterThan65535() throws IOException {
        int result = unescaper.translate("&#x10FFFF;", 0, out);
        assertEquals(9, result);
        assertEquals("\uD83D\uDCFF", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        int result = unescaper.translate("abc", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
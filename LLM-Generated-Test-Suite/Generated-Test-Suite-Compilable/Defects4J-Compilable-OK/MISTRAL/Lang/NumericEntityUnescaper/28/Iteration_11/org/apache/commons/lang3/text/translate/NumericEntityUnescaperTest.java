package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
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
    public void testTranslateDecimalEntity() throws IOException {
        String input = "&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(5, consumed);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(6, consumed);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#invalid;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "not an entity";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithExtraCharacters() throws IOException {
        String input = "&#65;extra";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(5, consumed);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLeadingCharacters() throws IOException {
        String input = "leading&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 7, out);
        assertEquals(5, consumed);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidHex() throws IOException {
        String input = "&#xG1;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidDecimal() throws IOException {
        String input = "&#G1;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithEmptyValue() throws IOException {
        String input = "&#;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithMissingSemicolon() throws IOException {
        String input = "&#65";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals(0, consumed);
        assertEquals("", out.toString());
    }
}
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

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
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateIncompleteEntity() throws IOException {
        String input = "&#x4";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "abc";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithExtraCharacters() throws IOException {
        String input = "&#65;xyz";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLeadingWhitespace() throws IOException {
        String input = " &#65;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 1, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithTrailingWhitespace() throws IOException {
        String input = "&#65; ";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithMixedCaseHex() throws IOException {
        String input = "&#X41;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithUpperCaseHex() throws IOException {
        String input = "&#X41;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLowerCaseHex() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithZero() throws IOException {
        String input = "&#0;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(4, result);
        assertEquals("\u0000", out.toString());
    }

    @Test
    public void testTranslateEntityWithLargeDecimal() throws IOException {
        String input = "&#123456;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(9, result);
        assertEquals("\u1E240", out.toString());
    }

    @Test
    public void testTranslateEntityWithLargeHex() throws IOException {
        String input = "&#x1E240;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(10, result);
        assertEquals("\u1E240", out.toString());
    }
}
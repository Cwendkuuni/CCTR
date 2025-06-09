package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "A";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateIncompleteEntity() throws IOException {
        String input = "&#";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithNoSemicolon() throws IOException {
        String input = "&#65";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithExtraCharacters() throws IOException {
        String input = "&#65;Extra";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateMultipleEntities() throws IOException {
        String input = "&#65;&#66;";
        Writer out = new StringWriter();
        int result1 = unescaper.translate(input, 0, out);
        int result2 = unescaper.translate(input, result1, out);
        assertEquals(5, result1);
        assertEquals(5, result2);
        assertEquals("AB", out.toString());
    }
}
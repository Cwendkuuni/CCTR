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
    public void testTranslateHexEntityLowerCase() throws IOException {
        String input = "&#x41;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntityUpperCase() throws IOException {
        String input = "&#X41;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xyz;";
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
        String input = "&#123";
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
        String input = "&#65;&#66;&#67;";
        Writer out = new StringWriter();
        int index = 0;
        while (index < input.length()) {
            int result = unescaper.translate(input, index, out);
            if (result == 0) {
                break;
            }
            index += result;
        }
        assertEquals("ABC", out.toString());
    }
}
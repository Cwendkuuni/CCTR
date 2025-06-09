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
        String input = "&notanentity;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithExtraCharacters() throws IOException {
        String input = "&#65;extra";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityAtEndOfString() throws IOException {
        String input = "text&#65;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 4, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLeadingSpaces() throws IOException {
        String input = "  &#65;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 2, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithTrailingSpaces() throws IOException {
        String input = "&#65;  ";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }
}
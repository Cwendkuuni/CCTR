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
    public void testTranslateNonEntity() throws IOException {
        String input = "Not an entity";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithExtraCharacters() throws IOException {
        String input = "&#65;Extra";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);

        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLeadingCharacters() throws IOException {
        String input = "Leading&#65;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 7, out);

        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithLeadingAndTrailingCharacters() throws IOException {
        String input = "Leading&#65;Trailing";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 7, out);

        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidIndex() throws IOException {
        String input = "&#65;";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 1, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithEmptyInput() throws IOException {
        String input = "";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithIncompleteEntity() throws IOException {
        String input = "&#65";
        StringWriter out = new StringWriter();
        int result = unescaper.translate(input, 0, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
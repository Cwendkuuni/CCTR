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
        StringWriter out = new StringWriter();
        String input = "&#65;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#x41;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(7, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#xG1;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter out = new StringWriter();
        String input = "abc";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        StringWriter out = new StringWriter();
        String input = "";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateIncompleteEntity() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidIndex() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#65;";
        int index = 1;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithOutOfBoundsIndex() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#65;";
        int index = input.length();
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
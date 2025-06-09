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
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(7, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "abc";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateIncompleteEntity() throws IOException {
        String input = "&#";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidEnd() throws IOException {
        String input = "&#65";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidStart() throws IOException {
        String input = "&65;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
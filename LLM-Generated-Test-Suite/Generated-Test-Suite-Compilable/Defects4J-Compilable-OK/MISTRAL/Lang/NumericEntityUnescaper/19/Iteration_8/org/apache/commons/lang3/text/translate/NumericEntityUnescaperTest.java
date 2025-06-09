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
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("A", out.toString());
        assertEquals(6, result);
    }

    @Test
    public void testTranslateDecimalEntityWithoutSemicolon() throws IOException {
        String input = "&#65";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateHexEntityWithoutSemicolon() throws IOException {
        String input = "&#x41";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEntityWithLargeCodepoint() throws IOException {
        String input = "&#x10FFFF;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("\uDBFF\uDFFF", out.toString());
        assertEquals(10, result);
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "&notanentity;";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateInputWithoutEntity() throws IOException {
        String input = "No entity here";
        StringWriter out = new StringWriter();
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }
}
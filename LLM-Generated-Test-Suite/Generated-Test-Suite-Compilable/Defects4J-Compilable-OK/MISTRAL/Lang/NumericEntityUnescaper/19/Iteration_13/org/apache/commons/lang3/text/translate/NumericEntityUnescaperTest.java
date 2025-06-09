package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
    }

    @After
    public void tearDown() {
        unescaper = null;
    }

    @Test
    public void testTranslateDecimal() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#65;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexadecimal() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#x41;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateDecimalWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#65";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(4, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexadecimalWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#x41";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(5, result);
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
    public void testTranslateHighSurrogate() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#x1D11E;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(8, result);
        assertEquals("\uD834\uDD1E", out.toString());
    }

    @Test
    public void testTranslateLowSurrogate() throws IOException {
        StringWriter out = new StringWriter();
        String input = "&#xDC00;";
        int index = 0;
        int result = unescaper.translate(input, index, out);

        assertEquals(7, result);
        assertEquals("\uDC00", out.toString());
    }
}
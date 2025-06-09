package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;
    private StringWriter writer;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
        writer = new StringWriter();
    }

    @After
    public void tearDown() {
        unescaper = null;
        writer = null;
    }

    @Test
    public void testTranslateDecimal() throws IOException {
        String input = "&#65;";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexadecimal() throws IOException {
        String input = "&#x41;";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateDecimalWithoutSemicolon() throws IOException {
        String input = "&#65";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexadecimalWithoutSemicolon() throws IOException {
        String input = "&#x41";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateNonEntityInput() throws IOException {
        String input = "Hello World";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateHighSurrogate() throws IOException {
        String input = "&#x1D11E;";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(8, result);
        assertEquals("\uD834\uDD1E", writer.toString());
    }

    @Test
    public void testTranslateLowSurrogate() throws IOException {
        String input = "&#xDC00;";
        int index = 0;
        int result = unescaper.translate(input, index, writer);
        assertEquals(7, result);
        assertEquals("\uDC00", writer.toString());
    }
}
package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;
    private StringWriter out;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
        out = new StringWriter();
    }

    @After
    public void tearDown() {
        unescaper = null;
        out = null;
    }

    @Test
    public void testTranslateDecimal() throws IOException {
        String input = "&#65;";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexadecimal() throws IOException {
        String input = "&#x41;";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateDecimalWithoutSemicolon() throws IOException {
        String input = "&#65";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(4, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateHexadecimalWithoutSemicolon() throws IOException {
        String input = "&#x41";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xG1;";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithLargeValue() throws IOException {
        String input = "&#x10FFFF;";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(9, result);
        assertEquals("\uD83D\uDFFF", out.toString());
    }

    @Test
    public void testTranslateEntityWithLargeValueWithoutSemicolon() throws IOException {
        String input = "&#x10FFFF";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(8, result);
        assertEquals("\uD83D\uDFFF", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidIndex() throws IOException {
        String input = "A&#65;";
        int index = 1;
        int result = unescaper.translate(input, index, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidStart() throws IOException {
        String input = "&#";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEntityWithInvalidEnd() throws IOException {
        String input = "&#65abc";
        int index = 0;
        int result = unescaper.translate(input, index, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
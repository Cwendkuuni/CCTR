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
    public void testDecimalEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#x41;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#x41";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#invalid;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "A";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testHighCodePointEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#128512;"; // 😀 emoji
        int result = unescaper.translate(input, 0, writer);
        assertEquals(8, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }

    @Test
    public void testHexHighCodePointEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#x1F600;"; // 😀 emoji
        int result = unescaper.translate(input, 0, writer);
        assertEquals(9, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }
}
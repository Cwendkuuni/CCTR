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
    public void testDecimalEntity() throws IOException {
        String input = "Hello &#65; World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityLowerCase() throws IOException {
        String input = "Hello &#x41; World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityUpperCase() throws IOException {
        String input = "Hello &#X41; World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testEntityWithoutSemicolon() throws IOException {
        String input = "Hello &#65 World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testInvalidEntity() throws IOException {
        String input = "Hello &#xyz; World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testNonEntity() throws IOException {
        String input = "Hello World";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testHighCodePointEntity() throws IOException {
        String input = "Hello &#128512; World"; // 😀 emoji
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(8, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }

    @Test
    public void testIncompleteEntity() throws IOException {
        String input = "Hello &#12";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testEmptyInput() throws IOException {
        String input = "";
        StringWriter writer = new StringWriter();
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }
}
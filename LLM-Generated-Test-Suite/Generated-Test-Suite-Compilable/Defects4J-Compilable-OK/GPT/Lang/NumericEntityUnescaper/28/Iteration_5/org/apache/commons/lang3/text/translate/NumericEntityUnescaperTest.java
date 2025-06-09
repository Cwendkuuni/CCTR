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
        StringWriter writer = new StringWriter();
        String input = "&#65;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntityLowerCase() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#x41;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateHexEntityUpperCase() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#X41;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateInvalidEntityNoSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateInvalidEntityNonNumeric() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#xGHI;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "Hello World";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateEntityAtEnd() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "Hello &#65;";
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testTranslateMultipleEntities() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65;&#66;&#67;";
        int index = 0;
        while (index < input.length()) {
            index += unescaper.translate(input, index, writer);
        }
        assertEquals("ABC", writer.toString());
    }
}
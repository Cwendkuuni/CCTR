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
    public void testDecimalEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityLowerCase() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#x41;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityUpperCase() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#X41;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testInvalidEntityNoSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testInvalidEntityNonNumeric() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#abc;";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "Hello World";
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testEntityInMiddleOfText() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "Hello &#65; World";
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testMultipleEntities() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65;&#66;&#67;";
        int result1 = unescaper.translate(input, 0, writer);
        int result2 = unescaper.translate(input, result1, writer);
        int result3 = unescaper.translate(input, result1 + result2, writer);
        assertEquals(5, result1);
        assertEquals(5, result2);
        assertEquals(5, result3);
        assertEquals("ABC", writer.toString());
    }
}
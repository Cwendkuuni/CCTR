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
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityLowerCase() throws IOException {
        String input = "Hello &#x41; World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityUpperCase() throws IOException {
        String input = "Hello &#X41; World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testInvalidEntityNoSemicolon() throws IOException {
        String input = "Hello &#65 World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testInvalidEntityNonNumeric() throws IOException {
        String input = "Hello &#ABC; World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testInvalidEntityEmpty() throws IOException {
        String input = "Hello &#; World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testNoEntity() throws IOException {
        String input = "Hello World";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testEntityAtEnd() throws IOException {
        String input = "Hello &#65;";
        Writer writer = new StringWriter();
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }
}
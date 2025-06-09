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
        String input = "&#65;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testHexEntityLowerCase() throws IOException {
        String input = "&#x41;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testHexEntityUpperCase() throws IOException {
        String input = "&#X41;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(6, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testInvalidEntityNoSemicolon() throws IOException {
        String input = "&#65";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testInvalidEntityNonNumeric() throws IOException {
        String input = "&#abc;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testNonEntity() throws IOException {
        String input = "A";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testEntityAtEnd() throws IOException {
        String input = "Hello &#65;";
        Writer out = new StringWriter();
        int result = unescaper.translate(input, 6, out);
        assertEquals(5, result);
        assertEquals("A", out.toString());
    }

    @Test
    public void testMultipleEntities() throws IOException {
        String input = "&#65;&#66;&#67;";
        Writer out = new StringWriter();
        int index = 0;
        while (index < input.length()) {
            int result = unescaper.translate(input, index, out);
            if (result == 0) {
                index++;
            } else {
                index += result;
            }
        }
        assertEquals("ABC", out.toString());
    }
}
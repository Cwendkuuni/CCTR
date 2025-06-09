package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.junit.Before;
import org.junit.Test;

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
    public void testInvalidEntityMissingSemicolon() throws IOException {
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
    public void testEntityAtEnd() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "Hello &#65;";
        int result = unescaper.translate(input, 6, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testMultipleEntities() throws IOException {
        StringWriter writer = new StringWriter();
        String input = "&#65;&#66;&#67;";
        int index = 0;
        while (index < input.length()) {
            int result = unescaper.translate(input, index, writer);
            if (result == 0) {
                break;
            }
            index += result;
        }
        assertEquals("ABC", writer.toString());
    }

    @Test
    public void testOutOfBounds() {
        StringWriter writer = new StringWriter();
        String input = "&#";
        try {
            unescaper.translate(input, 0, writer);
            fail("Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
}
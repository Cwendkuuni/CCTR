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
    public void testDecimalEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65;", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#65", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(4, consumed);
    }

    @Test
    public void testHexEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41;", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(6, consumed);
    }

    @Test
    public void testHexEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#x41", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&invalid;", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, consumed);
    }

    @Test
    public void testNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("Hello World", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, consumed);
    }

    @Test
    public void testHighCodepointEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("&#128512;", 0, writer); // 😀 emoji
        assertEquals("\uD83D\uDE00", writer.toString());
        assertEquals(8, consumed);
    }

    @Test
    public void testIncompleteEntity() {
        StringWriter writer = new StringWriter();
        try {
            unescaper.translate("&#x", 0, writer);
            fail("Expected IOException due to incomplete entity");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    public void testEmptyInput() throws IOException {
        StringWriter writer = new StringWriter();
        int consumed = unescaper.translate("", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, consumed);
    }
}
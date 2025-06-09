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
        int result = unescaper.translate("&#65;", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#65", 0, writer);
        assertEquals(4, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, writer);
        assertEquals(6, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testHexEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#x41", 0, writer);
        assertEquals(5, result);
        assertEquals("A", writer.toString());
    }

    @Test
    public void testInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&invalid;", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("A", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testEntityWithLargeCodePoint() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#128512;", 0, writer); // 😀 emoji
        assertEquals(8, result);
        assertEquals("\uD83D\uDE00", writer.toString());
    }

    @Test
    public void testEntityWithInvalidNumber() {
        StringWriter writer = new StringWriter();
        try {
            unescaper.translate("&#xZZ;", 0, writer);
            fail("Expected NumberFormatException");
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
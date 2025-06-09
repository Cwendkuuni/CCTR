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
        assertEquals("A", writer.toString());
        assertEquals(5, result);
    }

    @Test
    public void testDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#65", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(4, result);
    }

    @Test
    public void testHexEntityWithSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(6, result);
    }

    @Test
    public void testHexEntityWithoutSemicolon() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#x41", 0, writer);
        assertEquals("A", writer.toString());
        assertEquals(5, result);
    }

    @Test
    public void testInvalidEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&abc;", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testNonEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("Hello World", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testEntityWithInvalidNumber() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#xZZ;", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testHighCodePointEntity() throws IOException {
        StringWriter writer = new StringWriter();
        int result = unescaper.translate("&#x1F600;", 0, writer); // 😀 emoji
        assertEquals("\uD83D\uDE00", writer.toString());
        assertEquals(8, result);
    }

    @Test
    public void testIncompleteEntity() throws IOException {
        StringWriter writer = new StringWriter();
        try {
            unescaper.translate("&#x1F60", 0, writer);
            fail("Expected IOException due to incomplete entity");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
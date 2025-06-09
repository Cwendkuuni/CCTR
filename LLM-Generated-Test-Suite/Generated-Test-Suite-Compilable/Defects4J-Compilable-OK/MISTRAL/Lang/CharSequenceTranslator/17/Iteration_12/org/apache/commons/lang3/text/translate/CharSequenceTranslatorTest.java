package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

public class CharSequenceTranslatorTest {

    private CharSequenceTranslator translator;

    @Before
    public void setUp() {
        translator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Mock implementation for testing
                return 0;
            }
        };
    }

    @Test
    public void testTranslateCharSequence() {
        String input = "test";
        String result = translator.translate(input);
        assertNotNull(result);
        assertEquals(input, result); // Assuming the mock implementation does not change the input
    }

    @Test
    public void testTranslateCharSequenceNull() {
        String result = translator.translate(null);
        assertNull(result);
    }

    @Test
    public void testTranslateCharSequenceWriter() throws IOException {
        String input = "test";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        assertEquals(input, writer.toString()); // Assuming the mock implementation does not change the input
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceWriterNull() throws IOException {
        translator.translate("test", null);
    }

    @Test
    public void testTranslateCharSequenceWriterNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        assertEquals("", writer.toString());
    }

    @Test
    public void testWith() {
        CharSequenceTranslator mockTranslator = Mockito.mock(CharSequenceTranslator.class);
        CharSequenceTranslator result = translator.with(mockTranslator);
        assertNotNull(result);
        assertTrue(result instanceof AggregateTranslator);
    }

    @Test
    public void testHex() {
        int codepoint = 65; // ASCII 'A'
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals("41", result);
    }

    @Test
    public void testHexZero() {
        int codepoint = 0;
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals("0", result);
    }

    @Test
    public void testHexNegative() {
        int codepoint = -1;
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals("FFFFFFFF", result);
    }

    @Test
    public void testHexLargeValue() {
        int codepoint = Integer.MAX_VALUE;
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals("7FFFFFFF", result);
    }
}
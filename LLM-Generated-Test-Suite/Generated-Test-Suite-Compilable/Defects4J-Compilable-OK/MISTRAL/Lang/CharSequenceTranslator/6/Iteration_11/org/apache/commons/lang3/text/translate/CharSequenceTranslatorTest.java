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
                // Dummy implementation for testing purposes
                out.write(input.charAt(index));
                return 1;
            }
        };
    }

    @Test
    public void testTranslateCharSequence() {
        String input = "Hello World";
        String expectedOutput = "Hello World";
        String result = translator.translate(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testTranslateCharSequenceNull() {
        String result = translator.translate(null);
        assertNull(result);
    }

    @Test
    public void testTranslateCharSequenceWriter() throws IOException {
        String input = "Hello World";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        String result = writer.toString();
        assertEquals(input, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceWriterNull() throws IOException {
        translator.translate("Hello World", null);
    }

    @Test
    public void testTranslateCharSequenceWriterNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        assertEquals("", writer.toString());
    }

    @Test
    public void testWithTranslators() {
        CharSequenceTranslator anotherTranslator = Mockito.mock(CharSequenceTranslator.class);
        CharSequenceTranslator mergedTranslator = translator.with(anotherTranslator);
        assertNotNull(mergedTranslator);
    }

    @Test
    public void testHex() {
        int codepoint = 65; // ASCII 'A'
        String expectedHex = "41";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedHex, result);
    }

    @Test
    public void testHexZero() {
        int codepoint = 0;
        String expectedHex = "0";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedHex, result);
    }

    @Test
    public void testHexNegative() {
        int codepoint = -1;
        String expectedHex = "FFFFFFFF";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedHex, result);
    }
}
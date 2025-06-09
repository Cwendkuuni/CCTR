package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CharSequenceTranslatorTest {

    private CharSequenceTranslator translator;

    @Before
    public void setUp() {
        translator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Dummy implementation for testing purposes
                if (index < input.length()) {
                    out.write(input.charAt(index));
                    return 1;
                }
                return 0;
            }
        };
    }

    @Test
    public void testTranslateCharSequence() {
        String input = "Hello, World!";
        String expected = "Hello, World!";
        String result = translator.translate(input);
        assertEquals(expected, result);
    }

    @Test
    public void testTranslateCharSequenceNull() {
        String result = translator.translate(null);
        assertNull(result);
    }

    @Test
    public void testTranslateCharSequenceWriter() throws IOException {
        String input = "Hello, World!";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        String result = writer.toString();
        assertEquals(input, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceWriterNullWriter() throws IOException {
        String input = "Hello, World!";
        translator.translate(input, null);
    }

    @Test
    public void testTranslateCharSequenceWriterNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        String result = writer.toString();
        assertEquals("", result);
    }

    @Test
    public void testWith() {
        CharSequenceTranslator otherTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Dummy implementation for testing purposes
                if (index < input.length()) {
                    out.write(input.charAt(index));
                    return 1;
                }
                return 0;
            }
        };
        CharSequenceTranslator mergedTranslator = translator.with(otherTranslator);
        assertNotNull(mergedTranslator);
    }

    @Test
    public void testHex() {
        int codepoint = 65; // ASCII 'A'
        String expected = "41";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expected, result);
    }

    @Test
    public void testHexZero() {
        int codepoint = 0;
        String expected = "0";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expected, result);
    }

    @Test
    public void testHexNegative() {
        int codepoint = -1;
        String expected = "FFFFFFFF";
        String result = CharSequenceTranslator.hex(codepoint);
        assertEquals(expected, result);
    }
}
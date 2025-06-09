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
        String expectedOutput = "Hello, World!";
        String actualOutput = translator.translate(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testTranslateCharSequenceNull() {
        String input = null;
        String expectedOutput = null;
        String actualOutput = translator.translate(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testTranslateCharSequenceWriter() throws IOException {
        String input = "Hello, World!";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        String expectedOutput = "Hello, World!";
        String actualOutput = writer.toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceWriterNull() throws IOException {
        String input = "Hello, World!";
        Writer writer = null;
        translator.translate(input, writer);
    }

    @Test
    public void testTranslateCharSequenceWriterNullInput() throws IOException {
        String input = null;
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        String expectedOutput = "";
        String actualOutput = writer.toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testWith() {
        CharSequenceTranslator anotherTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Another dummy implementation for testing purposes
                if (index < input.length()) {
                    out.write(input.charAt(index));
                    return 1;
                }
                return 0;
            }
        };
        CharSequenceTranslator mergedTranslator = translator.with(anotherTranslator);
        assertNotNull(mergedTranslator);
    }

    @Test
    public void testHex() {
        int codepoint = 65; // ASCII value for 'A'
        String expectedOutput = "41";
        String actualOutput = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testHexZero() {
        int codepoint = 0;
        String expectedOutput = "0";
        String actualOutput = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testHexNegative() {
        int codepoint = -1;
        String expectedOutput = "FFFFFFFF";
        String actualOutput = CharSequenceTranslator.hex(codepoint);
        assertEquals(expectedOutput, actualOutput);
    }
}
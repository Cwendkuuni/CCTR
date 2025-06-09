package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

public class CharSequenceTranslatorTest {

    private CharSequenceTranslator translator;

    @Before
    public void setUp() {
        // Create a simple CharSequenceTranslator for testing purposes
        translator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Simple translation: replace 'a' with 'b'
                if (input.charAt(index) == 'a') {
                    out.write('b');
                    return 1;
                }
                return 0;
            }
        };
    }

    @Test
    public void testTranslateCharSequence() {
        String input = "apple";
        String expected = "bpple";
        String result = translator.translate(input);
        assertEquals(expected, result);
    }

    @Test
    public void testTranslateCharSequenceNullInput() {
        assertNull(translator.translate(null));
    }

    @Test
    public void testTranslateWriter() throws IOException {
        String input = "banana";
        String expected = "bbnbnb";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        assertEquals(expected, writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateWriterNullWriter() throws IOException {
        translator.translate("test", null);
    }

    @Test
    public void testTranslateWriterNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        assertEquals("", writer.toString());
    }

    @Test
    public void testWith() {
        CharSequenceTranslator anotherTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Simple translation: replace 'b' with 'c'
                if (input.charAt(index) == 'b') {
                    out.write('c');
                    return 1;
                }
                return 0;
            }
        };

        CharSequenceTranslator combined = translator.with(anotherTranslator);
        String input = "banana";
        String expected = "ccncnb";
        String result = combined.translate(input);
        assertEquals(expected, result);
    }

    @Test
    public void testHex() {
        assertEquals("41", CharSequenceTranslator.hex(65)); // 'A' in ASCII
        assertEquals("1F600", CharSequenceTranslator.hex(0x1F600)); // Unicode for 😀
    }
}
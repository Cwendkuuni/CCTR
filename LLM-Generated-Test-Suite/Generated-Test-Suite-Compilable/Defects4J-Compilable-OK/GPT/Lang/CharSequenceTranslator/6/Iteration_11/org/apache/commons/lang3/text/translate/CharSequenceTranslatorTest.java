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
    public void testTranslateCharSequenceToString() {
        String result = translator.translate("abc");
        assertEquals("bbc", result);
    }

    @Test
    public void testTranslateCharSequenceToWriter() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate("abc", writer);
        assertEquals("bbc", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateWithNullWriter() throws IOException {
        translator.translate("abc", null);
    }

    @Test
    public void testTranslateWithNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateWithEmptyInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate("", writer);
        assertEquals("", writer.toString());
    }

    @Test
    public void testWithMethod() {
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

        CharSequenceTranslator combinedTranslator = translator.with(anotherTranslator);
        String result = combinedTranslator.translate("abc");
        assertEquals("bcc", result);
    }

    @Test
    public void testHexMethod() {
        assertEquals("41", CharSequenceTranslator.hex(65)); // 'A' in ASCII
        assertEquals("1F600", CharSequenceTranslator.hex(0x1F600)); // Unicode for 😀
    }
}
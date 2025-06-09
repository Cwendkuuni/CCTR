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
                // Simple translation: convert 'a' to 'b'
                if (input.charAt(index) == 'a') {
                    out.write('b');
                    return 1;
                }
                return 0;
            }
        };
    }

    @Test
    public void testTranslateWithNullInput() {
        assertNull(translator.translate(null));
    }

    @Test
    public void testTranslateWithEmptyInput() {
        assertEquals("", translator.translate(""));
    }

    @Test
    public void testTranslateWithSimpleInput() {
        assertEquals("bbc", translator.translate("abc"));
    }

    @Test
    public void testTranslateWithNoTranslation() {
        assertEquals("xyz", translator.translate("xyz"));
    }

    @Test
    public void testTranslateWithWriter() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate("abc", writer);
        assertEquals("bbc", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateWithNullWriter() throws IOException {
        translator.translate("abc", null);
    }

    @Test
    public void testWithMethod() {
        CharSequenceTranslator additionalTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Simple translation: convert 'b' to 'c'
                if (input.charAt(index) == 'b') {
                    out.write('c');
                    return 1;
                }
                return 0;
            }
        };

        CharSequenceTranslator combinedTranslator = translator.with(additionalTranslator);
        assertEquals("ccc", combinedTranslator.translate("abc"));
    }

    @Test
    public void testHexMethod() {
        assertEquals("41", CharSequenceTranslator.hex(65)); // 'A' in ASCII
        assertEquals("1F600", CharSequenceTranslator.hex(0x1F600)); // 😀 emoji
    }
}
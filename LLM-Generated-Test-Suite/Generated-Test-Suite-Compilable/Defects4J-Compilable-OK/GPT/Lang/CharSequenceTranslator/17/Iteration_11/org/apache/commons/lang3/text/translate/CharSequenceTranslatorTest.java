package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;

public class CharSequenceTranslatorTest {

    private CharSequenceTranslator translator;

    @Before
    public void setUp() {
        // Create a simple implementation of CharSequenceTranslator for testing
        translator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Simple translation: convert each character to its uppercase equivalent
                char c = input.charAt(index);
                out.write(Character.toUpperCase(c));
                return 1;
            }
        };
    }

    @Test
    public void testTranslateCharSequenceToString() {
        String input = "hello";
        String expected = "HELLO";
        String result = translator.translate(input);
        assertEquals(expected, result);
    }

    @Test
    public void testTranslateCharSequenceToWriter() throws IOException {
        String input = "world";
        String expected = "WORLD";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        assertEquals(expected, writer.toString());
    }

    @Test
    public void testTranslateNullCharSequenceToString() {
        assertNull(translator.translate(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateNullWriter() throws IOException {
        translator.translate("test", null);
    }

    @Test
    public void testWithMethod() {
        CharSequenceTranslator anotherTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(CharSequence input, int index, Writer out) throws IOException {
                // Simple translation: convert each character to its lowercase equivalent
                char c = input.charAt(index);
                out.write(Character.toLowerCase(c));
                return 1;
            }
        };

        CharSequenceTranslator mergedTranslator = translator.with(anotherTranslator);
        assertNotNull(mergedTranslator);
    }

    @Test
    public void testHexMethod() {
        assertEquals("41", CharSequenceTranslator.hex(65)); // 'A' in ASCII
        assertEquals("7A", CharSequenceTranslator.hex(122)); // 'z' in ASCII
    }
}
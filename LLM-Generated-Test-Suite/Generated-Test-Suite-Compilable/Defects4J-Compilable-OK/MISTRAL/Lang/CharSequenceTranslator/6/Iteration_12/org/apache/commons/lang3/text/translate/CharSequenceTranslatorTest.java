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
    public void testTranslateCharSequence() throws IOException {
        String input = "Hello, World!";
        StringWriter writer = new StringWriter();
        translator.translate(input, writer);
        assertEquals("Hello, World!", writer.toString());
    }

    @Test
    public void testTranslateCharSequenceNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        translator.translate(null, writer);
        assertEquals("", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceNullWriter() throws IOException {
        translator.translate("Hello, World!", null);
    }

    @Test
    public void testTranslate() {
        String input = "Hello, World!";
        String result = translator.translate(input);
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testTranslateNullInput() {
        String result = translator.translate(null);
        assertNull(result);
    }

    @Test
    public void testWith() {
        CharSequenceTranslator anotherTranslator = new CharSequenceTranslator() {
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

        CharSequenceTranslator mergedTranslator = translator.with(anotherTranslator);
        assertNotNull(mergedTranslator);
        assertTrue(mergedTranslator instanceof AggregateTranslator);
    }

    @Test
    public void testHex() {
        assertEquals("41", CharSequenceTranslator.hex(65)); // 'A' in ASCII
        assertEquals("7A", CharSequenceTranslator.hex(122)); // 'z' in ASCII
        assertEquals("1F600", CharSequenceTranslator.hex(0x1F600)); // Grinning face emoji
    }

    private static class AggregateTranslator extends CharSequenceTranslator {
        private final CharSequenceTranslator[] translators;

        public AggregateTranslator(CharSequenceTranslator[] translators) {
            this.translators = translators;
        }

        @Override
        public int translate(CharSequence input, int index, Writer out) throws IOException {
            for (CharSequenceTranslator translator : translators) {
                int consumed = translator.translate(input, index, out);
                if (consumed > 0) {
                    return consumed;
                }
            }
            return 0;
        }
    }
}
package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.junit.Before;
import org.junit.Test;

public class LookupTranslatorTest {

    private LookupTranslator translator;

    @Before
    public void setUp() {
        // Initialize the translator with some sample data
        translator = new LookupTranslator(
            new CharSequence[][] {
                {"cat", "feline"},
                {"dog", "canine"},
                {"bird", "avian"}
            }
        );
    }

    @Test
    public void testTranslateExactMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("cat", 0, writer);
        assertEquals("feline", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslatePartialMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("caterpillar", 0, writer);
        assertEquals("feline", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("elephant", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateMultipleMatches() throws IOException {
        Writer writer = new StringWriter();
        String input = "catdogbird";
        int index = 0;
        while (index < input.length()) {
            index += translator.translate(input, index, writer);
        }
        assertEquals("felinecanineavian", writer.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateNullInput() {
        assertThrows(NullPointerException.class, () -> {
            translator.translate(null, 0, new StringWriter());
        });
    }

    @Test
    public void testTranslateWithOffset() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("xxcatxx", 2, writer);
        assertEquals("feline", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateWithShorterMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("dogmatic", 0, writer);
        assertEquals("canine", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateWithLongestMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("birdwatching", 0, writer);
        assertEquals("avian", writer.toString());
        assertEquals(4, result);
    }
}
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class LookupTranslatorTest {

    private LookupTranslator translator;

    @Before
    public void setUp() {
        // Initialize with some sample data
        translator = new LookupTranslator(
                new CharSequence[][]{
                        {"abc", "123"},
                        {"def", "456"},
                        {"ghi", "789"}
                }
        );
    }

    @Test
    public void testTranslateExactMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("abc", 0, writer);
        assertEquals(3, result);
        assertEquals("123", writer.toString());
    }

    @Test
    public void testTranslatePartialMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("abcdef", 0, writer);
        assertEquals(3, result);
        assertEquals("123", writer.toString());
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("xyz", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateMultipleMatches() throws IOException {
        Writer writer = new StringWriter();
        String input = "abcdefghi";
        int index = 0;
        while (index < input.length()) {
            int result = translator.translate(input, index, writer);
            if (result == 0) {
                writer.write(input.charAt(index));
                index++;
            } else {
                index += result;
            }
        }
        assertEquals("123456789", writer.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("", 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateNullInput() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate(null, 0, writer);
        assertEquals(0, result);
        assertEquals("", writer.toString());
    }

    @Test
    public void testTranslateWithOffset() throws IOException {
        Writer writer = new StringWriter();
        int result = translator.translate("xyzabc", 3, writer);
        assertEquals(3, result);
        assertEquals("123", writer.toString());
    }
}
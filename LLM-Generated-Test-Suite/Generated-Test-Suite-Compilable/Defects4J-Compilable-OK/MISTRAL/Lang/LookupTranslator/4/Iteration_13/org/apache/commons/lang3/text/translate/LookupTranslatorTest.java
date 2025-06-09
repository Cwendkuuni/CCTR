package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class LookupTranslatorTest {

    private LookupTranslator translator;

    @Before
    public void setUp() {
        CharSequence[][] lookup = {
            {"a", "1"},
            {"ab", "2"},
            {"abc", "3"},
            {"abcd", "4"}
        };
        translator = new LookupTranslator(lookup);
    }

    @Test
    public void testTranslateSingleChar() throws IOException {
        String input = "a";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(1, result);
        assertEquals("1", out.toString());
    }

    @Test
    public void testTranslateMultipleChars() throws IOException {
        String input = "abcd";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslatePartialMatch() throws IOException {
        String input = "abcdef";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        String input = "xyz";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateLongestMatch() throws IOException {
        String input = "abcdabcd";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslateShortestMatch() throws IOException {
        String input = "a";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(1, result);
        assertEquals("1", out.toString());
    }

    @Test
    public void testTranslateMixedMatch() throws IOException {
        String input = "ababcd";
        Writer out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("2", out.toString());
    }

    @Test
    public void testTranslateWithOffset() throws IOException {
        String input = "xyzabcd";
        Writer out = new StringWriter();
        int result = translator.translate(input, 3, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslateWithOffsetNoMatch() throws IOException {
        String input = "xyzabcd";
        Writer out = new StringWriter();
        int result = translator.translate(input, 1, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }
}
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
            {"b", "2"},
            {"ab", "3"},
            {"ba", "4"}
        };
        translator = new LookupTranslator(lookup);
    }

    @Test
    public void testTranslateSingleChar() throws IOException {
        String input = "a";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(1, result);
        assertEquals("1", out.toString());
    }

    @Test
    public void testTranslateMultipleChars() throws IOException {
        String input = "ab";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("3", out.toString());
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        String input = "c";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateGreedyAlgorithm() throws IOException {
        String input = "aba";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("3", out.toString());
    }

    @Test
    public void testTranslatePartialMatch() throws IOException {
        String input = "bac";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        String input = "";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateOutOfBounds() throws IOException {
        String input = "a";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 1, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslateLongestMatch() throws IOException {
        String input = "abba";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("3", out.toString());
    }

    @Test
    public void testTranslateShortestMatch() throws IOException {
        String input = "ba";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslateMixedMatch() throws IOException {
        String input = "abba";
        StringWriter out = new StringWriter();
        int result = translator.translate(input, 0, out);
        assertEquals(2, result);
        assertEquals("3", out.toString());

        out = new StringWriter();
        result = translator.translate(input, 2, out);
        assertEquals(2, result);
        assertEquals("4", out.toString());
    }
}
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.StringWriter;

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
    public void testTranslate_SingleChar() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("a", 0, out);
        assertEquals(1, result);
        assertEquals("1", out.toString());
    }

    @Test
    public void testTranslate_MultipleChars() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("ab", 0, out);
        assertEquals(2, result);
        assertEquals("2", out.toString());
    }

    @Test
    public void testTranslate_LongestMatch() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("abcd", 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslate_NoMatch() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("xyz", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslate_PartialMatch() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("abcde", 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());
    }

    @Test
    public void testTranslate_IndexOutOfBounds() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("abcd", 3, out);
        assertEquals(1, result);
        assertEquals("1", out.toString());
    }

    @Test
    public void testTranslate_EmptyInput() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("", 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslate_NullInput() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate(null, 0, out);
        assertEquals(0, result);
        assertEquals("", out.toString());
    }

    @Test
    public void testTranslate_MultipleTranslations() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("abcabc", 0, out);
        assertEquals(3, result);
        assertEquals("3", out.toString());

        out = new StringWriter();
        result = translator.translate("abcabc", 3, out);
        assertEquals(3, result);
        assertEquals("3", out.toString());
    }

    @Test
    public void testTranslate_MixedTranslations() throws IOException {
        StringWriter out = new StringWriter();
        int result = translator.translate("abcdabc", 0, out);
        assertEquals(4, result);
        assertEquals("4", out.toString());

        out = new StringWriter();
        result = translator.translate("abcdabc", 4, out);
        assertEquals(3, result);
        assertEquals("3", out.toString());
    }
}
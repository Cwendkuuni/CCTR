package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.junit.Before;
import org.junit.Test;

public class LookupTranslatorTest {

    private LookupTranslator translator;

    @Before
    public void setUp() {
        // Initialize the LookupTranslator with some test data
        translator = new LookupTranslator(
            new CharSequence[][] {
                {"a", "1"},
                {"b", "2"},
                {"abc", "123"},
                {"d", "4"}
            }
        );
    }

    @Test
    public void testTranslateSingleCharacter() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("a", 0, writer);
        assertEquals("1", writer.toString());
        assertEquals(1, result);
    }

    @Test
    public void testTranslateMultipleCharacters() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("abc", 0, writer);
        assertEquals("123", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("z", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslatePartialMatch() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("ab", 0, writer);
        assertEquals("1", writer.toString());
        assertEquals(1, result);
    }

    @Test
    public void testTranslateWithOffset() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("xabcd", 1, writer);
        assertEquals("123", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("", 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateNullInput() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate(null, 0, writer);
        assertEquals("", writer.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEndOfString() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("abcd", 3, writer);
        assertEquals("4", writer.toString());
        assertEquals(1, result);
    }

    @Test
    public void testTranslateLongestMatch() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("abc", 0, writer);
        assertEquals("123", writer.toString());
        assertEquals(3, result);
    }

    @Test
    public void testTranslateShortestMatch() throws IOException {
        StringWriter writer = new StringWriter();
        int result = translator.translate("abcd", 0, writer);
        assertEquals("1", writer.toString());
        assertEquals(1, result);
    }
}
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.StringWriter;
import static org.junit.Assert.assertEquals;

public class NumericEntityUnescaperTest {

    private NumericEntityUnescaper unescaper;

    @Before
    public void setUp() {
        unescaper = new NumericEntityUnescaper();
    }

    @Test
    public void testTranslateDecimalEntity() throws IOException {
        String input = "&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("A", out.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        String input = "&#x41;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("A", out.toString());
        assertEquals(6, consumed);
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        String input = "&#xyz;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("", out.toString());
        assertEquals(0, consumed);
    }

    @Test
    public void testTranslateIncompleteEntity() throws IOException {
        String input = "&#6";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("", out.toString());
        assertEquals(0, consumed);
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        String input = "abc";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("", out.toString());
        assertEquals(0, consumed);
    }

    @Test
    public void testTranslateEntityWithExtraChars() throws IOException {
        String input = "&#65;def";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 0, out);
        assertEquals("A", out.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testTranslateEntityWithLeadingChars() throws IOException {
        String input = "abc&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 3, out);
        assertEquals("A", out.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testTranslateEntityWithLeadingAndTrailingChars() throws IOException {
        String input = "abc&#65;def";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 3, out);
        assertEquals("A", out.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testTranslateEntityAtEndOfString() throws IOException {
        String input = "abc&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 3, out);
        assertEquals("A", out.toString());
        assertEquals(5, consumed);
    }

    @Test
    public void testTranslateEntityWithInvalidIndex() throws IOException {
        String input = "&#65;";
        StringWriter out = new StringWriter();
        int consumed = unescaper.translate(input, 1, out);
        assertEquals("", out.toString());
        assertEquals(0, consumed);
    }
}
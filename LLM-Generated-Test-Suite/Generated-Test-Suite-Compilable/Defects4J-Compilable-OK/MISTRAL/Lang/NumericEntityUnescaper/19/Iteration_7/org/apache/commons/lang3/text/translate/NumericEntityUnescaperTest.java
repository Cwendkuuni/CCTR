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
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65;", 0, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateHexEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, out);
        assertEquals("A", out.toString());
        assertEquals(6, result);
    }

    @Test
    public void testTranslateDecimalEntityWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateHexEntityWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x41", 0, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xG1;", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEntityWithLargeCodepoint() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x1F600;", 0, out);
        assertEquals("\uD83D\uDE00", out.toString());
        assertEquals(9, result);
    }

    @Test
    public void testTranslateEntityWithLargeCodepointWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x1F600", 0, out);
        assertEquals("\uD83D\uDE00", out.toString());
        assertEquals(8, result);
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEntityAtEndOfString() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateEntityWithInvalidHex() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xG1;", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEntityWithInvalidDecimal() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#G1;", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }
}
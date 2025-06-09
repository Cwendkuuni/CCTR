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
    public void testTranslateDecimal() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65;", 0, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateHexadecimal() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x41;", 0, out);
        assertEquals("A", out.toString());
        assertEquals(6, result);
    }

    @Test
    public void testTranslateDecimalWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateHexadecimalWithoutSemicolon() throws IOException {
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
    public void testTranslateNonEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateHighSurrogate() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x1D11E;", 0, out);
        assertEquals("\uD834\uDD1E", out.toString());
        assertEquals(9, result);
    }

    @Test
    public void testTranslateLowSurrogate() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xDD1E;", 0, out);
        assertEquals("\uDD1E", out.toString());
        assertEquals(8, result);
    }

    @Test
    public void testTranslateMixedContent() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("Hello &#65; World", 6, out);
        assertEquals("A", out.toString());
        assertEquals(5, result);
    }

    @Test
    public void testTranslateEmptyString() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateSingleAmpersand() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateAmpersandHash() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }
}
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
    public void testTranslateWithoutSemicolon() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#65", 0, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateInvalidEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#xyz;", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateHighSurrogate() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("&#x1D49C;", 0, out);
        assertEquals("\uD835\uDC9C", out.toString());
        assertEquals(9, result);
    }

    @Test
    public void testTranslateNonEntity() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEmptyInput() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("", 0, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }

    @Test
    public void testTranslateEntityAtEnd() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc&#65", 3, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateEntityInMiddle() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc&#65def", 3, out);
        assertEquals("A", out.toString());
        assertEquals(4, result);
    }

    @Test
    public void testTranslateEntityWithInvalidIndex() throws IOException {
        StringWriter out = new StringWriter();
        int result = unescaper.translate("abc&#65", 4, out);
        assertEquals("", out.toString());
        assertEquals(0, result);
    }
}
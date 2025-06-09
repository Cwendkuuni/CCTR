package org.znerd.xmlenc;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class XMLEncoderTest {

    private XMLEncoder encoder;
    private StringWriter writer;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        encoder = XMLEncoder.getEncoder("UTF-8");
        writer = new StringWriter();
    }

    @Test
    public void testGetEncoder() throws UnsupportedEncodingException {
        XMLEncoder encoder = XMLEncoder.getEncoder("UTF-8");
        assertNotNull(encoder);
        assertEquals("UTF-8", encoder.getEncoding());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncoderNullEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder(null);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testGetEncoderUnsupportedEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder("UNSUPPORTED");
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", encoder.getEncoding());
    }

    @Test
    public void testDeclaration() throws IOException {
        encoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextString() throws IOException, InvalidXMLException {
        encoder.text(writer, "test", true);
        assertEquals("test", writer.toString());
    }

    @Test
    public void testTextCharArray() throws IOException, InvalidXMLException {
        encoder.text(writer, "test".toCharArray(), 0, 4, true);
        assertEquals("test", writer.toString());
    }

    @Test
    public void testTextChar() throws IOException, InvalidXMLException {
        encoder.text(writer, 'a');
        assertEquals("a", writer.toString());
    }

    @Test
    public void testTextCharEscapeAmpersands() throws IOException, InvalidXMLException {
        encoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceString() throws IOException, InvalidXMLException {
        encoder.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceCharArray() throws IOException, InvalidXMLException {
        encoder.whitespace(writer, " \t\n\r".toCharArray(), 0, 4);
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testAttribute() throws IOException {
        encoder.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAttributeInvalidQuotationMark() throws IOException {
        encoder.attribute(writer, "name", "value", 'x', true);
    }

    @Test
    public void testAttributeEscapeAmpersands() throws IOException {
        encoder.attribute(writer, "name", "value&", '"', true);
        assertEquals(" name=\"value&amp;\"", writer.toString());
    }

    @Test
    public void testAttributeEscapeQuotes() throws IOException {
        encoder.attribute(writer, "name", "value\"", '"', true);
        assertEquals(" name=\"value&quot;\"", writer.toString());
    }

    @Test
    public void testAttributeEscapeApostrophes() throws IOException {
        encoder.attribute(writer, "name", "value'", '\'', true);
        assertEquals(" name='value&apos;'", writer.toString());
    }
}
package org.znerd.xmlenc;

import org.junit.Before;
import org.junit.Test;
import org.znerd.xmlenc.XMLEncoder;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class XMLEncoderTest {

    private XMLEncoder utf8Encoder;
    private XMLEncoder asciiEncoder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        utf8Encoder = XMLEncoder.getEncoder("UTF-8");
        asciiEncoder = XMLEncoder.getEncoder("US-ASCII");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncoderWithNullEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder(null);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testGetEncoderWithUnsupportedEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder("UNSUPPORTED");
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", utf8Encoder.getEncoding());
        assertEquals("US-ASCII", asciiEncoder.getEncoding());
    }

    @Test
    public void testDeclaration() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextWithEscapeAmpersands() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, "Text & More Text", true);
        assertEquals("Text &amp; More Text", writer.toString());
    }

    @Test
    public void testTextWithoutEscapeAmpersands() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, "Text & More Text", false);
        assertEquals("Text & More Text", writer.toString());
    }

    @Test(expected = InvalidXMLException.class)
    public void testTextWithInvalidCharacter() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, "\u0001", false);
    }

    @Test
    public void testTextChar() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, '<');
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testTextCharWithEscapeAmpersands() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespace() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.whitespace(writer, "   ");
        assertEquals("   ", writer.toString());
    }

    @Test
    public void testAttributeWithQuote() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", '\"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test
    public void testAttributeWithApostrophe() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", '\'', true);
        assertEquals(" name='value'", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAttributeWithInvalidQuotationMark() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", 'x', true);
    }
}